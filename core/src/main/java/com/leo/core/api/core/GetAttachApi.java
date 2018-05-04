package com.leo.core.api.core;

import com.leo.core.iapi.core.IGetAttachApi;

/**
 * 获取被附着物
 * @param <T> 本身
 * @param <A> 附着物
 */
public abstract class GetAttachApi<T extends GetAttachApi, A> extends ThisApi<T> implements IGetAttachApi<T, A> {

    @Override
    public abstract A getAttach();

}
