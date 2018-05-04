package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * 执行api
 *
 * @param <T>
 */
public interface IRunApi<T> extends IApi {
    void execute(T obj);
}
