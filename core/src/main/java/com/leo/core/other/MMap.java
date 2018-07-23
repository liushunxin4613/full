package com.leo.core.other;

import com.leo.core.api.api.VsApi;

import java.util.Map;

public class MMap<K, V> extends VsApi<MMap<K, V>> {

    private Map<K, V> map;

    public Map<K, V> map() {
        return map;
    }

    public MMap<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public MMap<K, V> put(K key, V value) {
        vs(map(), map -> map.put(key, value));
        return this;
    }

    public MMap<K, V> clear() {
        vs(map(), Map::clear);
        return this;
    }

    public boolean containsKey(K key) {
        return vr(map(), map -> map.containsKey(key));
    }

}