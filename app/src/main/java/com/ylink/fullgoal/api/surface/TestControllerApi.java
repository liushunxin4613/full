package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
    }

}
