package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IProgressListener;

import java.util.Map;

public interface IUrlApi extends IApi {

    <B> void get(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void post(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void jsonPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    <B> void bodyPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action, int what, String tag,
               IProgressListener listener);

    void get(String type, String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void post(String type, String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void jsonPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what, String tag);

    void bodyPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what, String tag,
               IProgressListener listener);

    Map<String, String> getActionMap(IObjAction<Map<String, Object>> action);

}