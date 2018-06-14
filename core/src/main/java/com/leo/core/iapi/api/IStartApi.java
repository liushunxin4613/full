package com.leo.core.iapi.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.main.IControllerApi;

public interface IStartApi<T extends IStartApi> extends IApi{

    String CONTROLLER_API = "controllerApi";
    String ROOT_VIEW_CLZ_API = "rootViewClzApi";

    Intent getIntent(Class<? extends Activity> clz, Class<? extends IControllerApi>... args);

    /**
     * 开启
     * @param clz clz
     * @param args args
     * @return 本身
     */
    T startActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args);

    Intent getIntent(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args);

    /**
     * 开启
     * @param clz clz
     * @param bundle bundle
     * @param args args
     * @return 本身
     */
    T startActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args);

    /**
     * 开启并结束
     * @param clz clz
     * @param args args
     * @return 本身
     */
    T startFinishActivity(Class<? extends Activity> clz, Class<? extends IControllerApi>... args);

    /**
     * 开启并结束
     * @param clz clz
     * @param bundle bundle
     * @param args args
     * @return 本身
     */
    T startFinishActivity(Class<? extends Activity> clz, Bundle bundle, Class<? extends IControllerApi>... args);

    Bundle getBundle(Class<? extends IControllerApi>... args);

    /**
     * 获取Fragment
     * @param clz clz
     * @param args args
     * @return 本身
     */
    Fragment getFragment(Class<? extends Fragment> clz, Class<? extends IControllerApi>... args);

    Bundle getBundle(Bundle bundle, Class<? extends IControllerApi>... args);

    /**
     * 获取Fragment
     * @param clz clz
     * @param bundle bundle
     * @param args args
     * @return 本身
     */
    Fragment getFragment(Class<? extends Fragment> clz, Bundle bundle, Class<? extends IControllerApi>... args);

}
