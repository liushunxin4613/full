package com.leo.core.api.main;

import android.support.annotation.NonNull;

import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.net.RetrofitFactory;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class HttpApi<T extends HttpApi> extends HasCoreControllerApi<T> implements IHttpApi<T> {

    private RetrofitFactory factory;
    private HashMap<String, Object> map;
    private Observable.Transformer transformer;

    public HttpApi(CoreControllerApi controllerApi, Observable.Transformer transformer) {
        super(controllerApi);
        this.map = new HashMap<>();
        this.transformer = transformer;
        this.factory = RetrofitFactory.getInstance();
    }

    @Override
    public <B> B create(String url, Class<B> clz) {
        if (!TextUtils.isEmpty(url) && clz != null) {
            String key = url + "/" + clz.getName();
            Object service = map.get(key);
            if (service == null) {
                service = factory.getRetrofit(url).create(clz);
                map.put(key, service);
            }
            return (B) service;
        }
        return null;
    }

    @Override
    public <B> B getApi(String url) {
        throw new NullPointerException("getApi 不能为空");
    }

    @Override
    public <B, M> Observable.Transformer<B, M> transformer() {
        return transformer;
    }

    @Override
    public <B> MsgSubscriber<T, B> newSubscriber() {
        return controllerApi().newSubscriber();
    }

    @Override
    public <B> T observable(B bean, String type, String baseUrl, String path, Map<String, String> map,
                            int what, String tag) {
        observable(getObservable(bean), type, baseUrl, path, map, what, tag);
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable, String type, String baseUrl, String path,
                            Map<String, String> map, int what, String tag) {
        if (observable != null && checkObservable(observable, type, baseUrl, path, map, what, tag)) {
            onObservable(observable, type, baseUrl, path, map, what, tag);
        }
        return getThis();
    }

    protected <B> boolean checkObservable(@NonNull Observable<B> observable, String type,
                                          String baseUrl, String path, Map<String, String> map,
                                          int what, String tag) {
        return true;
    }

    protected synchronized void onObservable(@NonNull Observable observable, String type, String baseUrl,
                                String path, Map<String, String> map, int what, String tag) {
        MsgSubscriber subscriber = newSubscriber();
        subscriber.init(type, baseUrl, path, map, what, tag);
        observable.compose(transformer()).subscribe(subscriber);
    }

    protected <B> Observable<B> getObservable(B bean) {
        return Observable.create(subscriber -> {
            subscriber.onNext(bean);
            subscriber.onCompleted();
        });
    }

}