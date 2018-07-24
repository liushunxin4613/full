package com.leo.core.net;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.Transformer;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static com.leo.core.util.TextUtils.check;

public class AuxiliaryFactory {

    private static AuxiliaryFactory instance;

    public static AuxiliaryFactory getInstance() {
        if (instance == null) {
            synchronized (AuxiliaryFactory.class) {
                if (instance == null) {
                    instance = new AuxiliaryFactory();
                }
            }
        }
        return instance;
    }

    private final static transient boolean SERVER = true;
    private final static transient String ROOT_IP = SERVER ? "111.231.231.226" : "192.168.8.102";
    private final static transient String ROOT_URL = String.format("http://%s/app/api/", ROOT_IP);

    private transient Api api;

    private AuxiliaryFactory() {
        if (getRootUrl() == null || !getRootUrl().endsWith("/")) {
            throw new NullPointerException(getRootUrl() + "为空或以不/结尾");
        }
        this.api = RetrofitFactory.getInstance().getRetrofit(getRootUrl()).create(Api.class);
    }

    public String getRootUrl() {
        return ROOT_URL;
    }

    public void postUpLoadHttp(Map<String, Object> map) {
        post("uploadHttp", map, null);
    }

    public void postSimulate(Map<String, Object> map, Subscriber<String> subscriber) {
        post("simulate", map, subscriber);
    }

    private void post(String path, Map<String, Object> map, Subscriber<String> subscriber) {
        if (check(api, path, map)) {
            api.post(path, get(mp -> mp.putAll(map)))
                    .compose(new Transformer())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            if (subscriber != null) {
                                subscriber.onCompleted();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (subscriber != null) {
                                subscriber.onError(e);
                            } else {
                                if (e instanceof ConnectException) {
                                    ii(path, map, "网络异常");
                                } else if (e instanceof HttpException) {
                                    ii(path, map, "服务器异常");
                                } else if (e instanceof SocketTimeoutException) {
                                    ii(path, map, "连接超时");
                                } else {
                                    ii(path, map, "数据异常");
                                }
                            }
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                                String text = body.string();
                                if (subscriber != null) {
                                    Response response = GsonDecodeUtil.decode(text, Response.class);
                                    subscriber.onNext(RunUtil.getExecute(response, res -> res.response));
                                } else {
                                    ii(path, map, text);
                                }
                            } catch (IOException e) {
                                onError(e);
                            }
                        }
                    });
        }

    }

    private void ii(String path, Map<String, Object> map, String text) {
        LogUtil.ii(this, String.format("URL: %s%s\nPARAMS: %s\nRESPONSE: %s", getRootUrl(), path,
                LogUtil.getLog(map), text));
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

    private class Response {
        private String response;
    }

}
