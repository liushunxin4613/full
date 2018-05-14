package com.leo.core.iapi;

public interface IBindBeanCallback<B> {
    void onBindBean(B bean, int position);
}
