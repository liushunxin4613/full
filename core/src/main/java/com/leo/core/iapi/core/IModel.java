package com.leo.core.iapi.core;

import com.leo.core.iapi.main.IControllerApi;

/**
 * 数据
 */
public interface IModel<P extends IControllerApi> extends IMNApi<P> {

    /**
     * 初始化norm
     */
    void initNorm();

    /**
     * 设置apiBean
     *
     * @param norm norm
     */
    void setNorm(INorm norm);

    /**
     * apiBean
     *
     * @return apiBean
     */
    INorm norm();

}