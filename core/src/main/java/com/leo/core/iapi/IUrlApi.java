package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

import java.util.Map;

public interface IUrlApi<T> extends IApi {

    T get(String path);

    T get(String path, IObjAction<Map<String, String>> action);

    T get(String path, IObjAction<Map<String, String>> action, int what);

    T get(String path, IObjAction<Map<String, String>> action, String tag);

    T get(String path, IObjAction<Map<String, String>> action, int what, String tag);

    T post(String path, IObjAction<Map<String, String>> action);

    T post(String path, IObjAction<Map<String, String>> action, int what);

    T post(String path, IObjAction<Map<String, String>> action, String tag);

    T post(String path, IObjAction<Map<String, String>> action, int what, String tag);

}
