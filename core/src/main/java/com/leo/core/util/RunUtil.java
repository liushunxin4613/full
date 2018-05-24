package com.leo.core.util;

import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IMapAction;
import com.leo.core.iapi.IReturnApi;
import com.leo.core.iapi.IObjAction;

import java.util.List;
import java.util.Map;

public class RunUtil {

    public static <A, B> B getExecute(A a, B b, IReturnApi<A, B> api) {
        return (a == null || api == null) ? b : api.execute(a);
    }

    public static <A, B> B getExecute(A a, IReturnApi<A, B> api) {
        return getExecute(a, null, api);
    }

    public static boolean execute(boolean is, IAction action) {
        if (is && action != null) {
            action.execute();
            return true;
        }
        return false;
    }

    public static <T> void executeNon(T obj, IObjAction<T> r) {
        if (obj != null && r != null) {
            r.execute(obj);
        }
    }

    public static <T> boolean execute(List<T> data, IObjAction<T> action) {
        if (action == null || data == null) {
            return false;
        }
        for (T obj : data) {
            if (obj != null) {
                action.execute(obj);
            }
        }
        return true;
    }

    public static <T> boolean execute(T[] args, IObjAction<T> action) {
        return execute(TextUtils.getListData(args), action);
    }

    public static <T, S extends T> boolean execute(List<T> data, Class<S> clz, IObjAction<S> action) {
        if (action == null || data == null || clz == null) {
            return false;
        }
        for (T obj : data) {
            if (obj != null && clz.isInstance(obj)) {
                action.execute((S) obj);
            }
        }
        return true;
    }

    public static <K, V> boolean execute(Map<K, V> map, IMapAction<K, V> action) {
        if (map == null || (action == null)) {
            return false;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry != null) {
                action.execute(entry.getKey(), entry.getValue());
            }
        }
        return true;
    }

    public static <K, V> boolean execute(Map<K, V> map, IObjAction<K> keyApi, IObjAction<V> valueApi) {
        if (map == null || (keyApi == null || valueApi == null)) {
            return false;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry != null) {
                keyApi.execute(entry.getKey());
                valueApi.execute(entry.getValue());
            }
        }
        return true;
    }

    public static <T, R> R getFirstNonItem(List<T> data, IReturnApi<T, R> api, R ro, R rd) {
        if (data != null && api != null && ro != null) {
            for (T obj : data) {
                if (ro.equals(api.execute(obj))) {
                    rd = ro;
                }
            }
        }
        return rd;
    }

    public static <T> T getFirstNonItem(List<T> data) {
        if (data != null) {
            for (T obj : data) {
                if (obj != null) {
                    return obj;
                }
            }
        }
        return null;
    }

}
