package com.leo.core.iapi.main;

import android.support.annotation.NonNull;

import com.leo.core.iapi.IDecodeApi;
import com.leo.core.iapi.core.IApi;

public interface IShowApi<T extends IShowApi> extends IApi {

    /**
     * 解析Api
     * @return
     */
    IDecodeApi decodeApi();

    /**
     * new解析Api
     * @return
     */
    IDecodeApi newDecodeApi();

    /**
     * 编码
     * @param obj 数据
     * @return string
     */
    String encode(Object obj);

    /**
     * 解码
     * @param in 输入
     * @param param 参数
     * @param <R> 返回泛型
     * @return 返回
     */
    <R> R decode(Object in, Object param);

    /**
     * 提示
     * @param text text
     * @return 本身
     */
    T show(@NonNull CharSequence text);

    /**
     * log
     * @param obj obj
     * @return log
     */
    String getLog(Object obj);

    /**
     * i级日志
     * @param obj 对象
     * @return 本身
     */
    T i(Object obj);

    /**
     * i级日志
     * @param text 名称
     * @param obj 对象
     * @return 本身
     */
    T i(CharSequence text, Object obj);

    /**
     * e级日志
     * @param obj 对象
     * @return 本身
     */
    T e(Object obj);

    /**
     * e级日志
     * @param text 名称
     * @param obj 对象
     * @return 本身
     */
    T e(CharSequence text, Object obj);

}
