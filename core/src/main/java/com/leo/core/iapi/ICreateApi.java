package com.leo.core.iapi;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IApi;

/**
 * create api
 *
 * @param <T> 本身
 * @param <I> 输入数据
 * @param <P> 输入参数
 */
public interface ICreateApi<T extends ICreateApi, I, P> extends IApi {

    /**
     * 创建
     *
     * @param in   输入数据
     * @param item 输入参数
     * @return 返回数据
     */
    <R extends P> R create(@NonNull I in, @NonNull R item);

    /**
     * 获取item
     *
     * @param in 传入数据
     * @return 返回参数
     */
    <R extends P> R getItem(@NonNull I in);

    /**
     * 获取item key值
     *
     * @param item 传入数据
     * @return key值
     */
    <R extends P> I getItemKey(@NonNull R item);

    /**
     * 清除数据
     *
     * @return 本身
     */
    T clear();

}
