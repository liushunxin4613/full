package com.leo.core.core;

import android.app.Application;
import android.content.Context;

import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

public class BaseControllerApiApp<T extends BaseControllerApiApp, C extends IControllerApi> extends Application implements IAFVApi<T, C> {

    private IControllerApi controllerApi;

    @Override
    public IControllerApi<C, T> controllerApi() {
        if(controllerApi == null){
            controllerApi = newControllerApi();
            if(controllerApi == null){
                throw new NullPointerException("newControllerApi 不能为空");
            }
        }
        return controllerApi;
    }

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return new BaseControllerApi(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        execute(controllerApi(), api -> api.attachBaseContext(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        execute(controllerApi(), api -> api.onCreate(null));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        execute(controllerApi(), IControllerApi::onTerminate);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        execute(controllerApi(), api -> api.onTrimMemory(level));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        execute(controllerApi(), IControllerApi::onLowMemory);
    }

    protected <R> void execute(R obj, IRunApi<R> api){
        RunUtil.executeNon(obj, api);
    }

}
