package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IProgressListener;

import java.util.Map;

public interface IUrlApi<T> extends IApi {

    <B> void get(String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void post(String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void jsonPost(String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void bodyPost(String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag,
               IProgressListener listener);

    void get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void jsonPost(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void bodyPost(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag,
               IProgressListener listener);

    Map<String, String> getActionMap(IObjAction<Map<String, Object>> action);

}