package com.leo.core.api.api;

import android.support.annotation.NonNull;

import com.leo.core.iapi.api.ICreateApi;
import com.leo.core.util.RPUtil;

import java.util.HashMap;
import java.util.Map;

public class MapCreateApi<T extends ICreateApi, I, P> implements ICreateApi<T, I, P> {

    private Map<I, P> map;

    public MapCreateApi() {
        map = new HashMap<>();
    }

    @Override
    public <R extends P> R create(@NonNull I in, @NonNull R item) {
        R r = getItem(in);
        if (r == null) {
            r = item;
            map.put(in, r);
        }
        return r;
    }

    @Override
    public <R extends P> R getItem(@NonNull I in) {
        return RPUtil.getObj(map.get(in));
    }

    @Override
    public <R extends P> I getItemKey(@NonNull R value) {
        for (Map.Entry<I, P> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public T clear() {
        map.clear();
        return (T) this;
    }

}
