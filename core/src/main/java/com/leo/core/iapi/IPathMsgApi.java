package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IPathMsgApi<T> extends IApi {
    T init(String path, int what, String msg);
}