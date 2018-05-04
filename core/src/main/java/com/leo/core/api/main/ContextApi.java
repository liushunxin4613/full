package com.leo.core.api.main;

import android.content.Context;
import android.support.annotation.NonNull;
import com.leo.core.api.core.AttachApi;
import com.leo.core.iapi.main.IContextApi;

/**
 * Context Api 类
 *
 * @param <T> 本身
 * @param <C> Context 子类
 */
public class ContextApi<T extends ContextApi, C extends Context> extends AttachApi<T, C> implements IContextApi<T, C> {

    public ContextApi(C context) {
        super(context);
    }

    @Override
    public T attach(@NonNull C context) {
        return super.attach(context);
    }

    @Override
    public Context getContext() {
        return getAttach();
    }

    @Override
    public Context getApplicationContext(){
        return (getContext() != null) ? getContext().getApplicationContext() : null;
    }

}
