package com.leo.core.bean;

import com.leo.core.iapi.main.IApiBean;

/**
 * 基础ApiBean
 */
public abstract class BaseApiBean implements IApiBean {

    @Override
    public abstract Integer getApiType();

    @Override
    public Object getApiId() {
        return hashCode();
    }

}
