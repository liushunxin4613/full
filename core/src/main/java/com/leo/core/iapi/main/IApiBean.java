package com.leo.core.iapi.main;

import com.leo.core.iapi.core.IApi;

public interface IApiBean extends IApi{

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
