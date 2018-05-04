package com.leo.core.api;

import android.support.annotation.NonNull;

import com.leo.core.core.MainManage;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.IBindContextApi;
import com.leo.core.iapi.IClassBindApi;

public class ClassBindImplApi<C extends IApi> implements IClassBindApi<ClassBindImplApi, C> {

    private Class<? extends C> defaultClz;
    private Class<? extends C> clz;

    public ClassBindImplApi(@NonNull Class<? extends C> defaultClz) {
        this.defaultClz = defaultClz;
    }

    @Override
    public ClassBindImplApi bind(@NonNull Class<? extends C> clz) {
        this.clz = clz;
        return this;
    }

    @Override
    public ClassBindImplApi bindDefault(@NonNull Class<? extends C> clz) {
        this.defaultClz = clz;
        return this;
    }

    @NonNull
    @Override
    public Class<? extends C> getDefaultClz() {
        return defaultClz;
    }

    @NonNull
    @Override
    public Class<? extends C> getClz() {
        if (clz == null)
            return defaultClz;
        return clz;
    }

    @NonNull
    @Override
    public Class<? extends C> getClz(Class<? extends C> clz) {
        bind(clz);
        return getClz();
    }

    @Override
    public C getApi() {
        Class<? extends C> clz = getClz();
        if (IBindContextApi.class.isAssignableFrom(clz)) {
            return (C) MainManage.getContextApi((Class<? extends IBindContextApi>) clz);
        }
        return MainManage.getApi(clz);
    }

}
