package com.leo.core.core;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.main.IControllerApi;

public class MNApi<T extends MNApi, P extends IControllerApi> extends VsApi<T> implements IMNApi<P> {

    private transient P api;
    private transient Object apiId;
    private transient Object controller;

    @Override
    public Object getApiId() {
        if (apiId == null) {
            apiId = hashCode();
        }
        return apiId;
    }

    @Override
    public void setParentControllerApi(P api) {
        this.api = api;
    }

    @Override
    public P parentControllerApi() {
        return api;
    }

    @Override
    public void setController(Object controller) {
        this.controller = controller;
    }

    @Override
    public Object getController() {
        return controller;
    }

    @Override
    public String getApiCode() {
        return null;
    }

}
