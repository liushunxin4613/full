package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IPathMsgAction<B> extends IApi {
    void execute(String type, String baseUrl, String path, Map<String, String> map, int what,
                 String msg, String field, B bean);
}
