package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

import java.io.Serializable;

/**
 * id api
 */
public interface IIdApi<T extends IIdApi> extends IApi, Serializable {

    /**
     * 获取api id
     *
     * @return id
     */
    Object getApiId();

    /**
     * 设置api id
     *
     * @return id
     */
    T setApiId(Object id);

}
