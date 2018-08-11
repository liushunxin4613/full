package com.leo.core.core;

import android.content.res.XmlResourceParser;
import android.view.ViewGroup;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;

/**
 * 创建和处理Norm
 */
public class Norm<T extends Norm, P extends IControllerApi> extends MNApi<T, P> implements INorm<P> {

    private transient int position;
    private transient String apiCode;
    private transient ViewGroup group;
    private transient Integer apiType;
    private transient String apiSearch;
    private transient IControllerApi controllerApi;
    private transient XmlResourceParser xmlResourceParser;

    @Override
    public ViewGroup getViewGroup() {
        return group;
    }

    @Override
    public void setViewGroup(ViewGroup group) {
        this.group = group;
    }

    @Override
    public void onCreateViewGroup(IControllerApi api) {
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
    public Integer getApiType() {
        return apiType;
    }

    @Override
    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    @Override
    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getApiCode() {
        return apiCode;
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