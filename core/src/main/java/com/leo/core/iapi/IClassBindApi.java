package com.leo.core.iapi;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IApi;

/**
 * 绑定class
 *
 * @param <T> 本身泛型
 * @param <C> IApi子类泛型
 */
public interface IClassBindApi<T extends IClassBindApi, C extends IApi> extends IApi {

    /**
     * 绑定class
     *
     * @param clz 类
     * @return 本身
     */
    T bind(@NonNull Class<? extends C> clz);

    /**
     * 绑定class
     *
     * @param clz 类
     * @return 本身
     */
    T bindDefault(@NonNull Class<? extends C> clz);

    /**
     * 获取默认类
     *
     * @return 非空类
     */
    @NonNull
    Class<? extends C> getDefaultClz();

    /**
     * 获取类
     *
     * @return 非空类
     */
    @NonNull
    Class<? extends C> getClz();

    /**
     * 获取类
     *
     * @param clz 类
     * @return 类
     */
    @NonNull
    Class<? extends C> getClz(Class<? extends C> clz);

    /**
     * 获取api
     *
     * @return api
     */
    C getApi();

}
