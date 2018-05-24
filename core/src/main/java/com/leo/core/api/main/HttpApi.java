package com.leo.core.api.main;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.net.RetrofitFactory;
import com.leo.core.util.TextUtils;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;

public class HttpApi<T extends HttpApi> extends ThisApi<T> implements IHttpApi<T> {

    private RetrofitFactory factory;
    private HashMap<String, Object> map;
    private Observable.Transformer transformer;
    private Observable.Transformer newTransformer;
    private Subscriber newSubscriber;

    public HttpApi(Observable.Transformer newTransformer) {
        this.map = new HashMap<>();
        this.newTransformer = newTransformer;
        this.factory = RetrofitFactory.getInstance();
    }

    @Override
    public <R> R create(String url, Class<R> clz) {
        if (!TextUtils.isEmpty(url) && clz != null) {
            String key = url + "/" + clz.getName();
            Object service = map.get(key);
            if (service == null) {
                service = factory.getRetrofit(url).create(clz);
                map.put(key, service);
            }
            return (R) service;
        }
        return null;
    }

    @Override
    public <R> R getApi(String url) {
        throw new NullPointerException("getApi 不能为空");
    }

    @Override
    public <R> R getApi() {
        throw new NullPointerException("getApi 不能为空");
    }

    @Override
    public <R, M> Observable.Transformer<R, M> transformer() {
        if (transformer == null){
            transformer = newTransformer();
            if(transformer == null){
                throw new NullPointerException("newTransformer 不能为空");
            }
        }
        return transformer;
    }

    @Override
    public <R, M> Observable.Transformer<R, M> newTransformer() {
        return newTransformer;
    }

    @Override
    public <R> Subscriber<R> subscriber() {
        Subscriber subscriber = newSubscriber();
        if(subscriber == null){
            throw new NullPointerException("newTransformer 不能为空");
        }
        return subscriber;
    }

    @Override
    public <R> Subscriber<R> newSubscriber() {
        return newSubscriber;
    }

    @Override
    public <R> T setNewSubscriber(Subscriber<R> newSubscriber) {
        this.newSubscriber = newSubscriber;
        return getThis();
    }

    @Override
    public <R> T observable(Observable<R> observable) {
        if(observable != null){
            observable.compose(transformer()).subscribe(subscriber());
        }
        return getThis();
    }

}
