package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * 信息传递通用Api
 */
public interface IInfoApi<T> extends IApi {

    /**
     * 请求成功返回固定值
     */
    int CODE_SUCCESS = 200;

    /**
     * 信息回调
     * @param requestCode 来源码
     * @param code 信息码
     * @param message 消息,一般作为解释和提示
     * @param clz 信息辅助解析格式
     * @param obj 对象
     * @param t 当有异常信息时采用
     */
    void onInfo(int requestCode, int code, String message, Class clz, Object obj, Throwable t);

}
