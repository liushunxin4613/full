package com.leo.core.net;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.api.IUrlApi;
import com.leo.core.iapi.inter.IProgressListener;
import com.leo.core.iapi.inter.IUrlAction;
import com.leo.core.other.MMap;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

import static com.leo.core.config.Config.APP_JSON;

public class UrlApi<T extends UrlApi> extends HasCoreControllerApi<T> implements IUrlApi {

    public final static String REQINFO = "REQINFO";
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

    private <B> void observable(String type, String url, B obj, String path, Map<String, String> map, int what,
                                String tag, IUrlAction<Api, Observable<B>> action) {
        if (TextUtils.check(url)) {
            Observable<B> observable = null;
            if (url.startsWith(APP_JSON)) {//json模拟数据
                observable = Observable.create(subscriber -> {
                    subscriber.onNext(obj);
                    subscriber.onCompleted();
                });
            } else if (action != null) {
                observable = action.execute(getApi(url), url, path, map);
            }
            if (observable != null) {
                controllerApi().observable(observable, type, url, path, map, what, tag);
            }
        }
    }

    @Override
    public <B> void get(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                        int what, String tag) {
        observable(type, url, obj, path, getActionMap(action), what, tag, null);
    }

    @Override
    public <B> void post(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                         int what, String tag) {
        observable(type, url, obj, path, getActionMap(action), what, tag, null);
    }

    @Override
    public <B> void jsonPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                             int what, String tag) {
        observable(type, url, obj, path, getActionMap(action), what, tag, null);
    }

    @Override
    public <B> void bodyPost(String type, String url, B obj, String path, IObjAction<Map<String, Object>> action,
                             int what, String tag, IProgressListener listener) {
        observable(type, url, obj, path, getActionMap(action), what, tag, null);
    }

    @Override
    public void get(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                    String tag) {
        observable(type, url, null, path, getActionMap(action), what, tag, (a, u, p, m) -> a.get(p, m));
    }

    @Override
    public void post(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                     String tag) {
        observable(type, url, null, path, getActionMap(action), what, tag, (a, u, p, m) -> a.post(p, m));
    }

    @Override
    public void jsonPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                         String tag) {
        observable(type, url, null, path, getActionMap(action), what, tag, (a, u, p, m) -> a.jsonPost(p,
                createJsonBody(m)));
    }

    @Override
    public void bodyPost(String type, String url, String path, IObjAction<Map<String, Object>> action, int what,
                         String tag, IProgressListener listener) {
        observable(type, url, null, path, getActionMap(action), what, tag, (a, u, p, m) -> a.bodyPost(p,
                createBody(m, listener)));
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

    //******************************************* JSON *******************************************

    public void post(String type, String url, String path, String json) {
        post(type, url, json, path, null, WHAT_DEFAULT, null);
    }

    //******************************************* HTTP *******************************************

    protected void postParams(String type, String url, String path, IObjAction<Map<String, Object>> action) {
        Map<String, String> map = getActionMap(action);
        observable(type, url, null, path, map, WHAT_DEFAULT, LogUtil.getLog(false, map), (a, u, p, m)
                -> a.post(p, m));
    }

    public void get(String type, String url, String path, String tag) {
        get(type, url, path, null, WHAT_DEFAULT, tag);
    }

    public void post(String type, String url, String path) {
        post(type, url, path, null, WHAT_DEFAULT, null);
    }

    public void get(String type, String url, String path, IObjAction<Map<String, Object>> action) {
        get(type, url, path, action, WHAT_DEFAULT, null);
    }

    public void get(String type, String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        get(type, url, path, action, WHAT_DEFAULT, tag);
    }

    public void post(String type, String url, String path, IObjAction<Map<String, Object>> action) {
        post(type, url, path, action, WHAT_DEFAULT, null);
    }

    public void jsonPost(String type, String url, String path, IObjAction<Map<String, Object>> action) {
        jsonPost(type, url, path, action, WHAT_DEFAULT, null);
    }

    public void post(String type, String url, String path, IObjAction<Map<String, Object>> action, String tag) {
        post(type, url, path, action, WHAT_DEFAULT, tag);
    }

    public void post(String type, String url, String path, IObjAction<Map<String, Object>> action, int what) {
        post(type, url, path, action, what, null);
    }

    protected IObjAction<Map<String, Object>> g(IObjAction<Map<String, Object>> action) {
        Map<String, Object> mp;
        executeNon(mp = new HashMap<>(), action);
        if (!TextUtils.isEmpty(mp)) {
            return map -> map.put(REQINFO, LogUtil.getLog(false, mp));
        }
        return null;
    }

    protected IObjAction<Map<String, Object>> g() {
        return map -> map.put(REQINFO, "{}");
    }

    protected IObjAction<Map<String, Object>> gm(IObjAction<MMap<String, Object>> action) {
        return action == null ? null : map -> action.execute(new MMap<String, Object>().map(map));
    }

}