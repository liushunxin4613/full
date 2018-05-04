package com.leo.core.iapi.core;

public interface IThisApi<T extends IThisApi> extends IApi {

    /**
     * 返回自身
     *
     * @return 本身
     */
    T getThis();

}
