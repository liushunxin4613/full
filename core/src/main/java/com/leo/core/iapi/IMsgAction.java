package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IMsgAction<B> extends IApi {
    void execute(int what, String msg, B bean);
}
