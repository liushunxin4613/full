package com.leo.core.core.bean;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.core.IControllerApiBean;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.main.IControllerApi;

public class Model<C extends IControllerApi> extends VsApi<Model> implements IModel<C>{

    private transient C controllerApi;
    private transient IControllerApiBean apiBean;

    @Override
    public void setControllerApi(C api) {
        this.controllerApi = api;
    }

    @Override
    public C controllerApi() {
        return controllerApi;
    }

    @Override
    public void initControllerApiBean() {
    }

    @Override
    public void setControllerApiBean(IControllerApiBean apiBean) {
        this.apiBean = apiBean;
    }

    @Override
    public IControllerApiBean controllerApiBean() {
        return apiBean;
    }

    @Override
    public String getApiCode() {
        return null;
    }

}