package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IMapAction<K, V> extends IApi {
    void execute(K key, V value);
}
