package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface IUserApi<T extends IUserApi> extends IApi {

    /**
     * 是否登录
     *
     * @return true为登录, false为非登录
     */
    boolean isLogin();

    /**
     * 退出登录
     *
     * @return 本身
     */
    T loginOut();

    /**
     * 初始化user
     *
     * @param user user
     * @param <A>  A
     * @return 本身
     */
    <A> T initUser(A user);

    /**
     * 获取user
     *
     * @param <A> A
     * @return A
     */
    <A> A getUser();

    /**
     * 获取department
     *
     * @return department
     */
    <A> A getDepartment();

    /**
     * 获取department
     *
     * @return department
     */
    String getDepartmentCode();

    /**
     * 获取uid
     *
     * @return uid
     */
    String getUId();

    /**
     * 获取username
     *
     * @return username
     */
    String getUserName();

    /**
     * 获取castgc
     * @return castgc
     */
    String getCastgc();

}