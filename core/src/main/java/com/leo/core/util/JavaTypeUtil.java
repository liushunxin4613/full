package com.leo.core.util;

public class JavaTypeUtil {

    public static int getInt(Object obj) {
        return getInt(obj, 0);
    }

    public static int getInt(Object obj, int i) {
        if (obj instanceof Integer) {
            return (int) obj;
        } else if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (Exception e) {
                return i;
            }
        }
        return i;
    }

    public static String getString(Object obj) {
        return String.valueOf(obj);
    }

    public static String getDefaultNon(String text){
        return getDefault(text, null, "");
    }

    public static <D> D getDefault(D item, D eq, D def){
        if (TextUtils.equals(item, eq)){
            return def;
        }
        return item;
    }

}
