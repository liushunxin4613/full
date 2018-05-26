package com.ylink.fullgoal.api.config;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.leo.core.api.core.ThisApi;
import com.leo.core.bean.Completed;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.IObjAction;
import com.leo.core.iapi.IParseApi;
import com.leo.core.net.Exceptions;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.util.ToastUtil;
import com.ylink.fullgoal.ht.BaseHt;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class ParseApi<T extends ParseApi> extends ThisApi<T> implements IParseApi<T> {

    private List<Type> data;
    private Map<String, Type> typeMap;
    private Map<Type, List<IObjAction>> apiMap;

    public ParseApi() {
        this.data = new ArrayList<>();
        this.typeMap = new HashMap<>();
        this.apiMap = new HashMap<>();
    }

    @Override
    public <A> T addRootType(Class<A>... args) {
        execute(args, obj -> executeNon(getExecute(TypeToken.get(obj), TypeToken::getType),
                item -> data.add(item)));
        return getThis();
    }

    @Override
    public <A> T addRootType(TypeToken<A>... args) {
        execute(args, obj -> executeNon(getExecute(obj, TypeToken::getType), item -> data.add(item)));
        return getThis();
    }

    @Override
    public <A> T addRootType(int index, Class<A>... args) {
        if (index >= 0 && index < data.size() && !TextUtils.isEmpty(args)) {
            List<Type> typeData = new ArrayList<>();
            execute(args, obj -> executeNon(getExecute(TypeToken.get(obj), TypeToken::getType),
                    typeData::add));
            data.addAll(index, typeData);
        }
        return getThis();
    }

    @Override
    public <A> T addRootType(int index, TypeToken<A>... args) {
        if (index >= 0 && index < data.size() && !TextUtils.isEmpty(args)) {
            List<Type> typeData = new ArrayList<>();
            execute(args, obj -> executeNon(getExecute(obj, TypeToken::getType), typeData::add));
            data.addAll(index, typeData);
        }
        return getThis();
    }

    @Override
    public T clearRootData() {
        data.clear();
        return getThis();
    }

    @Override
    public <A> T put(String key, TypeToken<A> token) {
        if (!TextUtils.isEmpty(key) && token != null) {
            typeMap.put(key, token.getType());
        }
        return getThis();
    }

    @Override
    public <A> T put(String key, Class<A> clz) {
        put(key, TypeToken.get(clz));
        return getThis();
    }

    @Override
    public T clearPutParseMap() {
        typeMap.clear();
        return getThis();
    }

    @Override
    public <A> T add(TypeToken<A> token, IObjAction<A> action) {
        if (token != null && action != null) {
            List<IObjAction> data = apiMap.get(token.getType());
            if (data == null) {
                apiMap.put(token.getType(), new ArrayList<>());
            }
            apiMap.get(token.getType()).add(action);
        }
        return getThis();
    }

    @Override
    public <A> T add(Class<A> clz, IObjAction<A> action) {
        add(TypeToken.get(clz), action);
        return getThis();
    }

    @Override
    public T clearAddParseMap() {
        apiMap.clear();
        return getThis();
    }

    @Override
    public T clearParse() {
        clearRootData();
        clearPutParseMap();
        clearAddParseMap();
        return getThis();
    }

    @Override
    public <B> T execute(B bean) {
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

    private <A> void onItem(A item, Type type) {
        executeNon(item, obj -> execute(apiMap.get(type), action -> {
            if(obj instanceof BaseHt){
                if(!((BaseHt) obj).isSuccess() && !TextUtils.isEmpty(((BaseHt) obj).getMessage())){
                    ToastUtil.show(((BaseHt) obj).getMessage());
                } else {
                    action.execute(obj);
                }
            } else {
                action.execute(obj);
            }
        }));
    }

    private void onString(String text) {
        if (!TextUtils.isEmpty(data)) {
            execute(data, type -> onData(GsonDecodeUtil.decode(text, type), type));
        }
        if (!TextUtils.isEmpty(typeMap)) {
            toJson(text);
        }
    }

    private void onData(Object obj, Type type) {
        if (obj != null) {
            onItem(obj, type);
            Class clz = obj.getClass();
            if (obj instanceof List) {
                if (!TextUtils.isEmpty(obj)) {
                    Class itemClz = getDataItemType((List) obj);
                    if (itemClz != null) {
                        onItem(obj, get(List.class, itemClz));
                    }
                }
            } else if (check(clz)) {
                execute(clz.getDeclaredFields(), field -> execute(!Modifier.isStatic(field.getModifiers())
                        && check(field.getType()) && !TextUtils.equals(field.getName(), "val$contentType"), () -> {
                    try {
                        field.setAccessible(true);
                        Object item = field.get(obj);
                        if (item != null) {
                            onData(item, item.getClass());
                        }
                    } catch (IllegalAccessException e) {
                        onExceptions(new Exceptions("解析异常", 101, e));
                    }
                }));
            }
        }
    }

    private void toJson(String text) {
        try {
            if (!TextUtils.isEmpty(text)) {
                Object obj = new JSONTokener(text).nextValue();
                if (obj instanceof JSONObject) {
                    JSONObject jo = (JSONObject) obj;
                    execute(typeMap, (key, value) -> execute(jo.has(key), () -> {
                        try {
                            Object mode = jo.get(key);
                            if (mode instanceof String) {
                                Object item = GsonDecodeUtil.decode(mode, value);
                                onData(item, item.getClass());
                            }
                        } catch (JSONException e) {
                            onExceptions(new Exceptions("json解析异常", HttpError.ERROR_JSON_PARSE, e));
                        }
                    }));
                }
            } else {
                onExceptions(new Exceptions("json字符串不能为空", HttpError.ERROR_DATA_NULL,
                        new RuntimeException("json字符串不能为空")));
            }
        } catch (Exception e) {
            onExceptions(new Exceptions("json解析异常", HttpError.ERROR_JSON_PARSE, e));
        }
    }

    private void onResponseBody(ResponseBody body) {
        try {
            onString(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean check(Class clz) {
        return clz != null && !clz.isPrimitive() && !clz.isPrimitive() &&
                !Modifier.isAbstract(clz.getModifiers()) && !String.class.isAssignableFrom(clz)
                && !LinkedTreeMap.class.isAssignableFrom(clz);
    }

    private void onCompleted(Completed completed) {
        LogUtil.ii(this, "本次请求结束!!!");
        onItem(completed, completed.getClass());
    }

    private void onExceptions(Exceptions exception) {
        onItem(exception, exception.getClass());
        executeNon(exception.getE(), Throwable::printStackTrace);
    }

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

}
