package com.leo.core.api.core;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IBindApi;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定类
 *
 * @param <B> 绑定数据
 */
public class BindApi<T extends BindApi, B> extends GetBindApi<T, B> implements IBindApi<T, B> {

    private List<B> data;

    public BindApi(@NonNull T api) {
        data = new ArrayList<>();
        onBind();
    }

    @NonNull
    @Override
    public List<B> getBindData() {
        return data;
    }

    @Override
    public void onBind() {}

    @Override
    public T bind(@NonNull B bind) {
        if (data != null) {
            data.add(bind);
        }
        return getThis();
    }

    @Override
    public T unBind(B bind) {
        if (bind != null && data != null) {
            data.remove(bind);
        }
        return getThis();
    }

}
