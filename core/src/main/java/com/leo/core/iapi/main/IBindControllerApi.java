package com.leo.core.iapi.main;

import com.leo.core.iapi.core.IApi;

public interface IBindControllerApi<A extends IControllerApi, B> extends IApi{

    /**
     * 布局id
     */
    Integer getLayoutResId();

    /**
     * 设置布局id
     */
    void setLayoutResId(int layoutResId);

    /**
     * 默认布局id
     */
    Integer getDefLayoutResId();

    B bean();

    A api();

    void onBindBean(B bean);

    void onBindApi(A api, B bean);

    void onDestroyUnBind();

}