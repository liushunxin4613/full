package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.IconTvMoreBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconTvMoreBean extends ApiBean<IconTvMoreBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, IconTvMoreBean> newDefApi() {
        return new IconTvMoreBi();
    }

    public IconTvMoreBean(Integer iconResId, String name, OnBVClickListener<IconTvMoreBean> listener) {
        super(iconResId, name, listener);
    }

    @Override
    public boolean isEnable() {
        return true;
    }

}
