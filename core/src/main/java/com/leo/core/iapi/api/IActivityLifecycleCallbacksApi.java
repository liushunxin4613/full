package com.leo.core.iapi.api;

import android.app.Activity;
import android.app.Application;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;

import java.util.Stack;

public interface IActivityLifecycleCallbacksApi extends IApi, Application.ActivityLifecycleCallbacks {
    Stack<Activity> getActivityStack();
    <A extends Activity> void remove(Class<A> clz, IObjAction<A> action);
}