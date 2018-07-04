package com.leo.core.api.core;

import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IPositionAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.iapi.inter.ITextAction;
import com.leo.core.other.ParamType;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThisApi<T extends ThisApi> implements IThisApi<T> {

    @Override
    public T getThis() {
        return (T) this;
    }

    protected T execute(String text, ITextAction action) {
        RunUtil.executeNon(text, action);
        return getThis();
    }

    protected <B> T executeNon(B obj, IObjAction<B> action) {
        RunUtil.executeNon(obj, action);
        return getThis();
    }

    protected static <A> void executeBol(List<A> data, IBolAction<A> action) {
        RunUtil.executeBol(data, action);
    }

    protected <B> T execute(List<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
        return getThis();
    }

    protected <B> void executePos(List<B> data, IPositionAction<B> action) {
        RunUtil.executePos(data, action);
    }

    protected <B> T execute(IObjAction<B> action, List<B>... args) {
        if (action != null && !TextUtils.isEmpty(args)) {
            for (List<B> data : args) {
                RunUtil.execute(data, action);
            }
        }
        return getThis();
    }

    protected <B> T execute(Set<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
        return getThis();
    }

    protected <K, V> T execute(Map<K, V> map, IMapAction<K, V> action) {
        RunUtil.execute(map, action);
        return getThis();
    }

    protected <B> T execute(B[] args, IObjAction<B> action) {
        RunUtil.execute(TextUtils.getListData(args), action);
        return getThis();
    }

    protected <B> T execute(IObjAction<B> action, B... args) {
        RunUtil.execute(TextUtils.getListData(args), action);
        return getThis();
    }

    protected <A, B> B getExecute(A in, B def, IReturnAction<A, B> api) {
        if (in != null && api != null) {
            return api.execute(in);
        }
        return def;
    }

    protected <A, B> B getExecute(A in, IReturnAction<A, B> api) {
        return getExecute(in, null, api);
    }

    protected <A> A no(A a, A def) {
        return a == null ? def : a;
    }

    protected String no(String text) {
        return no(text, "");
    }

    protected <A> ParamType<A> get(Class<A> clz, Type... args) {
        return new ParamType<>(clz, args);
    }

    protected <A> ParamType[] gets(Class<A> clz, Type... typeArguments) {
        if (clz != null && !TextUtils.isEmpty(typeArguments)) {
            ParamType[] args = new ParamType[typeArguments.length];
            for (int i = 0; i < typeArguments.length; i++) {
                args[i] = ParamType.get(clz, typeArguments[i]);
            }
            return args;
        }
        return null;
    }

}