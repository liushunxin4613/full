package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IMsgApi<T> extends IApi {
    T init(int what, String msg);
}
