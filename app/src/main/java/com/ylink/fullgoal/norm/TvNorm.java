package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvNorm extends BaseTestNorm<TvNorm>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvControllerApi(controller);
    }

    public TvNorm(String name) {
        super(name);
    }

    public TvNorm(String name, OnBVClickListener<TvNorm> listener) {
        super(name, listener);
    }

}
