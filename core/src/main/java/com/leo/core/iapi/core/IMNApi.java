package com.leo.core.iapi.core;

import com.leo.core.iapi.api.IApiCodeApi;
import com.leo.core.iapi.main.IControllerApi;

/**
 * IModel和INorm的基类
 */
public interface IMNApi<P extends IControllerApi> extends IApiCodeApi {

    /**
     * api id
     *
     * @return api id
     */
    Object getApiId();

    /**
     * 设置parentControllerApi
     */
    void setParentControllerApi(P api);

    /**
     * 基类parentControllerApi
     *
     * @return parentControllerApi
     */
    P parentControllerApi();

    /**
     * 设置controller
     */
    void setController(Object controller);

    /**
     * controller
     */
    Object getController();

}