package com.leo.core.util;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.leo.core.api.api.ToastImplApi;
import com.leo.core.core.MainManage;

/**
 * Toast工具类
 */
public class ToastUtil {

    public static void show(Object thisObj, @NonNull CharSequence text) {
        MainManage.getContextApi(ToastImplApi.class).show(thisObj, text);
    }

    public static void show(Object thisObj, @StringRes int resId) {
        MainManage.getContextApi(ToastImplApi.class).show(thisObj, resId);
    }

    public static void show(@NonNull CharSequence text) {
        MainManage.getContextApi(ToastImplApi.class).show(null, text);
    }

    public static void show(@StringRes int resId) {
        MainManage.getContextApi(ToastImplApi.class).show(null, resId);
    }

}