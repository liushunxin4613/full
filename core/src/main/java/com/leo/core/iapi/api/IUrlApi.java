package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;

import java.util.Map;

public interface IUrlApi<T> extends IApi {

    T get(String path);

    T get(String path, IObjAction<Map<String, Object>> action);

    T get(String path, IObjAction<Map<String, Object>> action, int what);

    T get(String path, IObjAction<Map<String, Object>> action, String tag);

    T get(String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T post(String path);

    T post(String path, IObjAction<Map<String, Object>> action);

    T post(String path, IObjAction<Map<String, Object>> action, int what);

    T post(String path, IObjAction<Map<String, Object>> action, String tag);

    T post(String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T get(String url, String path);

    T get(String url, String path, IObjAction<Map<String, Object>> action);

    T get(String url, String path, IObjAction<Map<String, Object>> action, int what);

    T get(String url, String path, IObjAction<Map<String, Object>> action, String tag);

    T get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T post(String url, String path);

    T post(String url, String path, IObjAction<Map<String, Object>> action);

    T post(String url, String path, IObjAction<Map<String, Object>> action, int what);

    T post(String url, String path, IObjAction<Map<String, Object>> action, String tag);

    T post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    Map<String, String> getCleanMapAction(IObjAction<Map<String, Object>> action);

}
