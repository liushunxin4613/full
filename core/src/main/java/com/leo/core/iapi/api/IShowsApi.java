package com.leo.core.iapi.api;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.leo.core.iapi.core.IApi;

public interface IShowsApi<T extends IShowsApi> extends IApi {

    T closeLog();

    T i(Object... param);

    T e(Object... param);

    T show(@NonNull CharSequence text);

    T show(@StringRes int resId);

}
