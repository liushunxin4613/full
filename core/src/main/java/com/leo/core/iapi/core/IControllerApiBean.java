package com.leo.core.iapi.core;

import android.content.res.XmlResourceParser;

import com.leo.core.iapi.main.IControllerApi;

/**
 * 创建ControllerApiBean
 */
public interface IControllerApiBean<P extends IControllerApi> extends IApi {

    /**
     * 验证合法性
     *
     * @return true:合法;false:不合法;
     */
    boolean apiCheck();

    /**
     * 设置parentControllerApi
     */
    void setParentControllerApi(P parentControllerApi);

    /**
     * 基类parentControllerApi
     *
     * @return parentControllerApi
     */
    P parentControllerApi();

    /**
     * 创建controllerApi[可在adapter中处理]
     */
    void initControllerApi();

    /**
     * 设置IControllerApi
     *
     * @param api api
     */
    void setControllerApi(IControllerApi api);

    /**
     * controllerApi
     */
    IControllerApi controllerApi();

    /**
     * XmlResourceParser
     *
     * @return XmlResourceParser
     */
    XmlResourceParser getApiXmlResourceParser();

    /**
     * 设置 XmlResourceParser
     */
    void setApiXmlResourceParser(XmlResourceParser parser);

    /**
     * api id
     *
     * @return api id
     */
    Object getApiId();

    /**
     * api type
     *
     * @return api type
     */
    Integer getApiType();

    /**
     * api type
     *
     * @return api type
     */
    Integer getDefApiType();

    /**
     * 编号,数据对比使用[可用于标记和过滤]
     */
    String getApiCode();

    /**
     * 初始化搜索
     */
    void initApiSearch();

    /**
     * 设置搜索数据
     *
     * @param apiSearch apiSearch
     */
    void setApiSearch(String apiSearch);

    /**
     * api search[用于搜索处理]
     */
    String getApiSearch();

}