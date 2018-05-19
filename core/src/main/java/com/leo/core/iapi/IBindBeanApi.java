package com.leo.core.iapi;

public interface IBindBeanApi<A, B> {
    void onItem(A api, B bean);
}
