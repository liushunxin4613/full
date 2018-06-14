package com.leo.core.iapi.api;

/**
 * type api
 *
 * @param <T>
 */
public interface ITypeApi<T extends ITypeApi> extends IIdApi<T> {

    /**
     * 获得api type
     *
     * @return type
     */
    int getApiType();

}
