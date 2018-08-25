package com.leo.core.iapi.core;

import com.leo.core.iapi.api.IApiIdApi;
import com.leo.core.iapi.api.ISelectedApiCodeApi;
import com.leo.core.iapi.api.IViewTypeApi;
import com.leo.core.iapi.main.IControllerApi;

/**
 * IModel和INorm的基类
 */
public interface IMNApi<P extends IControllerApi> extends IApiIdApi, ISelectedApiCodeApi, IViewTypeApi {

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

    /**
     * 搜索内容
     * @return 内容
     */
    String getSearchText();

    /**
     * 设置搜索内容
     * @param searchText 内容
     */
    void setSearchText(String searchText);

    /**
     * 创建搜索内容
     */
    void createSearchText();

    /**
     * 延迟处理
     */
    void lazy();

}