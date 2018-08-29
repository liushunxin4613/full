package com.leo.core.viewParse;

import android.support.annotation.NonNull;
import android.view.View;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IViewParseAction<A extends View, B> extends IApi {
    void onParse(@NonNull A view, B value, @NonNull final Map<String, Object> map);
}
