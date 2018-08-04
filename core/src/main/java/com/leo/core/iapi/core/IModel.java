package com.leo.core.iapi.core;

import com.leo.core.iapi.api.IApiCodeApi;
import com.leo.core.iapi.main.IControllerApi;

/**
 * 数据
 */
public interface IModel<C extends IControllerApi> extends IApiCodeApi {

    /**
     * 设置api
     *
     * @param api api
     */
    void setControllerApi(C api);

    /**
     * api
     *
     * @return api
     */
    C controllerApi();

    /**
     * 初始化apiBean
     */
    void initControllerApiBean();

    /**
     * 设置apiBean
     *
     * @param apiBean apiBean
     */
    void setControllerApiBean(IControllerApiBean apiBean);

    /**
     * apiBean
     *
     * @return apiBean
     */
    IControllerApiBean controllerApiBean();

}