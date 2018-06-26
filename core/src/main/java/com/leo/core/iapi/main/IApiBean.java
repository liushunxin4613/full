package com.leo.core.iapi.main;

import com.leo.core.iapi.core.IApi;

public interface IApiBean<A extends IControllerApi, AA extends IControllerApi> extends IApi{

    /**
     * IControllerApi
     */
    A getControllerApi(AA api);

    /**
     * api type
     * @return api type
     */
    Integer getApiType();

    /**
     * api id
     * @return api id
     */
    Object getApiId();

}