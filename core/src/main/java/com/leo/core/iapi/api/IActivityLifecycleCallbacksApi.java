package com.leo.core.iapi.api;

import android.app.Activity;
import android.app.Application;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IbolAction;
import com.leo.core.iapi.main.IControllerApi;

import java.util.Stack;

public interface IActivityLifecycleCallbacksApi extends IApi, Application.ActivityLifecycleCallbacks {
    Stack<Activity> getActivityStack();

    <A extends Activity> void remove(Class<A> clz, IObjAction<A> action);

    int getCount();

    Activity getItem(int position);

    void finishActivity(IbolAction<Activity> action);

    void finishActivity(Object obj);

    void finishActivity(Class<? extends IControllerApi> clz, Object obj);

    void finishActivity(Object obj, Class<? extends IControllerApi>... args);

    void finishAllActivity();
}