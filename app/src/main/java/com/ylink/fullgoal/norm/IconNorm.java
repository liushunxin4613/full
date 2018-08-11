package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.IconControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconNorm extends BaseTestNorm<IconNorm>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new IconControllerApi(controller);
    }

    public IconNorm(OnBVClickListener<IconNorm> listener) {
        super(listener);
    }

}