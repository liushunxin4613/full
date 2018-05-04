package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IUserApi<T extends IUserApi> extends IApi {

    /**
     * 是否登录
     * @return true为登录,false为非登录
     */
    boolean isLogin();

    /**
     * 退出登录
     * @return 本身
     */
    T loginOut();

    /**
     * 初始化user
     * @param user user
     * @param <R> R
     * @return 本身
     */
    <R> T initUser(R user);

    /**
     * 获取user
     * @param <R> R
     * @return R
     */
    <R> R getUser();

    /**
     * 获取uid
     * @return uid
     */
    String getUId();

    /**
     * 获取username
     * @return username
     */
    String getUserName();

    /**
     * 获取token
     * @return token
     */
    String getToken();

    /**
     * 获取u time
     * @return u time
     */
    String getUTime();

}
