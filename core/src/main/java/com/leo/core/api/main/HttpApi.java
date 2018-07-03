package com.leo.core.api.main;

import android.support.annotation.NonNull;

import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.net.RetrofitFactory;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class HttpApi<T extends HttpApi> extends HasCoreControllerApi<T> implements IHttpApi<T> {

    private IParseApi api;
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
    public <B> T setNewSubscriber(MsgSubscriber<T, B> newSubscriber) {
        this.newSubscriber = newSubscriber;
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable) {
        observable(observable, null, null, null, -1, null);
        return getThis();
    }

    @Override
    public <B> T observable(Observable<B> observable, String startUrl, String path, Map<String,
            String> map, int what, String tag) {
        if (observable != null && checkObservable(observable, startUrl, path, map, what, tag)) {
            onObservable(observable, startUrl, path, map, what, tag);
        }
        return getThis();
    }

    protected <B> boolean checkObservable(@NonNull Observable<B> observable, String startUrl, String path,
                                          Map<String, String> map, int what, String tag) {
        return true;
    }

    protected <B> void onObservable(@NonNull Observable<B> observable, String startUrl, String path,
                                       Map<String, String> map, int what, String tag) {
        MsgSubscriber<T, B> subscriber = subscriber();
        subscriber.init(path, what, tag);
        observable.compose(transformer()).subscribe((Subscriber) subscriber);
    }

}