package com.leo.core.iapi;

public interface IOnErrorApi {
    /**
     * 异常数据
     */
    void onError(Throwable e, int what, String msg);
}
