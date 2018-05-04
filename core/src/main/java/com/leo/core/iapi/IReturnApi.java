package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * 返回回调
 *
 * @param <T>对象
 * @param <R>   返回泛型
 */
public interface IReturnApi<T, R> extends IApi {

    /**
     * 获得返回
     *
     * @param obj 传入数据
     * @return 返回数据
     */
    R execute(T obj);

}
