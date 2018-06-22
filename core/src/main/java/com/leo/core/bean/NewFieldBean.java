package com.leo.core.bean;

import android.support.annotation.NonNull;

import com.leo.core.config.PatternConfig;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnEntryAction;
import com.leo.core.util.LogUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class NewFieldBean {

    public Map<String, Object> getCheckMap(String... args) {
        return getFieldMap((key, obj) -> {
            if (obj instanceof IController) {
                IController c = (IController) obj;
                return new Entry(c.getUBKey(args), c.getUB(args));
            }
            return null;
        });
    }

    public Map<String, Object> getFilterMap(String... args){
        return getFieldMap((key, obj) -> {
            if(Arrays.asList(args).contains(key)){
                return new Entry<>(key, obj);
            }
            return null;
        });
    }

    public String toCheckString() {
        return LogUtil.getLog(getFieldMap((key, obj) -> {
            if (obj instanceof IController) {
                IController c = (IController) obj;
                String type = null;
                if (c.getType() != null) {
                    type = c.getType().toString();
                    type = PatternConfig.getPattern(type, PatternConfig.JAVA_TYPE_SIMPLE);
                }
                return new Entry(key, new ControllerBean(type,
                        c.getDB(), c.getViewBean(), new Entry(c.getUBKey(), c.getUB())));
            }
            return null;
        }));
    }

    protected void initNewFields() {
        initFields(field -> {
            try {
                field.setAccessible(true);
                Object obj = field.getType().newInstance();
                if (obj instanceof IController) {
                    ((IController) obj).initField(field.getName());
                }
                field.set(this, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, true);
    }

    protected Map<String, Object> getFieldMap(IReturnEntryAction<Object, String, Entry<String, Object>> action) {
        Map<String, Object> map = new HashMap<>();
        initFields(field -> {
            try {
                field.setAccessible(true);
                Object item = field.get(this);
                if (!TextUtils.isEmpty(field.getName()) && item != null) {
                    if (action != null) {
                        Entry<String, Object> entry = action.execute(field.getName(), item);
                        if (entry != null && !TextUtils.isEmpty(entry.getKey())
                                && entry.getValue() != null) {
                            map.put(entry.getKey(), entry.getValue());
                        }
                    } else {
                        map.put(field.getName(), item);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }, false);
        return map;
    }

    protected void initFields(IObjAction<Field> action, boolean nw) {
        RunUtil.execute(getFields(this, nw), action);
    }

    protected Set<Field> getFields(Object obj, boolean nw) {
        if (obj != null) {
            Set<Field> set = new LinkedHashSet<>();
            Class clz = obj.getClass();
            while (clz != Object.class) {
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    if (checkField(field, nw)) {
                        set.add(field);
                    }
                }
                clz = clz.getSuperclass();
            }
            return set;
        }
        return null;
    }

    private boolean checkField(Field field, boolean nw) {
        if (field != null) {
            int m = field.getModifiers();
            return field != null && !Modifier.isStatic(m) && isOther(field)
                    && !Modifier.isAbstract(m) && !Modifier.isFinal(m)
                    && (!Modifier.isTransient(m) || nw)
                    && !Modifier.isInterface(field.getType().getModifiers());
        }
        return false;
    }

    protected boolean isOther(@NonNull Field field){
        return true;
    }

}
