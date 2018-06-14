package com.leo.core.iapi.api;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.core.IApi;

public interface ILoadApi<T extends ILoadApi> extends IApi{

    enum State{
        SUCCESS,
        NULL,
        ERROR,
    }

    /**
     * 初始化
     * @return 本身
     */
    T init();

    /**
     * CoreControllerApi
     * @return CoreControllerApi
     */
    CoreControllerApi controllerApi();

    /**
     * IContentApi
     * @return IContentApi
     */
    IContentApi contentApi();

    /**
     * 是否是开始状态
     * @return true为开始,false为结束
     */
    boolean isStart();

    /**
     * 是否是刷新状态
     * @return true为刷新,false为加载
     */
    boolean isRefresh();

    /**
     * 数据长度
     * @return count
     */
    int getDataCount();

    /**
     * 加载过程中
     * @param refresh refresh
     * @return 本身
     */
    void load(boolean refresh);

    /**
     * 加载状态
     * @param refresh refresh
     * @param state state
     * @param msg 状态消息
     * @return 本身
     */
    T onLoad(boolean refresh, State state, String msg);

    /**
     * 执行结束
     */
    void onCompleted();

}
