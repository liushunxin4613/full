package com.leo.core.iapi.main;

import android.content.Context;

import com.leo.core.iapi.core.IAttachApi;

/**
 * Context Api
 *
 * @param <T> 本身
 */
public interface IContextApi<T extends IContextApi, C extends Context> extends IAttachApi<T, C> {

    /**
     *  获取上下文
     * @return 上下文
     */
    Context getContext();

    /**
     * 获取整个app上下文
     * @return 整个app上下文
     */
    Context getApplicationContext();

}
