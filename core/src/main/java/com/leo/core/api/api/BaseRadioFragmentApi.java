package com.leo.core.api.api;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.leo.core.api.core.ThisApi;
import com.leo.core.bean.RadioButtonBean;
import com.leo.core.iapi.api.IRadioFragmentApi;
import com.leo.core.util.TextUtils;

import java.util.List;

public abstract class BaseRadioFragmentApi<T extends BaseRadioFragmentApi> extends ThisApi<T> implements
        IRadioFragmentApi<T, Fragment> {

    private int fragmentIndex = -1;
    private PagerAdapter adapter;
    private FragmentActivity fragmentActivity;
    private List<RadioButtonBean> data;

    public BaseRadioFragmentApi(Activity activity) {
        if (activity instanceof FragmentActivity) {
            this.fragmentActivity = (FragmentActivity) activity;
        }
    }

    public void init() {
        if (getBottomGroup() != null) {
            for (int i = getFragmentCount() - 1; i >= 0 ; i--) {
                RadioButtonBean bean = getItem(-1, i);
                if(bean == null){
                    getData().remove(i);
                }
            }
            if (getRadioButtonResId() != null) {
                for (int i = 0; i < getFragmentCount(); i++) {
                    RadioButtonBean bean = getItem(-1, i);
                    View.inflate(getBottomGroup().getContext(), getRadioButtonResId(), getBottomGroup());
                    RadioButton button = (RadioButton) getBottomGroup().getChildAt(i);
                    button.setId(bean.getId());
                    button.setText(bean.getText());
                    Drawable drawable = getFragmentActivity().getDrawable(bean.getDrawableResId());
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    button.setCompoundDrawables(null, drawable, null, null);
                }
            }
            getBottomGroup().setOnCheckedChangeListener((group, checkedId) -> onCheckRadioGroup(getPosition(checkedId)));
            onCheckRadioGroup(getDefaultFragmentIndex());
        }

    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        if (adapter == null) {
            adapter = newPagerAdapter();
            if (adapter == null) {
                throw new NullPointerException("newPagerAdapter()不能为空");
            }
        }
        return adapter;
    }

    @Override
    public PagerAdapter newPagerAdapter() {
        return new FragmentPagerAdapter(getFragmentActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return create(position);
            }

            @Override
            public int getCount() {
                return getFragmentCount();
            }
        };
    }

    @Override
    public List<RadioButtonBean> getData() {
        if (data == null) {
            data = newData();
            if (data == null) {
                throw new NullPointerException("newData()不能为空");
            }
        }
        return data;
    }

    @Override
    public RadioButtonBean get(Object... args) {
        if(TextUtils.count(args) >= 2){
            return new RadioButtonBean((int)args[0], (String) args[1]);
        }
        return null;
    }

    @Override
    public abstract RadioGroup getBottomGroup();

    @Override
    public abstract Integer getRadioButtonResId();

    @Override
    public abstract FrameLayout getContentLayout();

    @Override
    public abstract List<RadioButtonBean> newData();

    @Override
    public abstract Fragment getPositionFragment(int position);

    @Override
    public abstract int getDefaultFragmentIndex();

    @Override
    public int getPosition(int id) {
        RadioButtonBean bean = getItem(id, -1);
        if (bean != null) {
            return bean.getPosition();
        }
        return -1;
    }

    @Override
    public int getFragmentIndex() {
        return fragmentIndex;
    }

    @Override
    public final int getFragmentCount() {
        return TextUtils.count(getData());
    }

    @Override
    public RadioButtonBean getItem(int id, int position) {
        if (getFragmentCount() > 0) {
            if (position >= 0 && position < getFragmentCount()) {
                return getData().get(position);
            }
            if (id > 0) {
                for (int i = 0; i < getFragmentCount(); i++) {
                    RadioButtonBean bean = getData().get(i);
                    if (bean != null && TextUtils.equals(id, bean.getId())) {
                        bean.setPosition(i);
                        return bean;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Fragment create(int position) {
        RadioButtonBean bean = getItem(-1, position);
        if (bean != null) {
            if (bean.getFragment() == null) {
                Fragment fragment = getPositionFragment(position);
                bean.setPosition(position);
                bean.setFragment(fragment);
            }
            return bean.getFragment();
        }
        return null;
    }

    @Override
    public void onCheckRadioGroup(int position) {
        if (fragmentIndex != position && TextUtils.isEmits(getContentLayout(), getPagerAdapter())) {
            FragmentTransaction transaction = getFragmentActivity().getSupportFragmentManager().beginTransaction();
            RadioButtonBean oldBean = getItem(-1, fragmentIndex);
            RadioButtonBean bean = getItem(-1, position);
            if (oldBean != null && oldBean.getFragment() != null) {
                transaction.hide(oldBean.getFragment());
            }
            if (bean != null) {
                getBottomGroup().check(bean.getId());
                Fragment fragment = create(position);
                fragmentIndex = position;
                if (fragment != null) {
                    getPagerAdapter().setPrimaryItem(null, 0, getPagerAdapter().instantiateItem(getContentLayout(), position));
                    getPagerAdapter().finishUpdate(null);
                    transaction.show(fragment);
                    notifyDataSetChanged();
                }
            }
            transaction.commit();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (getPagerAdapter() != null) {
            getPagerAdapter().notifyDataSetChanged();
        }
    }

}
