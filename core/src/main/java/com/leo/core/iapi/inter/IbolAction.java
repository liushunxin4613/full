package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

/**
 * 执行api
 *
 */
public interface IbolAction<A> extends IApi {
    boolean execute(A obj);
}
