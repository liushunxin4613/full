package com.leo.core.api.core;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IAttachApi;

/**
 * 附加到对象类
 *
 * @param <T> 本身
 * @param <A> 被附加对象
 */
public class AttachApi<T extends AttachApi, A> extends GetAttachApi<T, A> implements IAttachApi<T, A> {

    private A obj;

    public AttachApi(A obj) {
        this.obj = obj;
    }

    @Override
    public A getAttach() {
        return obj;
    }

    @Override
    public T attach(@NonNull A obj) {
        this.obj = obj;
        return getThis();
    }

    @Override
    public T unAttach() {
        obj = null;
        return getThis();
    }

}
