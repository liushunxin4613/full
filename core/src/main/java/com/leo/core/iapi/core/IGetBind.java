package com.leo.core.iapi.core;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 获取绑定Api,可有多个
 *
 * @param <T> 本身
 * @param <B> 绑定数据
 */
public interface IGetBind<T extends IGetBind, B> extends IApi {

    /**
     * 获取绑定目标
     * @return 绑定目标
     */
    @NonNull
    List<B> getBindData();

}
