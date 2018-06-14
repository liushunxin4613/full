package com.ylink.fullgoal.controllerApi.surface;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class ViewPagerControllerApi<T extends ViewPagerControllerApi, C> extends SurfaceControllerApi<T, C> implements ViewPager.OnPageChangeListener {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private PagerAdapter adapter;

    public ViewPagerControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_view_pager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public <A extends PagerAdapter> A getPagerAdapter() {
        if (adapter == null) {
            adapter = newPagerAdapter();
            if (adapter == null) {
                throw new NullPointerException("newPagerAdapter 不能为空");
            }
        }
        return (A) adapter;
    }

    public <A extends PagerAdapter> A newPagerAdapter() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        getViewPager().addOnPageChangeListener(this);
        getViewPager().setAdapter(getPagerAdapter());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
