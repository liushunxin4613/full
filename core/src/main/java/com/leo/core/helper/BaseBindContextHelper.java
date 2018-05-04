package com.leo.core.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.leo.core.iapi.IBindContextApi;

public class BaseBindContextHelper<T extends BaseBindContextHelper> extends BaseHelper<T> implements IBindContextApi<T>{

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
