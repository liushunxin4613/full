package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

import java.util.List;
import java.util.Map;

public interface IClassAddApi<T extends IClassAddApi> extends IApi {

    <R> T add(Class<R> clz, IRunApi<R> api);

    <R> T listAdd(Class<R> clz, IRunApi<List<R>> api);

    T replaceClzAddApiAll(Map<String, List<IRunApi>> map);

}
