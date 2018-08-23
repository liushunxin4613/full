package com.ylink.fullgoal.main;

import com.leo.core.core.BaseControllerApiApp;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.core.AppControllerApi;

public class App extends BaseControllerApiApp {

    @Override
    public IControllerApi newControllerApi() {
        return new AppControllerApi(this);
    }

}