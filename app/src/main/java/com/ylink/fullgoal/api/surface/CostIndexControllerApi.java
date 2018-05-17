package com.ylink.fullgoal.api.surface;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.adapter.BasePagerAdapter;
import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.TvHEtBean;
import com.ylink.fullgoal.controllerApi.surface.BarControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.view.MViewPager;
import com.ylink.fullgoal.vo.CastTargetVo;

import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * 费用指标
 */
public class CostIndexControllerApi<T extends CostIndexControllerApi, C> extends BarControllerApi<T, C> {

    @Bind(R.id.view_pager)
    MViewPager viewPager;
    @Bind(R.id.search_vg)
    LinearLayout searchVg;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.tax_tv)
    TextView taxTv;
    @Bind(R.id.none_tax_money_tv)
    TextView noneTaxMoneyTv;
    @Bind(R.id.yet_complete_tv)
    TextView yetCompleteTv;

    private int miniWidth = 120;
    private int miniSpeed = 0;
    private CastTargetVo targetVo;
    private GestureDetector detector;
    private BasePagerAdapter adapter;

    public CostIndexControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_cost_index;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("费用指标").setRightTv("确认", v -> show("确认"));
        initGestureDetector();
        initViewPager();
    }

    @Override
    public void initData() {
        super.initData();
        testCastTargetVo();
        initCastTargetVo();
    }

    private void testCastTargetVo() {
        targetVo = new CastTargetVo("会议费", "20000.00", "需要分摊", "3500.00", "16500.00", "34.50%");
    }

    private void initCastTargetVo() {
        setText(nameTv, targetVo.getCastTarget())
                .setText(detailTv, targetVo.getMoney())
                .setText(typeTv, targetVo.getApportionState())
                .setText(taxTv, targetVo.getTaxMoney())
                .setText(noneTaxMoneyTv, targetVo.getHasCastMoney())
                .setText(yetCompleteTv, targetVo.getYetApportionPercent());
    }

    private void initViewPager() {
        adapter = new BasePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setVerticalBeyondApi(is -> execute(!is, () -> {
            adapter.add(getRecycleControllerApi().getRootView()).notifyDataSetChanged();
            ee("count", adapter.getCount());
        }));
        adapter.add(getRecycleControllerApi().getRootView()).notifyDataSetChanged();
    }

    private void addVgBean(RecycleControllerApi controllerApi, IRunApi<List<BaseApiBean>> api) {
        if (controllerApi != null && api != null) {
            controllerApi.addVgBean(api);
            controllerApi.notifyDataSetChanged();
        }
    }

    private RecycleControllerApi getRecycleControllerApi() {
        RecycleControllerApi api = getViewControllerApi(RecycleControllerApi.class);
        int[] args = new int[]{R.color.green, R.color.blue_light, R.color.yellow_lemon};
        api.getRecyclerView().setBackgroundColor(ResUtil.getColor(args[new Random().nextInt(args.length)]));
        addVgBean(api, data -> {
            data.add(new TvHEtBean("金额", "13100.00", "请输入金额"));
            data.add(new TvH2SBean("分摊比例", "65.50%"));
        });
        return api;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return getExecute(detector, false, obj -> obj.onTouchEvent(event));
    }

    private void initGestureDetector() {
        detector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float offsetX = e2.getX() - e1.getX();
                float offsetY = e2.getY() - e1.getY();
                if (offsetX > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    ee("onFling", "向右滑动");
                } else if (-offsetX > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    ee("onFling", "向左滑动");
                } else if (offsetY > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    ee("onFling", "向下滑动");
                } else if (-offsetY > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    ee("onFling", "向上滑动");
                }
                return false;
            }
        });
    }

}
