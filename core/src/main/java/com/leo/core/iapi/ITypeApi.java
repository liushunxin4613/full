package com.leo.core.iapi;

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
