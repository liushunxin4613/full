package com.ylink.fullgoal.api.config;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.leo.core.api.core.ThisApi;
import com.leo.core.bean.Completed;
import com.leo.core.bean.DataEmpty;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.net.Exceptions;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.bean.ParseBean;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.checkNull;
import static com.leo.core.util.TextUtils.getListData;

public class ParseApi<T extends ParseApi> extends ThisApi<T> implements IParseApi<T> {

    private String path;
    private int what;
    private String msg;
    private Map<Type, Map<Type, List<IPathMsgAction>>> map;
    private Handler handler;

    public ParseApi() {
        map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public List<ParseBean> getParseData() {
        return null;
    }

    private Map<Type, Map<Type, List<IPathMsgAction>>> getMap() {
        return map;
    }

    @Override
    public T init(String path, int what, String msg) {
        this.path = path;
        this.what = what;
        this.msg = msg;
        return getThis();
    }

    @Override
    public T copy() {
        ParseApi<T> api = new ParseApi<>();
        execute(getMap(), (key, value) -> api.getMap().put(key, value));
        return (T) api;
    }

    @Override
    public <A> T add(Class<A> root, IPathMsgAction<A> action) {
        add(root, root, action);
        return getThis();
    }

    @Override
    public <A> T add(TypeToken<A> root, IPathMsgAction<A> action) {
        add(root, root, action);
        return getThis();
    }

    @Override
    public <A, B> T add(Class<A> root, Class<B> clz, IPathMsgAction<B> action) {
        if (check(root, clz, action)) {
            mapAdd(root, clz, action);
        }
        return getThis();
    }

    @Override
    public <A, B> T addList(Class<A> root, Class<B> clz, IPathMsgAction<List<B>> action) {
        if (check(root, clz, action)) {
            mapAdd(root, TypeToken.getParameterized(List.class, clz).getType(), action);
        }
        return getThis();
    }

    @Override
    public <A, B> T add(Class<A> root, TypeToken<B> token, IPathMsgAction<B> action) {
        if (check(root, token, action)) {
            mapAdd(root, token.getType(), action);
        }
        return getThis();
    }

    @Override
    public <A, B> T add(TypeToken<A> root, Class<B> clz, IPathMsgAction<B> action) {
        if (check(root, clz, action)) {
            mapAdd(root.getType(), clz, action);
        }
        return getThis();
    }

    @Override
    public <A, B> T addList(TypeToken<A> root, Class<B> clz, IPathMsgAction<List<B>> action) {
        if (check(root, clz, action)) {
            mapAdd(root.getType(), TypeToken.getParameterized(List.class, clz).getType(), action);
        }
        return getThis();
    }

    @Override
    public <A, B> T add(TypeToken<A> root, TypeToken<B> token, IPathMsgAction<B> action) {
        if (check(root, token, action)) {
            mapAdd(root.getType(), token.getType(), action);
        }
        return getThis();
    }

    private void mapAdd(Type root, Type type, IPathMsgAction action) {
        if (check(root, type, action)) {
            Map<Type, List<IPathMsgAction>> rootMap = getMap().get(root);
            if (rootMap != null && rootMap.get(type) != null) {
                getMap().get(root).get(type).add(action);
            } else if (rootMap != null) {
                getMap().get(root).put(type, getListData(action));
            } else {
                rootMap = new HashMap<>();
                rootMap.put(type, getListData(action));
                getMap().put(root, rootMap);
            }
        }
    }

    @Override
    public T clearParse() {
        getMap().clear();
        return getThis();
    }

    @Override
    public <B> T parse(B bean) {
        if (bean instanceof String) {
            onString((String) bean);
        } else if (bean instanceof ResponseBody) {
            onResponseBody((ResponseBody) bean);
        } else if (bean instanceof Completed) {
            onCompleted((Completed) bean);
        } else if (bean instanceof Exceptions) {
            onExceptions((Exceptions) bean);
        }
        return getThis();
    }

    /**
     * 防止String解析非根节点的类数据
     * @param type 类型
     * @return 是否后加数据
     */
    private boolean checkRootType(Type type) {
        return type != null && !TextUtils.equals(type, ResponseBody.class)
                && !TextUtils.equals(type, String.class)
                && !TextUtils.equals(type, Completed.class)
                && !TextUtils.equals(type, DataEmpty.class)
                && !TextUtils.equals(type, Exceptions.class);
    }

    /**
     * 核心解析对象数据
     *
     * @param name 参数名
     * @param obj  传出对象
     * @param data 传出回调
     */
    private void onItem(String name, Object obj, List<IPathMsgAction> data) {
        if (TextUtils.checkNull(obj) && TextUtils.check(data)) {
            execute(data, action -> executeAction(name, action, obj));
        }
    }

    private void executeAction(String name, IPathMsgAction action, Object obj) {
        if (TextUtils.check(action) && TextUtils.checkNull(obj)) {
            handler.post(() -> action.execute(name, no(path), what, no(msg), obj));
        }
    }

    /**
     * 解析string
     *
     * @param text text
     */
    private void onString(String text) {
        onObj(text, String.class);
        if (TextUtils.check(getMap())) {
            if (TextUtils.isNotJsonString(text)) {
                onExceptions(new Exceptions("数据格式异常", HttpError.ERROR_JSON_ERROR,
                        new RuntimeException("数据格式异常")));
            } else {
                execute(getMap(), (type, map) -> {
                    if (checkRootType(type)) {
                        onData(null, GsonDecodeUtil.decode(text, type), type, map);
                    }
                });
            }
        }
    }

    /**
     * 解出对象
     *
     * @param obj obj
     */
    private void onObj(Object obj, Class clz) {
        if (check(obj, clz) && clz.isInstance(obj)) {
            onData(null, obj, clz, getMap().get(clz));
        }
    }

    /**
     * 递归遍历处理
     *
     * @param name 参数名
     * @param obj  传入对象
     * @param type 传入对象类型
     * @param map  传入对象可能对应map
     */
    private void onData(String name, Object obj, Type type, Map<Type, List<IPathMsgAction>> map) {
        if (checkNull(obj) && check(map)) {
            onItem(name, obj, map.get(type));
            Class clz = obj.getClass();
            if (obj instanceof List) {
                if (!TextUtils.isEmpty(obj)) {
                    Class itemClz = getDataItemType((List) obj);
                    if (itemClz != null) {
                        onItem(name, obj, map.get(TypeToken.getParameterized(List.class, itemClz)
                                .getType()));
                    }
                } else {
                    LogUtil.ee(this, m -> m.put("name", name).put("obj", obj).put("type", type.toString()));
                    onItem(name, new DataEmpty(), map.get(DataEmpty.class));
                }
            } else if (checkClz(clz)) {
                execute(clz.getDeclaredFields(), field -> {
                    if (!Modifier.isStatic(field.getModifiers())
                            && !Modifier.isFinal(field.getModifiers())
                            && checkClz(field.getType())) {
                        try {
                            field.setAccessible(true);
                            Object item = field.get(obj);
                            if (item != null) {
                                onData(field.getName(), item, item.getClass(), map);
                            }
                        } catch (IllegalAccessException e) {
                            onExceptions(new Exceptions("解析异常", 101, e));
                        }
                    }
                });
            }
        }
    }

    private void onResponseBody(ResponseBody body) {
        try {
            onObj(body, ResponseBody.class);
            onString(body.string());
        } catch (IOException e) {
            onExceptions(new Exceptions("body.string()异常", 102, e));
        }
    }

    private void onCompleted(Completed completed) {
        onObj(completed, Completed.class);
    }

    private void onExceptions(Exceptions exception) {
        onObj(exception, Exceptions.class);
        executeNon(exception.getE(), Throwable::printStackTrace);
    }

    //以下为私有方法

    private Class getDataItemType(List data) {
        if (!TextUtils.isEmpty(data)) {
            for (Object obj : data) {
                if (obj != null) {
                    return obj.getClass();
                }
            }
        }
        return null;
    }

    private boolean checkClz(Class clz) {
        return clz != null && !clz.isPrimitive() && !String.class.isAssignableFrom(clz) &&
                !LinkedTreeMap.class.isAssignableFrom(clz);
    }

}