package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

/**
 * 返回回调
 *
 * @param <A>对象
 * @param <B>   返回泛型
 */
public interface IReturnEntryAction<A, K, B> extends IApi {

    /**
     * 获得返回
     *
     * @param obj 传入数据
     * @return 返回数据
     */
    B execute(K key, A obj);

}
