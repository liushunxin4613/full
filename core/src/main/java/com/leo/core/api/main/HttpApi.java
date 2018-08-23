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
    private MsgSubscriber newSubscriber;
    private HashMap<String, Object> map;
    private Observable.Transformer transformer;
    private Observable.Transformer newTransformer;

    public HttpApi(CoreControllerApi controllerApi, Observable.Transformer newTransformer) {
        super(controllerApi);
        this.map = new HashMap<>();
        this.newTransformer = newTransformer;
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
        if (transformer == null) {
            transformer = newTransformer();
            if (transformer == null) {
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return transformer;
    }

    @Override
    public <B, M> Observable.Transformer<B, M> newTransformer() {
        return newTransformer;
    }

    @Override
    public <B> MsgSubscriber<T, B> subscriber() {
        MsgSubscriber<T, B> subscriber = newSubscriber();
        if (subscriber == null) {
            throw new NullPointerException("newTransformer 不能为空");
        }
        return subscriber;
    }

    @Override
    public <B> MsgSubscriber<T, B> newSubscriber() {
        return newSubscriber;
    }

    @Override
    public <B> void setNewSubscriber(MsgSubscriber<T, B> newSubscriber) {
        this.newSubscriber = newSubscriber;
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

    protected void onObservable(@NonNull Observable observable, String type, String baseUrl,
                                String path, Map<String, String> map, int what, String tag) {
        MsgSubscriber subscriber = subscriber();
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