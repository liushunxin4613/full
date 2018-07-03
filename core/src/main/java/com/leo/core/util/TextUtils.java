package com.leo.core.util;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import com.leo.core.iapi.inter.IObjAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.leo.core.config.Config.EMPTY;

public class TextUtils {

    private final static String F1 = "\"";

    public static boolean isEmpty(CharSequence text) {
        return android.text.TextUtils.isEmpty(text);
    }

    public static boolean isEmits(Object... args) {
        for (Object obj : args) {
            if (count(obj) >= 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean check(Object... args) {
        for (Object obj : args) {
            if (obj instanceof Boolean && !(Boolean) obj) {
                return false;
            } else if (count(obj) <= 0) {
                return false;
            }
        }
        return true;
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

    public static int count(Object obj) {
        if (obj == null) {
            return -1;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        } else if (obj.getClass().isArray()) {
            if (obj instanceof byte[]) {
                return ((byte[]) obj).length;
            } else if (obj instanceof short[]) {
                return ((short[]) obj).length;
            } else if (obj instanceof int[]) {
                return ((int[]) obj).length;
            } else if (obj instanceof long[]) {
                return ((long[]) obj).length;
            } else if (obj instanceof float[]) {
                return ((float[]) obj).length;
            } else if (obj instanceof double[]) {
                return ((double[]) obj).length;
            } else if (obj instanceof char[]) {
                return ((char[]) obj).length;
            } else if (obj instanceof boolean[]) {
                return ((boolean[]) obj).length;
            } else {
                return ((Object[]) obj).length;
            }
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size();
        } else if (obj instanceof Map) {
            return ((Map) obj).size();
        } else if (obj instanceof SparseArray) {
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

    public static <D> Set<D> getSetData(D... args) {
        Set<D> set = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(args)) {
            Collections.addAll(set, args);
        }
        return set;
    }

    public static <K, V> Map<K, V> getHashMap(IObjAction<Map<K, V>> action) {
        if (action != null) {
            Map<K, V> map;
            action.execute(map = new HashMap<>());
            return map;
        }
        return null;
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
    public static <C> void more(Class<C> clz, IObjAction<C> api, Object... args) {
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

    public static String getIntFormat(int num, boolean enable) {
        if (num == 0) {
            return enable ? "零" : "0";
        } else {
            StringBuilder rn = new StringBuilder();
            int test = num;
            if (test < 0) {
                rn.append("负");
                test = -test;
            }
            String[] xtrim = {"", "十", "百", "千"};
            String[] ytrim = {"", "万", "亿"};
            String[] root = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
            String ss = String.valueOf(test);
            boolean lin = false;
            int length = ss.length();
            for (int i = 0; i < length; i++) {
                int c = ss.charAt(i) - '0';
                int cl = length - i - 1;
                int chu = cl / xtrim.length;
                int yu = cl % xtrim.length;
                if (c == 0) {
                    lin = true;
                } else {
                    if (lin) {
                        rn.append("零");
                        lin = false;
                    }
                    rn.append(enable ? root[c - 1] : c).append(xtrim[yu]);
                }
                if (yu == 0 && chu < ytrim.length) {
                    rn.append(ytrim[chu]);
                }
            }
            if (rn.toString().startsWith("一十")) {
                return rn.substring(1);
            }
            return rn.toString();
        }
    }

    public static String getRandom() {
        String time = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf(Math.random());
        return time + random.substring(2, random.length());
    }

    public static int getEmptyLength(String text) {
        if (!TextUtils.isEmpty(text)) {
            text.replaceAll(EMPTY, "");
            return text.length();
        }
        return 0;
    }

    public static String getMoneyString(double money) {
        DecimalFormat f = new DecimalFormat("######0.00");
        f.setRoundingMode(RoundingMode.HALF_UP);//保留两位四舍五入
        return f.format(money);
    }

    public static String getMoneyString(Double money) {
        return money == null ? null : getMoneyString((double) money);
    }

    @SuppressLint("DefaultLocale")
    public static String getPercentage(double a, double b) {
        float f = (float) (a < b ? a / b : b / a) * 100;
        return String.format("%.2f%%", f);
    }

    public static Map<String, Object> toJSONMap(String text) {
        Object obj = toJSONMap(text, null);
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        return null;
    }

    private static Object toJSONMap(String text, Object obj) {
        try {
            if (!TextUtils.isEmpty(text)) {
                obj = new JSONTokener(text).nextValue();
            }
            if (obj instanceof JSONObject) {
                Map<String, Object> map = new TreeMap<>();
                for (Iterator<String> it = ((JSONObject) obj).keys(); it.hasNext(); ) {
                    String key = it.next();
                    Object item = ((JSONObject) obj).get(key);
                    if (!TextUtils.isEmpty(key)) {
                        map.put(key, toJSONMap(null, item));
                    }
                }
                return map;
            } else if (obj instanceof JSONArray) {
                List<Object> data = new ArrayList<>();
                for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                    Object item = ((JSONArray) obj).get(i);
                    data.add(toJSONMap(null, item));
                }
                return data;
            } else if (obj != null) {
                return obj;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <K, V> Map<K, V> copy(Map<K, V> map, boolean filter, K... args) {
        if (map != null) {
            try {
                Map<K, V> copy = map.getClass().newInstance();
                if (map.size() > 0) {
                    if (TextUtils.isEmpty(args)) {
                        if (filter) {
                            copy.putAll(map);
                        }
                    } else {
                        List<K> data = Arrays.asList((K[]) args);
                        for (Map.Entry<K, V> entry : map.entrySet()) {
                            if (data.contains(entry.getKey()) == !filter) {
                                copy.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                return copy;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static Map<String, String> parse(String text) {
        if (!TextUtils.isEmpty(text)) {
            boolean isKey = true;
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            Map<String, String> map = new TreeMap<>();
            for (char c : text.toCharArray()) {
                switch (c) {
                    default:
                        if (isKey) {
                            key.append(c);
                        } else {
                            value.append(c);
                        }
                        break;
                    case '=':
                        isKey = false;
                        value = new StringBuilder();
                        break;
                    case ';':
                        isKey = true;
                        if(key.length() > 0 && value.length() > 0){
                            map.put(key.toString(), value.toString());
                        }
                        key = new StringBuilder();
                        break;
                }
            }
            if(key.length() > 0 && value.length() > 0){
                map.put(key.toString(), value.toString());
            }
            return map;
        }
        return null;
    }

}