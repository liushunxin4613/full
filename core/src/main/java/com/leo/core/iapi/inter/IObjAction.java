package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

/**
 * 执行api
 *
 * @param <T>
 */
public interface IObjAction<T> extends IApi {
    void execute(T obj);
}
