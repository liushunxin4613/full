package com.ylink.fullgoal.controllerApi.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiFragment;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.full.FullSearchControllerApi;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.main.SurfaceActivity;
import com.ylink.fullgoal.vo.SearchVo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.leo.core.util.TextUtils.count;
import static com.ylink.fullgoal.config.ComConfig.SHOW;
import static com.ylink.fullgoal.config.ComConfig.UPDATE;
import static com.ylink.fullgoal.config.Config.FIELDS;

@SuppressWarnings("ReturnInsideFinallyBlock")
public class SurfaceControllerApi<T extends SurfaceControllerApi, C> extends ControllerApi<T, C> {

    private DVo vo;

    public SurfaceControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void onCom(int what, String com, String msg, Object... args) {
        super.onCom(what, com, msg, args);
        executeNon(com, c -> {
            switch (c){
                case UPDATE:
                    notifyDataChanged();
                    break;
                case SHOW:
                    show(msg);
                    break;
            }
        });
    }

    //自定义

    @SafeVarargs
    protected final T startSurfaceActivity(Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, args);
        return getThis();
    }

    @SafeVarargs
    protected final T startFinishSurfaceActivity(Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, args);
        return getThis();
    }


    @SafeVarargs
    protected final T startSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    protected final T startFinishSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    protected final BaseControllerApiFragment getFragment(Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, args);
    }

    @SafeVarargs
    protected final BaseControllerApiFragment getFragment(Bundle bundle, Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, bundle, args);
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz, Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz, View rootView) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, rootView).controllerApi();
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz) {
        return getViewControllerApi(clz, (Integer) null);
    }

    protected <B extends IControllerApi> B getDialogControllerApi(Class<B> clz, Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiDialog<>(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <B extends IControllerApi> B getDialogControllerApi(Class<B> clz) {
        return getDialogControllerApi(clz, null);
    }

    protected <B> B getBundle(Bundle bundle, Class<B> clz) {
        if (bundle != null && clz != null) {
            return decode(bundle.getString(clz.toString()), clz);
        }
        return null;
    }

    protected <B> List<B> getBundleList(Bundle bundle, Class<B> clz) {
        if (bundle != null && clz != null) {
            Type type = TypeToken.getParameterized(List.class, clz).getType();
            return decode(bundle.getString(type.toString()), type);
        }
        return null;
    }

    protected Bundle getBundle(Object... args) {
        if (!TextUtils.isEmpty(args)) {
            Bundle bundle = new Bundle();
            execute(args, obj -> bundle.putString(getObjectType(obj).toString(), encode(obj)));
            return bundle;
        }
        return null;
    }

    /**
     * 查询基础类
     * @param clz clz
     * @param action action
     * @param <B> B
     */
    protected <B> void addList(Class<B> clz, IPathMsgAction<List<B>> action){
        addList(DataFg.class, clz, action);
    }

    protected void startSearch(String search, ArrayList<String> filterData) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.SEARCH, search);
        bundle.putStringArrayList(Config.FILTERS, filterData);
        startSurfaceActivity(bundle, FullSearchControllerApi.class);
    }

    protected void startSearch(String search) {
        startSearch(search, null);
    }

    protected int getResTvColor(CharSequence text) {
        return !TextUtils.isEmpty(text) ? R.color.tv : R.color.tv1;
    }

    @SuppressLint("ResourceAsColor")
    protected T setTextView(TextView tv, String name, String hint) {
        if (TextUtils.isEmpty(name)) {
            setText(tv, hint).setTextColor(tv, R.color.tv1);
        } else {
            setText(tv, name).setTextColor(tv, R.color.tv);
        }
        return getThis();
    }

    protected Map<String, Object> getCheck(Object obj, Set<String> must, Set<String> all) {
        if (!TextUtils.isEmpty(all) && obj != null) {
            Map<String, Object> mp = new HashMap<>();
            Class clz = obj.getClass();
            for (String key : all) {
                Object item = null;
                try {
                    Field field = clz.getDeclaredField(key);
                    field.setAccessible(true);
                    item = field.get(obj);
                } catch (Exception ignored) {
                } finally {
                    if (count(item) > 0) {
                        mp.put(key, item);
                    } else if (!TextUtils.isEmpty(must) && must.contains(getValue(key))) {
                        String value = getValue(key);
                        value = TextUtils.isEmpty(value) ? key : value;
                        show(String.format("%s不能为空", value));
                        return null;
                    }
                }
            }
            return mp;
        }
        return null;
    }

    protected Map<String, Object> getCheck(Object obj, Set<String> must) {
        Set<String> all = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(FIELDS)) {
            for (String[] args : FIELDS) {
                if (count(args) == 2 && !TextUtils.isEmpty(args[0])) {
                    all.add(args[0]);
                }
            }
        }
        return getCheck(obj, must, all);
    }

    protected Map<String, Object> getCheckMap(Map<String, Object> map, Set<String> must) {
        if (!TextUtils.isEmpty(map) && !TextUtils.isEmpty(must)) {
            Set<String> keys = map.keySet();
            for (String key : must) {
                if(!TextUtils.isEmpty(key) && !keys.contains(getKey(key))){
                    show(String.format("%s(%s)不能为空", key, getKey(key)));
                    return null;
                }
            }
        }
        return map;
    }

    protected Map<String, Object> getCheckMap(Object obj, Set<String> must) {
        if (obj != null) {
            return getCheckMap(getFieldMap(obj), must);
        }
        return null;
    }

    /**
     * 获取value
     *
     * @param value value
     * @return key
     */
    protected String getKey(String[][] argss, String value) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(argss)) {
            for (String[] args : argss) {
                if (count(args) == 2 && TextUtils.equals(args[1], value)) {
                    return args[0];
                }
            }
        }
        return null;
    }

    /**
     * 获取key
     *
     * @param key key
     * @return value
     */
    protected String getValue(String[][] argss, String key, String def) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(argss)) {
            for (String[] args : argss) {
                if (count(args) == 2 && TextUtils.equals(args[0], key)) {
                    return args[1];
                }
            }
        }
        return def;
    }


    protected String getKey(String value) {
        return getKey(FIELDS, value);
    }

    protected String getValue(String key) {
        return getValue(FIELDS, key, null);
    }

    protected String getValue(String[][] argss, String key) {
        return getValue(argss, key, null);
    }

    protected <A> void executeSearch(Class<A> clz, IObjAction<A> action) {
        if (clz != null && action != null) {
            TypeToken<SearchVo<A>> token = (TypeToken<SearchVo<A>>) TypeToken
                    .getParameterized(SearchVo.class, clz);
            execute(getFinish(), token, vo -> action.execute(vo.getObj()));
        }
    }

    protected TextWatcher getMoneyTextWatcher(IObjAction<String> action) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                onAfterTextChanged(s, action);
            }
        };
    }

    protected void onAfterTextChanged(Editable text, IObjAction<String> action) {
        String temp = text.toString();
        int posDot = temp.indexOf(".");
        int endPosDot = temp.lastIndexOf(".");
        if (posDot >= 0 && endPosDot != posDot) {
            text.delete(endPosDot, endPosDot + 1);
            return;
        }
        if (posDot > 0) {
            if (temp.length() - posDot - 1 > 2) {
                text.delete(posDot + 3, posDot + 4);
            }
        } else if (posDot == 0) {
            text.insert(0, "0");
        }
        executeNon(temp, action);
    }

    protected Type getObjectType(Object obj) {
        if (obj instanceof List) {
            List data = (List) obj;
            if (!TextUtils.isEmpty(data)) {
                for (Object item : data) {
                    if (item != null) {
                        return TypeToken.getParameterized(List.class,
                                getObjectType(item)).getType();
                    }
                }
            }
            return List.class;
        } else if (obj != null) {
            return obj.getClass();
        }
        return null;
    }

    public Map<String, String> getFieldStringMap(Object obj) {
        if (obj != null) {
            Map<String, String> map = new HashMap<>();
            initFieldMap(obj, (key, value) -> map.put(key, getLog(value)));
            return map;
        }
        return null;
    }

    public Map<String, Object> getFieldMap(Object obj) {
        if (obj != null) {
            Map<String, Object> map = new HashMap<>();
            initFieldMap(obj, map::put);
            return map;
        }
        return null;
    }

    public void initFieldMap(Object obj, IMapAction<String, Object> action) {
        if (obj != null && action != null) {
            Set<Field> set = getFields(obj);
            if (!TextUtils.isEmpty(set)) {
                for (Field field : set) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        if (value != null) {
                            action.execute(field.getName(), value);
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
        }
    }

    public Set<Field> getFields(Object obj) {
        if (obj != null) {
            Set<Field> set = new LinkedHashSet<>();
            Class clz = obj.getClass();
            while (clz != Object.class) {
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    if (checkField(field)) {
                        set.add(field);
                    }
                }
                clz = clz.getSuperclass();
            }
            return set;
        }
        return null;
    }

    private boolean checkField(Field field) {
        if (field != null) {
            int m = field.getModifiers();
            return field != null && !TextUtils.isEmpty(field.getName())
                    && !Modifier.isAbstract(m) && !Modifier.isFinal(m)
                    && !Modifier.isTransient(m) && !Modifier.isStatic(m)
                    && !Modifier.isInterface(field.getType().getModifiers());
        }
        return false;
    }

    protected <D> D get(List<D> data){
        if(!TextUtils.isEmpty(data)){
            return data.get(0);
        }
        return null;
    }

    protected String getNull(String text){
        return TextUtils.isEmpty(text) ? null : text;
    }

    //有关于数据处理的

    protected <B> B no(IReturnAction<DVo, B> action) {
        return no(getVo(), action);
    }

    protected <A extends IController, B> B gt(Class<B> clz, IReturnAction<DVo, A> action){
        if(clz != null && action != null){
            A obj = no(action);
            if(obj != null && clz.isInstance(obj)){
                return (B) obj;
            }
        }
        return null;
    }

    protected <A extends IController, B> B gt(IReturnAction<DVo, A> action, IReturnAction<A, B> an){
        if(action != null && an != null){
            A obj = no(action);
            if(obj != null){
                return an.execute(obj);
            }
        }
        return null;
    }

    protected <A extends IController, B> B gtv(IReturnAction<DVo, A> action){
        if(action != null){
            A obj = no(action);
            if(obj != null){
                return (B) obj.getViewBean();
            }
        }
        return null;
    }

    protected <A extends IController, B> B gtd(IReturnAction<DVo, A> action){
        if(action != null){
            A obj = no(action);
            if(obj != null){
                return (B) obj.getDB();
            }
        }
        return null;
    }

    protected T setVo(DVo vo){
        this.vo = vo;
        return getThis();
    }

    protected DVo getVo() {
        if (vo == null) {
            vo = new DVo();
        }
        return vo;
    }

    protected T ioo(IObjAction<DVo> action) {
        executeNon(getVo(), action);
        return getThis();
    }

    protected <B> T iso(IReturnAction<DVo, B> action, IObjAction<B> ac){
        if(action != null && ac != null){
            B obj = no(action);
            if(obj != null){
                ac.execute(obj);
            }
        }
        return getThis();
    }

    protected <B, D> T ioo(IReturnAction<DVo, B> action, IReturnAction<B, D> an){
        if(action != null && an != null){
            B obj = no(action);
            if(obj != null){
                an.execute(obj);
            }
        }
        return getThis();
    }

    protected <B, D> T iso(IReturnAction<DVo, B> action, IReturnAction<B, D> an, IObjAction<D> ac){
        if(action != null && an != null && ac != null){
            B obj = no(action);
            if(obj != null){
                D item = an.execute(obj);
                if(item != null){
                    ac.execute(item);
                }
            }
        }
        return getThis();
    }

}