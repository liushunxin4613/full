package com.leo.core.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.IBindContextApi;

/**
 * 公共管理类
 */
public class MainManage {

    @SuppressLint("StaticFieldLeak")
    private static Context context;//解析类

    public static void init(@NonNull Application context) {
        MainManage.context = context;
    }

    public static Context getApplicationContext(){
        return context;
    }

    /**
     * 按照clz名称返回IContext子类
     *
     * @param clz IContext子类名
     * @param <T> 继承于IContext的类
     * @return IContext子类
     */
    public static <T extends IBindContextApi> T getContextApi(Context context, @NonNull Class<T> clz) {
        if (context == null){
            throw new NullPointerException("MainManage 没有 init(Application context)");
        }
        return ApiFactory.getInstance().getApi(clz, context);
    }

    public static <T extends IBindContextApi> T getContextApi(@NonNull Class<T> clz) {
        return getContextApi(context, clz);
    }

    /**
     * 按照clz名称返回IBase子类
     *
     * @param clz IBase子类名
     * @param <T> 继承于IBase的类
     * @return IBase子类
     */
    public static <T extends IApi> T getApi(@NonNull Class<T> clz) {
        return ApiFactory.getInstance().getApi(clz);
    }

}
