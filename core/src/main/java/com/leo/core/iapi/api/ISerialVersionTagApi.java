package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface ISerialVersionTagApi<T> extends IApi {
    boolean isSerialVersionTag(T obj);
}
