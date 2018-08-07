package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.IconTvMoreControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconTvMoreNorm extends BaseTestNorm<IconTvMoreNorm> {

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new IconTvMoreControllerApi(controller);
    }

    public IconTvMoreNorm(Integer iconResId, String name, OnBVClickListener<IconTvMoreNorm> listener) {
        super(iconResId, name, listener);
    }

}