package com.leo.core.iapi.core;

import android.content.res.XmlResourceParser;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.iapi.main.IControllerApi;

/**
 * 创建规范
 */
public interface INorm<P extends IControllerApi> extends IMNApi<P> {

    /**
     * 父容器
     */
    ViewGroup getViewGroup();

    /**
     * 设置ViewGroup
     * @param group group
     */
    void setViewGroup(ViewGroup group);

    /**
     * 当创建父容器时
     * @param api api
     */
    void onCreateViewGroup(IControllerApi api);

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
     * 设置apiType
     *
     * @param apiType apiType
     */
    void setApiType(Integer apiType);

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

    /**
     * 设置apiCode
     */
    void setApiCode(String apiCode);

}