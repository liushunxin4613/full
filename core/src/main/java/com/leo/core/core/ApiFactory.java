package com.leo.core.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.leo.core.api.ObjectApi;
import com.leo.core.iapi.IBindContextApi;
import com.leo.core.iapi.IObjectApi;
import com.leo.core.iapi.core.IApi;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口子类生成工厂
 */
public class ApiFactory {

    private static ApiFactory instance;

    public static ApiFactory getInstance(){
        if (instance == null){
            synchronized (ApiFactory.class){
                if (instance == null)
                    instance = new ApiFactory();
            }
        }
        return instance;
    }

    private Map<String, IApi> map;
    private IObjectApi objectApi;

    private ApiFactory() {
        map = new HashMap<>();
        objectApi = new ObjectApi();
    }

    public <T extends IApi> T getApi(@NonNull Class<T> clz, Context context){
        String name = clz.getName();
        IApi api = map.get(name);
        if (api == null || !clz.isAssignableFrom(api.getClass())){
            api = (IApi) objectApi.getObject(clz);
            map.put(name, api);
        }
        if (api instanceof IBindContextApi && context != null){
            ((IBindContextApi) api).bind(context);
        }
        return (T) api;
    }

    public <T extends IApi> T getApi(@NonNull Class<T> cls){
        return getApi(cls, null);
    }

    public void close(){
        instance = null;
        map = null;
    }

}
