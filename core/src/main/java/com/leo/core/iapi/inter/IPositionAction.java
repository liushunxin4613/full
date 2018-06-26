package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

public interface IPositionAction<T> extends IApi {
    void execute(T item, int position);
}