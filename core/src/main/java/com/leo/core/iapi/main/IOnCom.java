package com.leo.core.iapi.main;

/**
 * 命令
 */
public interface IOnCom {

    /**
     * 统一命令
     *
     * @param what 类型
     * @param com  命令
     * @param msg  消息
     * @param args 其他参数
     */
    void onCom(int what, String com, String msg, Object... args);

}