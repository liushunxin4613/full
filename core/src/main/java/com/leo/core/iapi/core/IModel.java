package com.leo.core.iapi.core;

/**
 * 数据
 */
public interface IModel extends IMNApi {

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