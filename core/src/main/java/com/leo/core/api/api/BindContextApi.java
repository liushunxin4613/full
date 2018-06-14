package com.leo.core.api.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.IBindContextApi;

public class BindContextApi<T extends BindContextApi> extends ThisApi<T> implements IBindContextApi<T> {

    private Context context;

    @Override
    public T bind(@NonNull Context context) {
        this.context = context;
        return getThis();
    }

    @Override
    public Context getContext() {
        return context;
    }

}
