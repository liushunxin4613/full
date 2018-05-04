package com.leo.core.api.main;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IClassAddApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.main.IHttpApi;
import com.leo.core.net.RetrofitFactory;
import com.leo.core.other.ParamType;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

public class HttpApi<T extends HttpApi> extends ThisApi<T> implements IHttpApi<T> {

    private RetrofitFactory factory;
    private HashMap<String, Object> map;
    private Map<String, List<IRunApi>> apiMap;
    private Observable.Transformer transformer;
    private Observable.Transformer newTransformer;
    private Subscriber subscriber;
    private Subscriber newSubscriber;

    public HttpApi(Observable.Transformer newTransformer) {
        this.newTransformer = newTransformer;
        factory = RetrofitFactory.getInstance();
        map = new HashMap<>();
        apiMap = new HashMap<>();
    }

    protected ParamType get(Type rawType, Type... typeArguments){
        return ParamType.get(rawType, typeArguments);
    }

    @Override
    public <R> T add(Class<R> clz, IRunApi<R> api) {
        if(clz != null && api != null){
            String name = clz.toString();
            List<IRunApi> data = apiMap.get(name);
            if(data == null){
                apiMap.put(name, new ArrayList<>());
            }
            apiMap.get(name).add(api);
        }
        return getThis();
    }

    @Override
    public <R> T listAdd(Class<R> clz, IRunApi<List<R>> api) {
        if(clz != null && api != null){
            String name = get(List.class, clz).toString();
            List<IRunApi> data = apiMap.get(name);
            if(data == null){
                apiMap.put(name, new ArrayList<>());
            }
            apiMap.get(name).add(api);
        }
        return getThis();
    }

    @Override
    public T replaceClzAddApiAll(Map<String, List<IRunApi>> map) {
        if(!TextUtils.isEmpty(map)){
            apiMap = map;
        } else {
            apiMap.clear();
        }
        return getThis();
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
        subscriber = newSubscriber();
        if(subscriber == null){
            throw new NullPointerException("newTransformer 不能为空");
        } else if(subscriber instanceof IClassAddApi){
            IClassAddApi addApi = (IClassAddApi) subscriber;
            addApi.replaceClzAddApiAll(apiMap);
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
