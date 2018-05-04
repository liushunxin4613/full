package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IRAction<T, R> extends IApi {

    R action(T obj);

}
