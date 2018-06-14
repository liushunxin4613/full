package com.leo.core.iapi.api;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;

public interface IGsonDecodeApi<T extends IGsonDecodeApi, EI, DI, DP> extends IDecodeApi<T, EI, String, DI, DP> {

    @Override
    String encode(EI in);

    <R> R decode(JsonElement in, Class<R> clz);

    <R> R decode(JsonElement in, Type type);

    <R> R decode(JsonReader in, Type type);

    <R> R decode(Reader in, Class<R> clz);

    <R> R decode(Reader in, Type type);

    <R> R decode(String in, Class<R> clz);

    <R> R decode(String in, Type type);

    <R> R decode(Object obj, Class<R> clz);

}
