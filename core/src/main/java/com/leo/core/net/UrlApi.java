package com.leo.core.net;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.iapi.inter.IProgressListener;
import com.leo.core.iapi.inter.IUrlAction;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class UrlApi<T extends UrlApi> extends HasCoreControllerApi<T> implements IUrlApi<T> {

    private final static int WHAT_DEFAULT = -1;

    public UrlApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    private Api getApi(String url) {
        return (Api) controllerApi().getApi(url);
    }

    private RequestBody createJsonBody(Map<String, String> map) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                controllerApi().encode(map));
    }

    private RequestBody createBody(Map<String, String> map, IProgressListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        execute(map, (key, value) -> {
            if (!TextUtils.isEmpty(key) && value != null) {
                builder.addFormDataPart(key, value);
            }
        });
        return new MRequestBody(builder.build(), listener);
    }

    private <B> void observable(String url, String path, Map<String, String> map, int what,
                                String tag, IUrlAction<Api, Observable<B>> action) {
        controllerApi().observable(action.execute(getApi(url), url, path, map), url, path, map,
                what, tag);
    }

    @Override
    public T get(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        observable(url, path, getActionMap(action), what, tag, (a, u, p, m) -> a.get(p, m));
        return getThis();
    }

    @Override
    public T post(String url, String path, IObjAction<Map<String, Object>> action, int what, String tag) {
        observable(url, path, getActionMap(action), what, tag, (a, u, p, m) -> a.post(p, m));
        return getThis();
    }

    @Override
    public T jsonPost(String url, String path, IObjAction<Map<String, Object>> action, int what,
                      String tag) {
        observable(url, path, getActionMap(action), what, tag, (a, u, p, m) -> a.jsonPost(p,
                createJsonBody(m)));
        return getThis();
    }

    @Override
    public T bodyPost(String url, String path, IObjAction<Map<String, Object>> action, int what,
                      String tag, IProgressListener listener) {
        observable(url, path, getActionMap(action), what, tag, (a, u, p, m) -> a.bodyPost(p,
                createBody(m, listener)));
        return getThis();
    }

    @Override
    public Map<String, String> getActionMap(IObjAction<Map<String, Object>> action) {
        if (action != null) {
            Map<String, String> map = new HashMap<>();
            Map<String, Object> actionMap = new HashMap<>();
            action.execute(actionMap);
            if (!TextUtils.isEmpty(actionMap)) {
                for (Map.Entry<String, Object> entry : actionMap.entrySet()) {
                    if (!TextUtils.isEmpty(entry.getKey()) && entry.getValue() != null) {
                        map.put(entry.getKey(), LogUtil.getLog(false, entry.getValue()));
                    }
                }
                return map;
            }
        }
        return new HashMap<>();
    }

    public void get(String url, String path, String tag) {
        get(url, path, null, WHAT_DEFAULT, tag);
    }

    public void post(String url, String path) {
        post(url, path, null, WHAT_DEFAULT, null);
    }

    public void get(String url, String path, IObjAction<Map<String, Object>> action) {
        get(url, path, action, WHAT_DEFAULT, null);
    }

    public void post(String url, String path, IObjAction<Map<String, Object>> action) {
        post(url, path, action, WHAT_DEFAULT, null);
    }

    public void jsonPost(String url, String path, IObjAction<Map<String, Object>> action) {
        jsonPost(url, path, action, WHAT_DEFAULT, null);
    }

    public void post(String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        post(url, path, action, WHAT_DEFAULT, tag);
    }

}