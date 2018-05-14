package com.leo.core.bean;

import com.leo.core.iapi.main.IApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;

/**
 * 基础ApiBean
 */
public abstract class BaseApiBean implements IApiBean {

    private transient Object apiId = TextUtils.getRandom();
    private transient IControllerApi api;

    @Override
    public abstract Integer getApiType();

    @Override
    public Object getApiId() {
        return apiId;
    }

    public IControllerApi getApi() {
        return api;
    }

    public void setApi(IControllerApi api) {
        this.api = api;
    }
}
