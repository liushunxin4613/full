package com.leo.core.net;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.Transformer;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

import static com.leo.core.util.TextUtils.check;

public class HttpHelper {

    private String rootUrl;
    private transient Api api;

    public HttpHelper(String rootUrl) {
        if (rootUrl == null || !rootUrl.endsWith("/")) {
            throw new NullPointerException(rootUrl + "为空或以不/结尾");
        }
        this.rootUrl = rootUrl;
        this.api = RetrofitFactory.getInstance().getRetrofit(rootUrl).create(Api.class);
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void post(String path, Map<String, Object> map) {
        if (check(api, path, map)) {
//            api.post(path, get(mp -> mp.putAll(map)))
//                    .compose(new Transformer())
//                    .subscribe(next -> LogUtil.ii(this, "next: " + LogUtil.getLog(next)));
        }
    }

    private Map<String, String> get(IObjAction<Map<String, Object>> action) {
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

}
