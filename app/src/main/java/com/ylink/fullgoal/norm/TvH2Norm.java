package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvH2ControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2Norm extends BaseTestNorm<TvH2Norm>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvH2ControllerApi(controller);
    }

    public TvH2Norm(String name, String detail) {
        super(name, detail);
    }

}
