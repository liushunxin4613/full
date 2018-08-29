package com.leo.core.util;

public class JavaTypeUtil {

    public static int getInt(Object obj, int i) {
        if (obj instanceof Integer) {
            return (int) obj;
        } else if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (Exception ignored) {
            }
        }
        return i;
    }

    public static float getfloat(Object obj, float f) {
        if (obj instanceof Float) {
            return (float) obj;
        } else if (obj instanceof String) {
            try {
                return Float.valueOf((String) obj);
            } catch (Exception ignored) {
            }
        }
        return f;
    }

    public static double getdouble(Object obj, double d) {
        if (obj instanceof Double) {
            return (double) obj;
        } else if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (Exception ignored) {
            }
        }
        return d;
    }

    public static boolean getboolean(Object obj, boolean b) {
        if (obj instanceof Boolean) {
            return (boolean) obj;
        } else {
            try {
                return Boolean.valueOf((String) obj);
            } catch (Exception ignored) {
            }
        }
        return b;
    }

    public static String getString(Object obj) {
        return String.valueOf(obj);
    }

}