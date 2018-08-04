package com.leo.core.iapi.api;

import com.google.gson.reflect.TypeToken;
import com.leo.core.bean.ParseBean;
import com.leo.core.iapi.inter.IPathMsgAction;

import java.util.List;

public interface IParseApi<T extends IParseApi> extends IPathMsgApi<T> {

    T copy();

    <A> T add(Class<A> root, IPathMsgAction<A> action);

    <A> T add(TypeToken<A> root, IPathMsgAction<A> action);

    <A, B> T add(Class<A> root, Class<B> clz, IPathMsgAction<B> action);

    <A, B> T addList(Class<A> root, Class<B> clz, IPathMsgAction<List<B>> action);

    <A, B> T add(Class<A> root, TypeToken<B> token, IPathMsgAction<B> action);

    <A, B> T add(TypeToken<A> root, Class<B> clz, IPathMsgAction<B> action);

    <A, B> T addList(TypeToken<A> root, Class<B> clz, IPathMsgAction<List<B>> action);

    <A, B> T add(TypeToken<A> root, TypeToken<B> token, IPathMsgAction<B> action);

    T clearParse();

    <B> T parse(B bean);

    List<ParseBean> getParseData();

}
