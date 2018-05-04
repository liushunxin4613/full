package com.leo.core.util;

import com.leo.core.iapi.IReturnApi;
import com.leo.core.iapi.IRunApi;

import java.util.List;
import java.util.Map;

public class RunUtil {

    public static <T> void executeNon(T obj, IRunApi<T> r) {
        if (obj != null && r != null) {
            r.execute(obj);
        }
    }

    public static <T> boolean execute(List<T> data, IRunApi<T> runApi) {
        if (runApi == null || data == null) {
            return false;
        }
        for (T obj : data) {
            if (obj != null) {
                runApi.execute(obj);
            }
        }
        return true;
    }

    public static <T, S extends T> boolean execute(List<T> data, Class<S> clz, IRunApi<S> runApi) {
        if (runApi == null || data == null || clz == null) {
            return false;
        }
        for (T obj : data) {
            if (obj != null && clz.isInstance(obj)) {
                runApi.execute((S) obj);
            }
        }
        return true;
    }

    public static <K, V> boolean execute(Map<K, V> map, IRunApi<K> keyApi, IRunApi<V> valueApi) {
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
