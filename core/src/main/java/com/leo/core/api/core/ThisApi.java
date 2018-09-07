package com.leo.core.api.core;

import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IPositionAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.iapi.inter.ITextAction;
import com.leo.core.iapi.inter.IbolAction;
import com.leo.core.other.MMap;
import com.leo.core.other.ParamType;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.leo.core.util.TextUtils.check;

public class ThisApi<T extends ThisApi> implements IThisApi<T> {

    @Override
    public T getThis() {
        return (T) this;
    }

    public T execute(IAction action){
        if(action != null){
            action.execute();
        }
        return getThis();
    }

    protected <A> T execute(int count, boolean sequence, IReturnAction<Integer, A> ra,
                            IObjAction<A> action) {
        if (count > 0 && check(ra, action)) {
            int index = sequence ? 0 : count - 1;
            while ((sequence && index < count) || (!sequence && index >= 0)) {
                try {
                    A a = ra.execute(sequence ? index++ : index--);
                    if (check(a)) {
                        action.execute(a);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return getThis();
    }

    protected T execute(String text, ITextAction action) {
        RunUtil.executeNon(text, action);
        return getThis();
    }

    protected <B> T executeNon(B obj, IObjAction<B> action) {
        RunUtil.executeNon(obj, action);
        return getThis();
    }

    protected static <A> void executeBol(List<A> data, IbolAction<A> action) {
        RunUtil.executeBol(data, action);
    }

    protected <B> T execute(List<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
        return getThis();
    }

    protected <B> void executePos(List<B> data, IPositionAction<B> action) {
        RunUtil.executePos(data, action);
    }

    @SafeVarargs
    protected final <B> T execute(IObjAction<B> action, List<B>... args) {
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

    @SafeVarargs
    protected final <B> T execute(IObjAction<B> action, B... args) {
        RunUtil.execute(TextUtils.getListData(args), action);
        return getThis();
    }

    public <A> A getOExecute(IObjAction<A> api, A a) {
        return RunUtil.getExecute(api, a);
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

    protected <A> List<A> getData(Class<A> clz, Object obj) {
        if (clz != null && obj != null) {
            try {
                return (List<A>) obj;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    protected <K, V> Map<K, V> getMap(Class<K> kClz, Class<V> vClz, Object obj) {
        if (kClz != null && vClz != null && obj != null) {
            try {
                return (Map<K, V>) obj;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    protected <K, V> Map<K, V> map(Map<K, V> map, IObjAction<MMap<K, V>> action) {
        if (action != null) {
            MMap<K, V> mMap = new MMap<>();
            action.execute(mMap.map(map));
            return mMap.map();
        }
        return null;
    }

    protected <K, V> Map<K, V> map(IObjAction<MMap<K, V>> action) {
        if (action != null) {
            MMap<K, V> mMap = new MMap<>();
            action.execute(mMap.map(new LinkedHashMap<>()));
            return mMap.map();
        }
        return null;
    }

    protected <K, V> Map<K, V> map(Class<K> k, Class<V> v, IObjAction<MMap<K, V>> action) {
        if (TextUtils.check(k, v, action)) {
            MMap<K, V> mMap = new MMap<>();
            action.execute(mMap.map(new LinkedHashMap<>()));
            return mMap.map();
        }
        return null;
    }

}