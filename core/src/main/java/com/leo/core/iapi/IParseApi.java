package com.leo.core.iapi;

import com.google.gson.reflect.TypeToken;

import java.util.List;

public interface IParseApi<T extends IParseApi> extends IMsgApi<T> {

    T copy();

    T addRootType(Class... args);

    T addRootType(TypeToken... args);

    T clearRootData();

    <A> T put(String key, TypeToken<A> token);

    <A> T put(String key, Class<A> clz);

    T clearPutParseMap();

    <A> T add(TypeToken<A> token, IMsgAction<A> action);

    <A> T add(Class<A> clz, IMsgAction<A> action);

    <A> T addList(Class<A> clz, IMsgAction<List<A>> action);

    T clearAddParseMap();

    T clearParse();

    <B> T execute(B bean);

}
