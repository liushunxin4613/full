package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IProgressListener;

import java.util.Map;

public interface IUrlApi<T> extends IApi {

    T get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T jsonPost(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    T bodyPost(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag,
               IProgressListener listener);

    Map<String, String> getActionMap(IObjAction<Map<String, Object>> action);

}