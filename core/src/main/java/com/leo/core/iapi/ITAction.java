package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;


public interface ITAction<T> extends IApi {

    void action(T obj);

}
