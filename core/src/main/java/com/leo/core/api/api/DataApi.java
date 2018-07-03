package com.leo.core.api.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leo.core.iapi.api.IDataApi;
import com.leo.core.other.ParameterizedTypeImpl;
import com.leo.core.util.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * 单键值表数据处理类
 */
public class DataApi<T extends DataApi> extends BindContextApi<T> implements IDataApi<T> {

    private static final String APP_SHARED_PREFERENCES = "appSharedPreferences";
    private static final int MODE_DEFAULT = Context.MODE_PRIVATE;//默认mode,私有数据
    private static final String LIST_END = ".list";//list数据标志
    private SharedPreferences preferences;
    private int mode = MODE_DEFAULT;
    private String table;
    private Gson gson;

    @Override
    public T bind(@NonNull Context context) {
        gson = new GsonBuilder().disableHtmlEscaping().create();
        return super.bind(context);
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public T switchDefault() {
        switchTable(APP_SHARED_PREFERENCES, mode);
        return getThis();
    }

    @Override
    public T switchTable(String key) {
        switchTable(key, mode);
        return getThis();
    }

    @Override
    public T switchTable(String key, int mode) {
        if (!TextUtils.isEmpty(key)) {
            this.mode = mode;
            preferences = getContext().getSharedPreferences(key, mode);
            this.table = key;
        }
        return getThis();
    }

    @Override
    public <V> T saveData(String key, V value) {
        if (!TextUtils.isEmpty(key)) {
            if (value == null) {
                preferences.edit().remove(key).apply();
            } else {
                preferences.edit().putString(key, gson.toJson(value)).apply();
            }
        }
        return getThis();
    }

    @Override
    public <V> T saveData(V value) {
        if (value != null) {
            saveData(value.getClass().getName(), value);
        }
        return getThis();
    }

    @Override
    public <V> T saveData(String key, List<V> value) {
        if (!TextUtils.isEmpty(key)) {
            if (value == null) {
                preferences.edit().remove(key + LIST_END).apply();
            } else {
                preferences.edit().putString(key + LIST_END, gson.toJson(value)).apply();
            }
        }
        return getThis();
    }

    @Override
    public <V> T saveData(List<V> value) {
        if (!TextUtils.isEmpty(value)) {
            saveData(value.get(0).getClass().getName() + LIST_END, value);
        }
        return getThis();
    }

    @Override
    public String getString(String key) {
        if (!TextUtils.isEmpty(key)) {
            return TextUtils.removeF1(preferences.getString(key, null));
        }
        return null;
    }

    @Override
    public <C> C getBean(Class<C> clz) {
        return clz == null ? null : getBean(clz.getName(), clz);
    }

    @Override
    public <C> C getBean(String key, Class<C> clz) {
        try {
            if (!TextUtils.isEmpty(key) && clz != null) {
                return gson.fromJson(TextUtils.removeF1(preferences.getString(key, null)), clz);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public <C> List<C> getBeanData(Class<C> clz) {
        return clz == null ? null : getBeanData(clz.getName(), List.class, clz);
    }

    @Override
    public <C> List<C> getBeanData(String key, Class<C> clz) {
        return getBeanData(key, List.class, clz);
    }

    @Override
    public <C> List<C> getBeanData(String key, Class<? extends List> lClz, Class<C> clz) {
        try {
            if (!TextUtils.isEmpty(key) && lClz != null && clz != null) {
                return gson.fromJson(TextUtils.removeF1(preferences.getString(key + LIST_END, null)),
                        ParameterizedTypeImpl.get(lClz, clz));
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public <C> List<C> getBeanData(Class<? extends List> lCls, Class<C> cls) {
        return getBeanData(cls.getName(), lCls, cls);
    }

    @Override
    public List<String> getStringData(String key) {
        return TextUtils.isEmpty(key) ? null : getBeanData(key, List.class, String.class);
    }

    @Override
    public Map<String, ?> getAllBean() {
        return preferences.getAll();
    }

    @Override
    public String getAllBeanJsonString() {
        return gson.toJson(preferences.getAll());
    }

    @Override
    public T remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            preferences.edit().remove(key).apply();
        }
        return getThis();
    }

    @Override
    public T removeDataAll() {
        preferences.edit().clear().apply();
        return getThis();
    }

    @Override
    public T remove(Class clz) {
        if (clz != null) {
            remove(clz.getName());
        }
        return getThis();
    }

    @Override
    public T removeData(String key) {
        if (!TextUtils.isEmpty(key)) {
            remove(key + LIST_END);
        }
        return getThis();
    }

    @Override
    public T removeData(Class clz) {
        if (!TextUtils.isEmpty(clz)) {
            remove(clz.getName() + LIST_END);
        }
        return getThis();
    }

}
