package com.ylink.fullgoal.core;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class SurfaceBiBean<T extends SurfaceBiBean> extends BaseBiBean<T, SurfaceControllerApi> {

    @Override
    public SurfaceControllerApi getControllerApi(SurfaceControllerApi api) {
        return new SurfaceControllerApi(api.getController());
    }

}