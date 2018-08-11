package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvHTv3ControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHTv3Norm extends BaseTestNorm<TvHTv3Norm>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvHTv3ControllerApi(controller);
    }

    public TvHTv3Norm(String name, String detail) {
        super(name, detail);
    }

}