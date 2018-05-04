package com.ylink.fullgoal.main;

import com.leo.core.core.BaseControllerApiActivity;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.SplashControllerApi;

public class SplashActivity<T extends SplashActivity, C extends IControllerApi> extends BaseControllerApiActivity<T, C> {

    @Override
    public IControllerApi newControllerApi() {
        return new SplashControllerApi(this);
    }

}
