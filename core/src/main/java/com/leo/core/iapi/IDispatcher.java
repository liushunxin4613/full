package com.leo.core.iapi;

public interface IDispatcher extends IOnCompletedApi, IOnErrorApi {

    /**
     * 分发器入口
     *
     * @param obj 对象
     */
    void onDispatcher(Object obj);

    /**
     * 预处理
     *
     * @param obj 对象
     */
    void onStart(Object obj);

    /**
     * 处理下级数据
     *
     * @param obj 数据
     * @param <D> 下级数据泛型
     */
    <D> void onData(D obj);

    /**
     * 异常数据
     */
    @Override
    void onError(Throwable e, int what, String msg);

    /**
     * 结束本次请求
     */
    @Override
    void onCompleted();

}
