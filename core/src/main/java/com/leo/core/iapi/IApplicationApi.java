package com.leo.core.iapi;

import android.app.Application;
import android.support.annotation.CallSuper;

import com.leo.core.iapi.core.IApi;

/**
 * Application Api
 *
 * @param <T> 本身
 * @param <A> Application 子类
 */
public interface IApplicationApi<T extends IApplicationApi, A extends Application> extends IApi {

    @CallSuper
    void onCreate();

    @CallSuper
    void onTerminate();

    @CallSuper
    void onTrimMemory(int level);

    @CallSuper
    void onLowMemory();

}
