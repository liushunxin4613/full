package com.leo.core.other;

import java.util.Map;

public class MMap<K, V> {

    private Map<K, V> map;

    public Map<K, V> map(){
        return map;
    }

    public MMap<K, V> map(Map<K, V> map){
        this.map = map;
        return this;
    }

    public MMap<K, V> put(K key, V value){
        map().put(key, value);
        return this;
    }

    public MMap<K, V> clear(){
        map().clear();
        return this;
    }

    public boolean containsKey(K key){
        return map().containsKey(key);
    }

}