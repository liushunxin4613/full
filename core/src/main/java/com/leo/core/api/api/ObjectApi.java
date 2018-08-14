package com.leo.core.api.api;

import android.text.TextUtils;

import com.leo.core.iapi.api.IObjectApi;

import java.lang.reflect.Constructor;

/**
 * 类帮助
 */
public class ObjectApi<C extends ObjectApi> implements IObjectApi<C> {

    @Override
    public Class getClass(String name) {
        if (!TextUtils.isEmpty(name)) {
            try {
                return Class.forName(name);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    public <T> T getObject(Class<T> clz) {
        if (clz != null) {
            try {
                return clz.newInstance();
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    public Object getObject(String name) {
        return getObject(getClass(name));
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz) {
        Class clz = getClass(name);
        if (clz != null && pClz != null && pClz.isAssignableFrom(clz)) {
            return (P) getObject(clz);
        }
        return null;
    }

    @Override
    public <T> T getObject(Class<T> clz, Class[] cs, Object[] os) {
        if (clz != null && cs != null && os != null && cs.length == os.length) {
            try {
                /*以下调用带参的、私有构造函数*/
                Constructor<T> c = clz.getDeclaredConstructor(cs);
                c.setAccessible(true);
                return c.newInstance(os);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public <T> T getObject(Class<T> clz, Class cs, Object os) {
        if (clz != null && cs != null) {
            try {
                /*以下调用带参的、私有构造函数*/
                Constructor<T> c = clz.getDeclaredConstructor(cs);
                c.setAccessible(true);
                return c.newInstance(os);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object getObject(String name, Class[] cs, Object[] os) {
        return getObject(getClass(name), cs, os);
    }

    @Override
    public Object getObject(String name, Class cs, Object os) {
        return getObject(getClass(name), cs, os);
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz, Class[] cs, Object[] os) {
        Class clz = getClass(name);
        if (clz != null && pClz != null && pClz.isAssignableFrom(clz)) {
            return (P) getObject(getClass(name), cs, os);
        }
        return null;
    }

    @Override
    public <P> P getObject(String name, Class<P> pClz, Class cs, Object os) {
        Class clz = getClass(name);
        if (clz != null && pClz != null && cs != null && pClz.isAssignableFrom(clz)) {
            try {
                /*以下调用带参的、私有构造函数*/
                Constructor<P> c = clz.getDeclaredConstructor(cs);
                c.setAccessible(true);
                return c.newInstance(os);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

}
