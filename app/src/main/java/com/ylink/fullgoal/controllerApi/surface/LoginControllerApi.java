package com.ylink.fullgoal.controllerApi.surface;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class LoginControllerApi<T extends LoginControllerApi, C> extends SurfaceControllerApi<T, C> {

    public LoginControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        statusBar(true);
    }

}
