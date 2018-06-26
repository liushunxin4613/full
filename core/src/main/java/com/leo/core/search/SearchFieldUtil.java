package com.leo.core.search;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.leo.core.util.TextUtils;

public class SearchFieldUtil {

	public static Set<String> getFieldSet(Object obj) {
		if (TextUtils.check(obj)) {
			Set<String> set = new TreeSet<>();
			Map<String, Object> map = getFieldMap(obj);
			if (!TextUtils.isEmpty(map)) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					set.add(getJsonString(entry.getValue()));
				}
			}
			return set;
		}
		return null;
	}

	public static String getJsonString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		} else if (obj != null) {
			return JSON.toJSONString(obj);
		}
		return null;
	}

	public static Map<String, Object> getFieldMap(Object obj) {
		try {
			if (TextUtils.check(obj)) {
				Set<Field> set = getFields(obj, false);
				Map<String, Object> map = new TreeMap<>();
				for (Field field : set) {
					field.setAccessible(true);
					Object item = field.get(obj);
					if (!TextUtils.isEmpty(field.getName()) && item != null) {
						map.put(field.getName(), item);
					}
				}
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isOther(Field field) {
		return true;
	}
	
	private static Set<Field> getFields(Object obj, boolean nw) {
		if (obj != null) {
			Set<Field> set = new LinkedHashSet<>();
			Class<?> clz = obj.getClass();
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

	private static boolean checkField(Field field, boolean nw) {
		if (field != null) {
			int m = field.getModifiers();
			return field != null && !Modifier.isStatic(m) && isOther(field)
					&& !Modifier.isAbstract(m) && !Modifier.isFinal(m)
					&& (!Modifier.isTransient(m) || nw)
					&& !Modifier.isInterface(field.getType().getModifiers());
		}
		return false;
	}

}