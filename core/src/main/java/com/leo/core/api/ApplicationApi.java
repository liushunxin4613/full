package com.leo.core.api;

import android.annotation.SuppressLint;
import android.app.Application;

import com.leo.core.api.main.ContextApi;
import com.leo.core.iapi.IApplicationApi;
import com.leo.core.iapi.ICApi;

@SuppressLint("MissingSuperCall")
public class ApplicationApi<T extends ApplicationApi, A extends Application> extends ContextApi<T, A>
        implements IApplicationApi<T, A>, ICApi<T> {

    public ApplicationApi(A context) {
        super(context);
    }

    protected Application getApplication(){
        return getAttach();
    }

    @Override
    public void onCreate() {
        init();
    }

    @Override
    public void onTerminate() {
        clear();
    }

    @Override
    public void onTrimMemory(int level) {
        clear();
    }

    @Override
    public void onLowMemory() {
        clear();
    }

    @Override
    public T init() {
        return getThis();
    }

    @Override
    public T clear() {
        return getThis();
    }

}
