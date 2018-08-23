package com.ylink.fullgoal.api.config;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.leo.core.api.core.ThisApi;
import com.leo.core.bean.Completed;
import com.leo.core.bean.DataEmpty;
import com.leo.core.bean.HttpError;
import com.leo.core.bean.ParseCompleted;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.net.Exceptions;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.bean.ParseBean;
import com.ylink.fullgoal.db.table.Request;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.checkNull;

public class ParseApi<T extends ParseApi> extends ThisApi<T> implements IParseApi<T> {

    private int what;
    private String msg;
    private String path;
    private String type;
    private String baseUrl;
    private Handler handler;
    private Map<String, String> map;
    private ParseCompleted parseCompleted;
    private Map<String, ParseTypeBean> typeMap;

    public ParseApi() {
        typeMap = new HashMap<>();
        parseCompleted = new ParseCompleted();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public List<ParseBean> getParseData() {
        return null;
    }

    private Map<String, ParseTypeBean> getMap() {
        return typeMap;
    }

    @Override
    public T init(String type, String baseUrl, String path, Map<String, String> map, int what, String msg) {
        this.type = type;
        this.baseUrl = baseUrl;
        this.path = path;
        this.map = map;
        this.what = what;
        this.msg = msg;
        return getThis();
    }

    @Override
    public T copy() {
        ParseApi<T> api = new ParseApi<>();
        execute(getMap(), (key, value) -> api.getMap().put(key, value.copy()));
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
            String rootName = ParseTypeBean.getName(root);
            String typeName = ParseTypeBean.getName(type);
            if (getMap().get(rootName) == null) {
                getMap().put(rootName, new ParseTypeBean(root, type, action));
            } else {
                if (getMap().get(rootName).getMap().get(typeName) == null) {
                    getMap().get(rootName).getMap().put(typeName,
                            TextUtils.getListData(action));
                } else {
                    getMap().get(rootName).getMap().get(typeName).add(action);
                }
            }
        }
    }

    private ParseTypeBean getParseTypeBean(String type) {
        return getMap().get(type);
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

    private boolean checkRootType(Type type) {
        return type != null
                && !TextUtils.equals(type, ResponseBody.class)
                && !TextUtils.equals(type, String.class)
                && !TextUtils.equals(type, Completed.class)
                && !TextUtils.equals(type, DataEmpty.class)
                && !TextUtils.equals(type, ParseCompleted.class)
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
            handler.post(() -> action.execute(no(type), no(baseUrl), no(path), map, what, no(msg),
                    no(name), obj));
            parseCompleted.add(obj.getClass());
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
                //存储数据
                Request.sav(baseUrl, path, GsonDecodeUtil.encode(map), text);
                ParseTypeBean bean = getParseTypeBean(type);
                if (bean != null && checkRootType(bean.getType())) {
                    onData(path, GsonDecodeUtil.decode(text, bean.getType()),
                            bean.getMap());
                }
                if (!TextUtils.isEmpty(parseCompleted.getData())) {
                    onObj(parseCompleted, ParseCompleted.class);
                }
            }
        }
    }

    /**
     * 解出对象
     */
    private void onObj(String name, Object obj, Class clz) {
        if (check(obj, clz) && clz.isInstance(obj)) {
            ParseTypeBean bean = getParseTypeBean(ParseTypeBean.getName(clz));
            if (bean != null) {
                onData(name, obj, bean.getMap());
            }
        }
    }

    private void onObj(Object obj, Class clz) {
        onObj(path, obj, clz);
    }

    /**
     * 递归遍历处理
     *
     * @param name 参数名
     * @param obj  传入对象
     */
    private void onData(String name, Object obj, Map<String, List<IPathMsgAction>> map) {
        if (checkNull(obj) && check(map)) {
            if (obj instanceof List) {
                if (TextUtils.count(obj) > 0) {
                    Class itemClz = getDataItemType((List) obj);
                    if (itemClz != null) {
                        onItem(name, obj, map.get(ParseTypeBean.getName(
                                TypeToken.getParameterized(List.class, itemClz).getType())));
                    }
                } else {
                    onObj(name, new DataEmpty(), DataEmpty.class);
                }
            } else {
                Class clz = obj.getClass();
                onItem(name, obj, map.get(ParseTypeBean.getName(clz)));
                if (checkClz(clz)) {
                    execute(clz.getDeclaredFields(), field -> {
                        if (!Modifier.isStatic(field.getModifiers())
                                && !Modifier.isFinal(field.getModifiers())
                                && checkClz(field.getType())) {
                            try {
                                field.setAccessible(true);
                                Object item = field.get(obj);
                                if (item != null) {
                                    onData(field.getName(), item, map);
                                }
                            } catch (IllegalAccessException e) {
                                onExceptions(new Exceptions("解析异常", 101, e));
                            }
                        }
                    });
                }
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