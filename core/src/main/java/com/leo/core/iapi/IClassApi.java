package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * class 辅助接口
 *
 * @param <T> 本身泛型
 * @param <I> 输入数据泛型
 * @param <P> 输入参数泛型
 * @param <R> 返回数据泛型
 */
public interface IClassApi<T extends IClassApi, I, P, R> extends IApi {

    /**
     * 获取类名
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 返回数据
     */
    R getClassName(I in, P param);

}
