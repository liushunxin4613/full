package com.ylink.fullgoal.api.surface;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.inter.IMsgAction;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class IndicatorControllerApi<T extends IndicatorControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.vg)
    LinearLayout vg;
    @Bind(R.id.indicator)
    HorizontalScrollView indicator;

    private int sum;
    private View selected;
    private ViewPager viewPager;
    private BasePagerAdapter adapter;
    private IMsgAction action;
    private DataApi<DataApi, IndicatorBean> dataApi;

    public IndicatorControllerApi(C controller) {
        super(controller);
        dataApi = new DataApi<>();
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_indicator;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        sum = View.MeasureSpec.getSize(widthMeasureSpec);
        notifyDataSetChanged();
    }

    public DataApi<DataApi, IndicatorBean> getDataApi() {
        return dataApi;
    }

    public T setAction(IMsgAction action) {
        this.action = action;
        return getThis();
    }

    public T clear() {
        getDataApi().removeAll();
        return getThis();
    }

    public T add(IndicatorBean bean) {
        if (bean != null && bean.getApiType() != null) {
            getDataApi().add(bean);
        }
        return getThis();
    }

    public T addAll(IndicatorBean... args) {
        addAll(TextUtils.getListData(args));
        return getThis();
    }

    public T addAll(List<IndicatorBean> data) {
        if (!TextUtils.isEmpty(data)) {
            for (IndicatorBean bean : data) {
                add(bean);
            }
        }
        return getThis();
    }

    private int getCount() {
        return getDataApi().getCount();
    }

    public String getCurrentItemName() {
        if (viewPager != null) {
            IndicatorBean bean = getDataApi().getItem(viewPager.getCurrentItem());
            if (bean != null) {
                return bean.getName();
            }
        }
        return null;
    }

    public void notifyDataSetChanged() {
        int count = getCount();
        if (sum > 0 && count > 0) {
            int width = sum / count;
            if (adapter != null) {
                adapter.getApi().removeAll();
            }
            int viewSum = 0;
            vg.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            List<View> data = new ArrayList<>();
            for (int i = 0; i < getCount(); i++) {
                int finalI = i;
                IndicatorBean bean = getDataApi().getItem(i);
                if (bean != null && bean.getApiType() != null) {
                    if (adapter != null && bean.getApi() != null) {
                        adapter.getApi().add(bean.getApi());
                    }
                    View rootView = inflater.inflate(bean.getApiType(), null);
                    vg.addView(rootView);
                    setOnClickListener(rootView, v -> setCurrentItem(finalI))
                            .setText(findViewById(rootView, R.id.name_tv), bean.getName());
                    rootView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    rootView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    int viewWidth = rootView.getMeasuredWidth();
                    if (viewWidth < width) {
                        data.add(rootView);
                    }
                    viewSum += viewWidth;
                }
            }
            if (viewSum < sum && !TextUtils.isEmpty(data)) {
                int offset = (sum - viewSum) / TextUtils.count(data);
                for (View item : data) {
                    item.getLayoutParams().width = item.getMeasuredWidth() + offset;
                }
                vg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            }
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < 0) {
                currentItem = 0;
            } else if (currentItem > count - 1) {
                currentItem = count - 1;
            }
            onSelected(currentItem);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public T setViewPager(ViewPager vp) {
        this.viewPager = vp;
        if (viewPager != null) {
            adapter = (BasePagerAdapter) viewPager.getAdapter();
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    onSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
        return getThis();
    }

    public T setAdapter(BasePagerAdapter adapter) {
        this.adapter = adapter;
        viewPager.setAdapter(adapter);
        return getThis();
    }

    public T setCurrentItem(int item) {
        onSelected(item);
        if (viewPager != null)
            viewPager.setCurrentItem(item);
        return getThis();
    }

    private void onSelected(int position) {
        if (position < vg.getChildCount()) {
            View v = vg.getChildAt(position);
            if (!TextUtils.equals(v, selected) || !v.isSelected()) {
                if (selected != null) {
                    initSelected(selected, false);
                }
                initSelected(v, true);
                selected = v;
                scrollCenter(position);
                //处理数据
                if (action != null) {
                    action.execute(position, getExecute(getDataApi().getItem(position),
                            IndicatorBean::getName), null);
                }
            }
        }
    }

    private void initSelected(View v, boolean selected) {
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                ((ViewGroup) v).getChildAt(i).setSelected(selected);
            }
        }
    }

    private void scrollCenter(int position) {
        if (position < vg.getChildCount()) {
            View v = vg.getChildAt(position);
            int viewCenter = v.getLeft() + v.getMeasuredWidth() / 2;
            int center = sum / 2;
            int offset = viewCenter - center;
            indicator.scrollTo(offset, 0);
        }
    }

}
