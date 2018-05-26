package com.leo.core.api.core;

import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IMapAction;
import com.leo.core.iapi.IReturnApi;
import com.leo.core.iapi.IObjAction;
import com.leo.core.iapi.core.IThisApi;
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

    protected <B> T executeNon(B obj, IObjAction<B> api) {
        RunUtil.executeNon(obj, api);
        return getThis();
    }

    protected boolean execute(boolean is, IAction action) {
        return RunUtil.execute(is, action);
    }

    protected <B> T execute(List<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
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

    protected <A, B> B getExecute(A in, B def, IReturnApi<A, B> api) {
        if (in != null && api != null) {
            return api.execute(in);
        }
        return def;
    }

    protected <A, B> B getExecute(A in, IReturnApi<A, B> api) {
        return getExecute(in, null, api);
    }

    protected <A, B> B no(A a, IReturnApi<A, B> api) {
        return (a == null || api == null) ? null : api.execute(a);
    }

    protected  <A> ParamType<A> get(Class<A> clz, Type... args) {
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
