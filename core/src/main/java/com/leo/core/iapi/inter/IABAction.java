package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

/**
 * 执行api
 */
public interface IABAction<A, B> extends IApi {
    void execute(A a, B b);
}
