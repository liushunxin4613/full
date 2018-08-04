package com.leo.core.core.bean;

import android.content.res.XmlResourceParser;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.core.IControllerApiBean;
import com.leo.core.iapi.main.IControllerApi;

/**
 * 创建和处理IControllerApi
 */
public class ControllerApiBean<P extends IControllerApi> extends VsApi implements IControllerApiBean<P> {

    private transient Object apiId;
    private transient Integer apiType;
    private transient String apiSearch;
    private transient P parentControllerApi;
    private transient IControllerApi controllerApi;
    private transient XmlResourceParser xmlResourceParser;

    @Override
    public boolean apiCheck() {
        return getApiType() != null || getApiXmlResourceParser() != null;
    }

    @Override
    public void setParentControllerApi(P parentControllerApi) {
        this.parentControllerApi = parentControllerApi;
    }

    @Override
    public P parentControllerApi() {
        return parentControllerApi;
    }

    @Override
    public void initControllerApi() {
    }

    @Override
    public void setControllerApi(IControllerApi api) {
        this.controllerApi = api;
    }

    @Override
    public IControllerApi controllerApi() {
        return controllerApi;
    }

    @Override
    public XmlResourceParser getApiXmlResourceParser() {
        return xmlResourceParser;
    }

    @Override
    public void setApiXmlResourceParser(XmlResourceParser parser) {
        this.xmlResourceParser = parser;
    }

    @Override
    public Object getApiId() {
        if (apiId == null) {
            apiId = hashCode();
        }
        return apiId;
    }

    @Override
    public Integer getApiType() {
        if (apiType == null) {
            return getDefApiType();
        }
        return apiType;
    }

    @Override
    public Integer getDefApiType() {
        return null;
    }

    @Override
    public String getApiCode() {
        return null;
    }

    @Override
    public void initApiSearch() {
    }

    @Override
    public void setApiSearch(String apiSearch) {
        this.apiSearch = apiSearch;
    }

    @Override
    public String getApiSearch() {
        return apiSearch;
    }

}