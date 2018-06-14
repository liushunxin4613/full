package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface IMergeApi<T extends IMergeApi> extends IApi {

    <R> R[] newArray(Class clz, int length);

    <R> R[] merge(Class<R> clz, boolean end, R[] args, R... args1);

}
