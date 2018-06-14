package com.leo.core.iapi.api;

import com.google.gson.reflect.TypeToken;
import com.leo.core.iapi.inter.IPathMsgAction;

import java.util.List;

public interface IParseApi<T extends IParseApi> extends IPathMsgApi<T> {

    T copy();

    T addRootType(Class... args);

    T addRootType(TypeToken... args);

    T clearRootData();

    <A> T put(String key, TypeToken<A> token);

    <A> T put(String key, Class<A> clz);

    T clearPutParseMap();

    <A> T add(TypeToken<A> token, IPathMsgAction<A> action);

    <A> T add(Class<A> clz, IPathMsgAction<A> action);

    <A> T addList(Class<A> clz, IPathMsgAction<List<A>> action);

    T clearAddParseMap();

    T clearParse();

    <B> T execute(B bean);

}
