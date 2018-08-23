package com.leo.core.iapi.api;

import java.lang.reflect.Type;

public interface IGsonDecodeApi<T extends IGsonDecodeApi, EI, DI, DP> extends IDecodeApi<T, EI, String, DI, DP> {

    @Override
    String encode(EI in);

    <R> R decode(String in, Class<R> clz);

    <R> R decode(String in, Type type);

    <R> R decode(Object obj, Class<R> clz);

    <R> R decode(Object obj, Type type);

}