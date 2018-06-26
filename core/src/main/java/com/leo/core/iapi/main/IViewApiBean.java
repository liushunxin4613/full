package com.leo.core.iapi.main;

public interface IViewApiBean<A extends IControllerApi, AA extends IControllerApi, B extends
        IViewApiBean> extends IApiBean<A, AA> {

    /**
     * 当数据绑定时
     */
    void onBindApi(A api);

    /**
     * 绑定
     */
    void bind(IBindControllerApi<A, B> api);

    /**
     * 解除绑定
     */
    void unBind();

}