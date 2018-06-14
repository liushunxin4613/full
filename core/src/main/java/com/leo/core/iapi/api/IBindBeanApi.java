package com.leo.core.iapi.api;

public interface IBindBeanApi<A, B> {
    void onItem(A api, B bean);
}
