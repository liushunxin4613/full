package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

public class TestControllerApi<T extends TestControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

}