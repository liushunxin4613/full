package com.leo.core.iapi.main;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.core.IThisApi;

public interface ISubscriberApi<T extends ISubscriberApi> extends IThisApi<T> {

    <R> T add(Class<R> clz, IObjAction<R> api);

    <R> T listAdd(Class<R> clz, IObjAction<R> api);

}
