package com.leo.core.iapi.api;

import com.leo.core.iapi.inter.ITAction;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IAction;

import java.util.Map;

public interface IConfigApi<T extends IConfigApi> extends IApi {

    Map<String, ?> getConfigMap();

    Map<String, ?> newConfigMap();

    boolean hasConfig(String key);

    <R> T addConfig(String key, R value);

    <R> R getConfig(String key);

    <R> T executeConfig(String key, ITAction<R> action);

    <R> T executeConfig(String key, R compare, IAction action);

    T removeConfig(String key);

    T removeConfigAll();

    T replaceConfigAll(Map<String, ?> map);

}
