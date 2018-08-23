package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IPathMsgApi<T> extends IApi {
    T init(String type, String baseUrl, String path, Map<String, String> map, int what, String msg);
}