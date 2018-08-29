package com.leo.core.util;

import android.annotation.SuppressLint;
import android.content.res.XmlResourceParser;
import android.util.SparseArray;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.MMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

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

    public static boolean checkNull(Object... args) {
        for (Object obj : args) {
            if (obj == null) {
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

    /**
     * 检测http地址
     */
    public static boolean checkUrl(String imageUrl) {
        return !TextUtils.isEmpty(imageUrl) && (imageUrl.startsWith("http://")
                || imageUrl.startsWith("https://"));
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

    public static String getSMoneyString(double money) {
        return getDecimalString(getMoneyString(money), 2);
    }

    public static String getSMoneyString(Double money) {
        return money == null ? null : getSMoneyString((double) money);
    }

    public static String getDecimalString(String text, int dec) {
        if (!TextUtils.isEmpty(text)) {
            if (!Pattern.compile("^([-]?[0-9]+\\.[0-9]+)$|^([-]?[1-9][0-9]*)$|^(0)$")
                    .matcher(text).find()) {
                return null;
            } else {
                int index = text.indexOf(".");
                if (index >= 0) {
                    int a = index + 1 + dec;
                    if (a > text.length()) {
                        a = text.length();
                    }
                    text = text.substring(0, a);
                }
            }
        }
        if (!TextUtils.isEmpty(text)) {
            boolean decimal = false;
            StringBuilder start = new StringBuilder();
            StringBuilder end = new StringBuilder();
            for (char c : text.toCharArray()) {
                switch (c) {
                    default:
                        if (end.length() > 0) {
                            start.append(end);
                            end.setLength(0);
                        }
                        start.append(c);
                        break;
                    case '.':
                        decimal = true;
                        end.append(c);
                        break;
                    case '0':
                        if (decimal) {
                            end.append(c);
                        } else if (start.length() <= 0) {
                            start.append(c);
                        }
                        break;
                }
            }
            return start.toString();
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public static String getPercentage(double a, double b) {
        float f = (float) (b == 0 ? 0 : a / b * 100);
        return String.format("%.2f%%", f);
    }

    public static Map<String, Object> toJSONMap(String text) {
        Object obj = toJSONMap(text, null);
        if (obj instanceof Map) {
            return toJSONMap(String.class, Object.class, text);
        }
        return null;
    }

    public static <K, V> Map<K, V> toJSONMap(Class<K> kClz, Class<V> vClz, String text) {
        if (TextUtils.check(kClz, vClz, text)) {
            return toJSONMap(kClz, vClz, toJSONMap(text, null));
        }
        return null;
    }

    public static <K, V> Map<K, V> toJSONMap(Class<K> kClz, Class<V> vClz, Object obj) {
        if (obj instanceof Map) {
            Map<K, V> map = new LinkedHashMap<>();
            RunUtil.execute((Map) obj, (key, value) -> {
                if (kClz.isInstance(key) && vClz.isInstance(value)) {
                    map.put((K) key, (V) value);
                }
            });
            return map;
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
                        if (key.length() > 0 && value.length() > 0) {
                            map.put(key.toString(), value.toString());
                        }
                        key = new StringBuilder();
                        break;
                }
            }
            if (key.length() > 0 && value.length() > 0) {
                map.put(key.toString(), value.toString());
            }
            return map;
        }
        return null;
    }

    public static void onXmlResourceParser(XmlResourceParser parser) {
        try {
            int type = parser.getEventType();
            while (type != parser.END_DOCUMENT) {
                LogUtil.ee("onXmlResourceParser", "-----------------------------------------------");
                LogUtil.ee("parser.getName()", parser.getName());
                LogUtil.ee("parser.getDepth()", parser.getDepth());
                switch (type) {
                    case XmlPullParser.START_TAG:
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            LogUtil.ee(parser.getAttributeName(i), parser.getAttributeValue(i));
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }

    public static <E> boolean contains(Iterable<E> col, E obj) {
        if (TextUtils.check(col, obj)) {
            if (col instanceof Collection) {
                return ((Collection) col).contains(obj);
            } else {
                for (E e : col) {
                    if (equals(e, obj)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static <E> boolean contains(E[] args, E obj) {
        if (TextUtils.check(args, obj)) {
            for (E e : args) {
                if (equals(e, obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <E> int indexOf(E[] args, E obj) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (equals(args[i], obj)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String getUriParams(Map<String, ?> map) {
        if (!TextUtils.isEmpty(map)) {
            StringBuilder builder = new StringBuilder();
            RunUtil.execute(map, (key, value) -> builder.append(key)
                    .append("=").append(LogUtil.getLog(false, value)));
            return builder.toString();
        }
        return null;
    }

    public static boolean checkParams(Object v1, Object v2) {
        return v1 == null && v2 == null || v1 != null && v2 != null
                && TextUtils.equals(String.class, v1.getClass())
                && TextUtils.equals(String.class, v2.getClass())
                && mapEquals(TextUtils.toJSONMap((String) v1), TextUtils.toJSONMap((String) v2));
    }

    public static boolean mapEquals(Map<?, ?> m1, Map<?, ?> m2) {
        if (m1 == null && m2 == null) {
            return true;
        } else if (m1 != null && m2 != null && m1.size() == m2.size()) {
            for (Map.Entry entry : m1.entrySet()) {
                if (!TextUtils.equals(entry.getValue(), m2.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @SafeVarargs
    public static <K, V> Map<K, V> getMap(Map<K, V> map, boolean filter,
                                          K... args) {
        if (map != null) {
            Map<K, V> rm = new LinkedHashMap<>();
            List<K> data = Arrays.asList(args);
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (data.contains(entry.getKey()) == filter) {
                    rm.put(entry.getKey(), entry.getValue());
                }
            }
            return rm;
        }
        return null;
    }

    public static Map<String, Object> getMap(String text, boolean filter, String... args) {
        return getMap(toJSONMap(text), filter, args);
    }

    public static String toJsonString(String text, boolean filter, String... args) {
        return GsonDecodeUtil.encode(getMap(text, filter, args));
    }

    public static String getJsonStringValue(String text, String key) {
        Map<String, Object> map = toJSONMap(text);
        if (checkNull(map, key)) {
            Object obj = map.get(key);
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        return null;
    }

    public static boolean isNotJsonString(String text) {
        return isEmpty(text) || !(isJsonObjectString(text) || isJsonArrayString(text));
    }

    public static boolean isJsonObjectString(String text) {
        return !isEmpty(text) && (text.startsWith("{") && text.endsWith("}"));
    }

    public static boolean isJsonArrayString(String text) {
        return !isEmpty(text) && (text.startsWith("[") && text.endsWith("]"));
    }

    @SuppressLint("DefaultLocale")
    public static <D> void lookDataJsonString(Object obj, List<D> data) {
        if (TextUtils.check(obj, data)) {
            LogUtil.ee(obj, String.format("List -- %d --> [", data.size()));
            for (int i = 0; i < data.size(); i++) {
                LogUtil.ee(obj, String.format("总%d个,当前第%d个", data.size(), i + 1));
                LogUtil.ee(obj, data.get(i));
            }
            LogUtil.ee(obj, String.format("] <-- %d -- List", data.size()));
        } else if (TextUtils.checkNull(obj, data)) {
            LogUtil.ee(obj, "[]");
        }
    }

    public static <K, V> Map<K, V> map(IObjAction<MMap<K, V>> action) {
        if (action != null) {
            MMap<K, V> mMap = new MMap<>();
            action.execute(mMap.map(new LinkedHashMap<>()));
            return mMap.map();
        }
        return null;
    }

    public static <K, V> Map<K, V> map(Class<K> k, Class<V> v, IObjAction<MMap<K, V>> action) {
        if (TextUtils.check(k, v, action)) {
            MMap<K, V> mMap = new MMap<>();
            action.execute(mMap.map(new LinkedHashMap<>()));
            return mMap.map();
        }
        return null;
    }

    public static String timeToString(long time) {
        if (time == 0) {
            return "零";
        }
        StringBuilder builder = new StringBuilder();
        if (time < 0) {
            time = -time;
            builder.append("负");
        }
        int hs = (int) (time % 1000);
        int s = (int) ((time / 1000) % 60);
        int m = (int) ((time / (1000 * 60)) % 60);
        int h = (int) ((time / (1000 * 60 * 60)) % 24);
        int d = (int) (time / (1000 * 60 * 60 * 24));
        if (d > 0) {
            builder.append(d).append("天");
        }
        if (h > 0) {
            builder.append(h).append("小时");
        }
        if (m > 0) {
            builder.append(m).append("分钟");
        }
        if (s > 0) {
            builder.append(s).append("秒");
        }
        if (hs > 0) {
            builder.append(hs).append("毫秒");
        }
        return builder.toString();
    }

}