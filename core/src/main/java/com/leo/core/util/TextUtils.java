package com.leo.core.util;

import android.util.SparseArray;

import com.leo.core.iapi.IRunApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextUtils {

    private final static String F1 = "\"";

    public static boolean isEmpty(CharSequence text) {
        return android.text.TextUtils.isEmpty(text);
    }

    public static boolean isEmits(Object... args) {
        for (Object obj : args) {
            if(count(obj) <= 0){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(List data) {
        return data == null || data.isEmpty();
    }

    public static <D> boolean isEmpty(D[] args) {
        return args == null || args.length == 0;
    }

    public static boolean isEmpty(int[] args) {
        return args == null || args.length == 0;
    }

    public static <D> boolean isEmpty(Set<D> set) {
        return set == null || set.size() == 0;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isTrimEmpty(CharSequence text) {
        return isEmpty(text) || isEmpty(text.toString().trim());
    }

    public static int count(Object obj){
        if(obj == null){
            return -1;
        } else if(obj instanceof CharSequence){
            return ((CharSequence) obj).length();
        } else if(obj.getClass().isArray()){
            return ((Object[])obj).length;
        } else if(obj instanceof Collection){
            return ((Collection) obj).size();
        } else if(obj instanceof Map){
            return ((Map) obj).size();
        } else if(obj instanceof SparseArray){
            return ((SparseArray) obj).size();
        }
        return 1;
    }

    public static boolean equals(Object obj, Object obj1) {
        return obj == null ? obj1 == null : obj.equals(obj1);
    }

    public static boolean andEquals(Object obj, Object item, Object... args) {
        if (equals(obj, item)) {
            if (!isEmpty(args)) {
                for (Object child : args) {
                    if (!equals(obj, child)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean orEquals(Object obj, Object item, Object... args) {
        if (equals(obj, item)) {
            return true;
        } else if (!isEmpty(args)) {
            for (Object child : args) {
                if (equals(obj, child)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <C> boolean andIsInstance(Class<C> clz, Object obj, Object... args) {
        if (clz != null && obj != null) {
            if (clz.isInstance(obj)) {
                if (isEmpty(args)) {
                    return true;
                } else {
                    for (Object item : args) {
                        if (!clz.isInstance(item)) {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static <C> boolean orIsInstance(Class<C> clz, Object obj, Object... args) {
        if (clz != null && obj != null) {
            if (clz.isInstance(obj)) {
                if (isEmpty(args)) {
                    return true;
                } else {
                    for (Object item : args) {
                        if (clz.isInstance(item)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String remove2Mark(String text) {
        if (isEmpty(text))
            return null;
        if (text.startsWith(F1))
            text = text.substring(text.indexOf(F1) + 1);
        if (text.endsWith(F1))
            text = text.substring(0, text.lastIndexOf(F1));
        return text.trim();
    }

    public static String removeF1(String text) {
        if (isEmpty(text))
            return null;
        text = text.replace("\\\"", "\"");
        return remove2Mark(text);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isHttpUrl(String url) {
        return !isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static <T> T getItem(List<T> data, int position) {
        if (!TextUtils.isEmpty(data) && position >= 0 && position < data.size()) {
            return data.get(position);
        }
        return null;
    }

    @SafeVarargs
    public static <D> List<D> getAllData(List<D>... data) {
        List<D> all = new ArrayList<>();
        if (!TextUtils.isEmpty(data)) {
            for (List<D> item : data) {
                if (!TextUtils.isEmpty(item)) {
                    all.addAll(item);
                }
            }
        }
        return all;
    }

    @SafeVarargs
    public static <D> List<D> getListData(D... args) {
        List<D> data = new ArrayList<>();
        if (!TextUtils.isEmpty(args)) {
            Collections.addAll(data, args);
        }
        return data;
    }

    /**
     * 检测http地址
     */
    public static boolean checkUrl(String imageUrl) {
        return !TextUtils.isEmpty(imageUrl) && (imageUrl.startsWith("http://") || imageUrl.startsWith("https://"));
    }

    /**
     * 统一化操作
     */
    public static <C> void more(Class<C> clz, IRunApi<C> api, Object... args) {
        if (clz != null && api != null && !isEmpty(args)) {
            for (Object item : args) {
                if (clz.isInstance(item)) {
                    api.execute((C) item);
                }
            }
        }
    }

    /**
     * 获取文件后
     */
    public static String getFileName(String name) {
        if (name != null && TextUtils.isEmpty(name.trim())) {
            name = System.currentTimeMillis() + "";
        } else {
            int w = name.lastIndexOf("/");
            if (w != -1) {
                name = name.substring(w + 1, name.length());
            }
        }
        return name;
    }

    /**
     * 获取文件后缀名
     */
    public static String getEndName(String name, String def) {
        name = getFileName(name);
        if (name != null) {
            name = name.trim();
            int x = name.lastIndexOf(".");
            if (x != -1) {
                return name.substring(x, name.length());
            }
        }
        return def;
    }

    /**
     * 分离http或https Url
     */
    public static String[] getSeparateUrl(String url) {
        if (checkUrl(url)) {
            return separate("/", false, true, null, url);
        }
        return null;
    }

    public static String[] separate(String spit, boolean isFirst, boolean isStart, String... separate) {
        if (!TextUtils.isEmpty(separate) && separate.length == 2 && !TextUtils.isEmpty(separate[1]) && !TextUtils.isEmpty(spit)) {
            String start = separate[0];
            String end = separate[1];
            int a = isFirst ? end.indexOf(spit) : end.lastIndexOf(spit);
            if (a != -1) {
                separate[0] = (start == null ? "" : start) + end.substring(0, a);
                separate[1] = end.substring(a + spit.length(), end.length());
                if (isStart) {
                    separate[0] += spit;
                } else {
                    separate[1] = spit + separate[1];
                }
                if (TextUtils.isEmpty(separate[0])) {
                    separate[0] = null;
                }
                if (TextUtils.isEmpty(separate[1])) {
                    separate[1] = null;
                }
            }
        }
        return separate;
    }

    public static String getIntFormat(int num, boolean enable){
        if(num == 0){
            return enable ? "零" : "0";
        } else {
            StringBuilder rn = new StringBuilder();
            int test = num;
            if(test < 0){
                rn.append("负");
                test = -test;
            }
            String[] xtrim = {"", "十", "百", "千"};
            String[] ytrim = {"", "万", "亿"};
            String[] root = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
            String ss = String.valueOf(test);
            boolean lin = false;
            int length = ss.length();
            for (int i = 0; i < length; i++){
                int c = ss.charAt(i) - '0';
                int cl = length - i - 1;
                int chu = cl / xtrim.length;
                int yu = cl % xtrim.length;
                if(c == 0){
                    lin = true;
                } else {
                    if(lin){
                        rn.append("零");
                        lin = false;
                    }
                    rn.append(enable ? root[c - 1] : c).append(xtrim[yu]);
                }
                if(yu == 0 && chu < ytrim.length){
                    rn.append(ytrim[chu]);
                }
            }
            if(rn.toString().startsWith("一十")){
                return rn.substring(1);
            }
            return rn.toString();
        }
    }

}
