package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RefreshRecycleBarControllerApi;

public class MyControllerApi<T extends MyControllerApi, C> extends SurfaceControllerApi<T, C> {

    public MyControllerApi(C controller) {
        super(controller);
    }

}