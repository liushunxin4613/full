package com.leo.core.iapi;

public interface IBindItemCallback<A, T> {
    void onItem(A api, T bean);
}
