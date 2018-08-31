package com.leo.core.viewParse;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.View;

import com.leo.core.iapi.core.IApi;

public interface IStyleParseAction<A extends View> extends IApi {
    void onParse(@NonNull A view, @NonNull String name, @NonNull final TypedArray a);
}
