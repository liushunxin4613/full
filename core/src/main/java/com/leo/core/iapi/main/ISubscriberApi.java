package com.leo.core.iapi.main;

import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.net.RetrofitSubscriber;

public interface ISubscriberApi<T extends ISubscriberApi> extends IThisApi<T> {

    <R> T add(Class<R> clz, IRunApi<R> api);

    <R> T listAdd(Class<R> clz, IRunApi<R> api);

}
