package com.leo.core.iapi.main;

import android.content.res.XmlResourceParser;

import com.leo.core.iapi.core.IApi;

public interface IApiBean<A extends IControllerApi, AA extends IControllerApi> extends IApi{

    /**
     * IControllerApi
     */
    A getControllerApi(AA api);

    /**
     * 验证合法性
     * @return true:合法;false:不合法;
     */
    boolean apiCheck();

    /**
     * XmlResourceParser
     * @return XmlResourceParser
     */
    XmlResourceParser getApiXmlResourceParser();

    /**
     * 设置 XmlResourceParser
     */
    void setApiXmlResourceParser(XmlResourceParser parser);

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