package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface ICallbackApi<T> extends IApi{
    void onCall(int code, T... obj);
}
