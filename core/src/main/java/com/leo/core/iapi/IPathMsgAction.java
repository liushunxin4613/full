package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IPathMsgAction<B> extends IApi {
    void execute(String path, int what, String msg, B bean);
}
