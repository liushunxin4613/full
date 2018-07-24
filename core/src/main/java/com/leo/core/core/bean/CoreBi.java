package com.leo.core.core.bean;

import android.support.annotation.NonNull;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.iapi.main.IControllerApi;

import butterknife.ButterKnife;

public abstract class CoreBi<T extends CoreBi, B, A extends IControllerApi> extends VsApi<T> implements
        IBindControllerApi<A, B> {

    private B bean;
    private transient A api;
    private transient Integer layoutResId;

    @Override
    public Integer getLayoutResId() {
        return layoutResId == null ? getDefLayoutResId() : layoutResId;
    }

    @Override
    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    @Override
    public abstract Integer getDefLayoutResId();

    @Override
    public final A api() {
        return api;
    }

    @Override
    public B bean() {
        return bean;
    }

    protected void api(A api) {
        this.api = api;
    }

    protected void bean(B bean) {
        this.bean = bean;
    }

    @Override
    public void onBindBean(B bean) {
        checkException(bean);
        bean(bean);
    }

    @Override
    public void onBindApi(A api, B bean) {
        checkException(api);
        onBindBean(bean);
        api(api);
        ButterKnife.bind(this, api.getRootView());
        updateBind();
    }

    @Override
    public void updateBind() {
        if(api() != null && bean() != null){
            updateBind(api(), bean());
        }
    }

    @Override
    public void updateBind(@NonNull A api, @NonNull B bean) {
    }

    @Override
    public void onDestroyUnBind() {
        ButterKnife.unbind(this);
        api(null);
        bean(null);
    }

    private void checkException(A api) {
        if (api == null) {
            throw new NullPointerException("api不允许为空");
        }
        if (api.getRootView() == null) {
            throw new NullPointerException("api.getRootView()不允许为空");
        }
    }

    private void checkException(B bean) {
        if (bean == null) {
            throw new NullPointerException("bean不允许为空");
        }
    }

}