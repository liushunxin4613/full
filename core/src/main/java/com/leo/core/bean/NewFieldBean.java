package com.leo.core.bean;

import com.leo.core.config.PatternConfig;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnEntryAction;
import com.leo.core.util.LogUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
                field.set(this, field.getType().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
        });
        return map;
    }

    protected void initFields(IObjAction<Field> action) {
        RunUtil.execute(getFields(this), action);
    }

    protected Set<Field> getFields(Object obj) {
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
            return field != null && !Modifier.isStatic(m)
                    && !Modifier.isAbstract(m) && !Modifier.isFinal(m) && !Modifier.isTransient(m)
                    && IController.class.isAssignableFrom(field.getType())
                    && !Modifier.isInterface(field.getType().getModifiers());
        }
        return false;
    }

}
