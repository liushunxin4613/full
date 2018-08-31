package com.leo.core.core;

import android.view.ViewGroup;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.TextUtils;

/**
 * 创建和处理Norm
 */
public abstract class Norm<T extends Norm, P extends IControllerApi> extends MNApi<T, P> implements INorm<P> {

    private transient String xml;
    private transient int position;
    private transient String apiCode;
    private transient ViewGroup group;
    private transient Integer apiType;
    private transient String apiSearch;
    private transient String viewJsonName;
    private transient IControllerApi controllerApi;

    @Override
    public Integer getViewType() {
        String type = "";
        Class<? extends IControllerApi> clz = getControllerApiClass();
        if (clz != null) {
            type += "&" + clz.getName();
        }
        if (getApiType() != null) {
            type += "&" + getApiType();
        } else if (!TextUtils.isEmpty(getRootViewXml())) {
            type += "&" + getRootViewXml();
        }
        return type.hashCode();
    }

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
    public IControllerApi createControllerApi() {
        Class<? extends IControllerApi> clz = getControllerApiClass();
        if (clz == null) {
            throw new NullPointerException("getControllerApiClass()不能为空");
        }
        IControllerApi api = (IControllerApi) ObjectUtil.getObject(clz,
                Object.class, getController());
        api.setRootViewJsonName(getRootViewJsonName());
        api.setRootViewXml(getRootViewXml());
        api.setRootViewResId(getApiType());
        return api;
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
    public String getRootViewJsonName() {
        return viewJsonName;
    }

    @Override
    public void setRootViewJsonName(String jsonName) {
        this.viewJsonName = jsonName;
    }

    @Override
    public String getRootViewXml() {
        return xml;
    }

    @Override
    public void setRootViewXml(String xml) {
        this.xml = xml;
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