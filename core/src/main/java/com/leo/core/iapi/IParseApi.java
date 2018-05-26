package com.leo.core.iapi;

import com.google.gson.reflect.TypeToken;

public interface IParseApi<T extends IParseApi> {

    <A> T addRootType(Class<A>... args);

    <A> T addRootType(TypeToken<A>... args);

    <A> T addRootType(int index, Class<A>... args);

    <A> T addRootType(int index, TypeToken<A>... args);

    T clearRootData();

    <A> T put(String key, TypeToken<A> token);

    <A> T put(String key, Class<A> clz);

    T clearPutParseMap();

    <A> T add(TypeToken<A> token, IObjAction<A> action);

    <A> T add(Class<A> clz, IObjAction<A> action);

    T clearAddParseMap();

    T clearParse();

    <B> T execute(B bean);

}
