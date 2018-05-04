package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

/**
 * 编解码接口
 *
 * @param <T>  本身
 * @param <EI> 编码输入数据
 * @param <ER> 编码返回数据
 * @param <DI> 解码输入数据
 * @param <DP> 解码参数
 */
public interface IDecodeApi<T extends IDecodeApi, EI, ER, DI, DP> extends IApi {

    /**
     * 编码
     *
     * @param in 输入数据
     * @return 返回数据
     */
    ER encode(EI in);

    /**
     * 解码
     *
     * @param in    输入数据
     * @param param 输入参数
     * @return 返回数据
     */
    <R> R decode(DI in, DP param);

}
