package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * init和clear接口
 *
 * @param <T>
 */
public interface ICApi<T extends ICApi> extends IApi {

    /**
     * 初始化
     *
     * @return 本身
     */
    T init();

    /**
     * 清理
     *
     * @return 本身
     */
    T clear();

}
