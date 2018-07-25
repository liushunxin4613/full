package com.leo.core.core.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.iapi.main.IViewApiBean;

public abstract class CoreBindApiBean<T extends CoreBindApiBean, A extends IControllerApi,
        AA extends IControllerApi> extends CoreApiBean<T, A, AA> implements IViewApiBean<A, AA, T> {

    private transient IBindControllerApi<A, T> bindControllerApi;

    protected IBindControllerApi<A, T> newDefApi() {
        return null;
    }

    protected IBindControllerApi<A, T> api() {
        if (bindControllerApi == null) {
            bindControllerApi = newDefApi();
        }
        if (bindControllerApi != null) {
            bindControllerApi.onBindBean(getThis());
        }
        return bindControllerApi;
    }

    protected IBindControllerApi<A, T> api(IBindControllerApi<A, T> api) {
        return bindControllerApi = api;
    }

    @Override
    public Integer getApiType() {
        return getExecute(api(), IBindControllerApi::getLayoutResId);
    }

    @Override
    public void bind(IBindControllerApi<A, T> api) {
        api(api);
    }

    @Override
    public void unBind() {
        executeNon(api(), IBindControllerApi::onDestroyUnBind);
        api(null);
    }

    @Override
    public void onBindApi(A controllerApi) {
        executeNon(api(), api -> api.onBindApi(controllerApi, getThis()));
    }

    @Override
    public void updateBind() {
        executeNon(api(), IBindControllerApi::updateBind);
    }

}