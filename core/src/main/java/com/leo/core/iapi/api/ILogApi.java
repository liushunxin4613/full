package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

/**
 * log Api
 *
 * @param <T> 本身泛型
 * @param <I> 输入数据泛型
 * @param <P> 输入参数反型
 */
public interface ILogApi<T extends ILogApi, I, P> extends IApi {

    /**
     * 打开全局log
     *
     * @return 本身
     */
    T openLog();

    /**
     * 关闭本类log
     *
     * @param in 输入数据
     * @return 本身
     */
    T closeThisLog(I in);

    /**
     * 关闭I级log
     *
     * @return 本身
     */
    T closeILog();

    /**
     * 关闭E级log
     *
     * @return 本身
     */
    T closeELog();

    /**
     * 获取非空log
     *
     * @param obj 类
     * @return 非空log
     */
    String getLog(P obj);

    /**
     * 获取非空log
     *
     * @param obj 类
     * @return 非空log
     */
    String getLog(P... obj);

    /**
     * I 级输出
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 本身
     */
    T ii(I in, P param);

    /**
     * I 级输出
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 本身
     */
    T ii(I in, P... param);

    /**
     * E 级输出
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 本身
     */
    T ee(I in, P param);

    /**
     * E 级输出
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 本身
     */
    T ee(I in, P... param);

}
