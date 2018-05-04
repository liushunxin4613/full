package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IActionApi<T extends IActionApi, A extends IApi> extends IApi {

    /**
     * 当前时间
     * @return 当前时间
     */
    long currentTimeMillis();

    /**
     * action map
     * @return action map
     */
    Map<Long, List<A>> getActionMap();

    /**
     * new action map
     * @return action map
     */
    Map<Long, List<A>> newActionMap();

    /**
     * ui action map
     * @return action map
     */
    Map<Long, List<A>> getUIActionMap();

    /**
     * new ui action map
     * @return action map
     */
    Map<Long, List<A>> newUIActionMap();

    /**
     * cancel data
     * @return cancel data
     */
    Set<Integer> getCancelData();

    /**
     * new cancel data
     * @return cancel data
     */
    Set<Integer> newCancelData();

    /**
     * 设置时间间隔
     * @param interval 时间间隔
     * @return 本身
     */
    T setInterval(int interval);

    /**
     * 获取时间间隔
     * @return 时间间隔
     */
    int getInterval();

    /**
     * 添加事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int add(long time, A action);

    /**
     * 添加事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int add(long time, A action, int interval, int num);

    /**
     * 添加UI事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int addUI(long time, A action);

    /**
     * 添加UI事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int addUI(long time, A action, int interval, int num);

    /**
     * 加入事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int put(long time, A action);

    /**
     * 加入事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int put(long time, A action, int interval, int num);

    /**
     * 加入UI事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int putUI(long time, A action);

    /**
     * 加入UI事件
     * @param time 时间
     * @param action 事件
     * @return 本身
     */
    int putUI(long time, A action, int interval, int num);

    /**
     * 取消action
     * @param actionCode actionCode
     * @return 本身
     */
    T cancel(int actionCode);

    /**
     * 执行
     * @param action action
     * @return 本身
     */
    T action(A action);

    /**
     * 开始
     * @return 本身
     */
    T start();

    /**
     * 结束
     * @return 本身
     */
    T stop();

}
