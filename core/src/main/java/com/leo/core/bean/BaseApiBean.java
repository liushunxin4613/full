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
    private transient boolean enable;

    @Override
    public abstract Integer getApiType();

    @Override
    public Object getApiId() {
        return apiId;
    }

    public BaseApiBean() {
    }

    public BaseApiBean(IControllerApi api) {
        this.api = api;
    }

    public IControllerApi getApi() {
        return api;
    }

    public void setApi(IControllerApi api) {
        this.api = api;
    }

    public boolean isEnable() {
        return enable;
    }

    public BaseApiBean setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    protected  <B> B getEnable(B a, B b) {
        return isEnable() ? a : b;
    }

}
