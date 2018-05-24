package com.leo.core.api;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IConfigApi;
import com.leo.core.iapi.ITAction;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class ConfigApi<T extends ConfigApi> extends ThisApi<T> implements IConfigApi<T> {

    private Map<String, Object> map;

    @Override
    public Map<String, Object> getConfigMap() {
        if (map == null) {
            map = newConfigMap();
            if (map == null) {
                throw new NullPointerException("newConfigMap 不能为空");
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> newConfigMap() {
        return new HashMap<>();
    }

    @Override
    public boolean hasConfig(String key) {
        return !TextUtils.isEmpty(key) && getConfigMap().containsKey(key);
    }

    @Override
    public <R> T addConfig(String key, R value) {
        if(!TextUtils.isEmpty(key) && value != null){
            getConfigMap().put(key, value);
        }
        return getThis();
    }

    @Override
    public <R> R getConfig(String key) {
        return !TextUtils.isEmpty(key) ? (R) getConfigMap().get(key) : null;
    }

    @Override
    public <R> T executeConfig(String key, ITAction<R> action) {
        if(!TextUtils.isEmits(key, action)){
            if(hasConfig(key)){
                action.action(getConfig(key));
            }
        }
        return getThis();
    }

    @Override
    public <R> T executeConfig(String key, R compare, IAction action) {
        if(!TextUtils.isEmits(key, compare, action)){
            if(hasConfig(key) && TextUtils.equals(getConfig(key), compare)){
                action.execute();
            }
        }
        return getThis();
    }

    @Override
    public T removeConfig(String key) {
        if (hasConfig(key)) {
            getConfigMap().remove(key);
        }
        return getThis();
    }

    @Override
    public T removeConfigAll() {
        getConfigMap().clear();
        return getThis();
    }

    @Override
    public T replaceConfigAll(Map<String, ?> map) {
        if (!TextUtils.isEmpty(map)) {
            removeConfigAll();
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                addConfig(entry.getKey(), entry.getValue());
            }
        }
        return getThis();
    }

}
