package com.leo.core.viewParse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.core.R;
import com.leo.core.api.api.VsApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IWhatAction;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static android.view.View.OVER_SCROLL_ALWAYS;
import static android.view.View.OVER_SCROLL_IF_CONTENT_SCROLLS;
import static android.view.View.OVER_SCROLL_NEVER;

public class ViewFactory extends VsApi<ViewFactory> {

    @SuppressLint("StaticFieldLeak")
    private static ViewFactory instance;

    public static ViewFactory getInstance() {
        if (instance == null) {
            synchronized (ViewFactory.class) {
                if (instance == null) {
                    instance = new ViewFactory();
                }
            }
        }
        return instance;
    }

    private final static String ID = "id";
    private final static String WELL = "#";
    private final static String AT = "@%s/";
    private final static String AT_ADD = "@+%s/";
    private final static String STYLE = "style";
    private final static String COLOR = "color";
    private final static String DIMEN = "dimen";
    private final static String LAYOUT = "layout";
    private final static String MIPMAP = "mipmap";
    private final static String STRING = "string";
    private final static String DRAWABLE = "drawable";
    private final static List<String> KEY_FILTER = TextUtils.getListData("cname", "xmlns:",
            "tools:", "android:layout_width", "android:layout_height", "style", "xml_priority");
    private final static Map<String, List<Class<? extends View>>> ALLOW = TextUtils.map(m -> m
            .put("android:orientation", TextUtils.getListData(LinearLayout.class)));
    private final static List<String> RULE = TextUtils.getListData("android:layout_height",
            "android:layout_width", "cname", "layout", "style", "xml_priority");

    private static boolean XML_PRIORITY;

    private CoreControllerApi api;
    private Map<String, Set<String>> noParseMap;
    private Map<String, List<ViewParse>> parseMap;
    private Map<String, List<ViewParse>> parseLazyMap;

    private <A extends View, B> void addLazy(Class<A> viewClz, String key,
                                             Class<B> valueClz, IViewParseAction<A, B> action) {
        if (TextUtils.check(viewClz, key, valueClz, action)) {
            if (TextUtils.check("android")) {
                key = String.format("%s:%s", "android", key);
            }
            if (parseLazyMap.get(key) == null) {
                parseLazyMap.put(key, new ArrayList<>());
            }
            parseLazyMap.get(key).add(new ViewParse(viewClz, valueClz, action));
        }
    }

    private <A extends View, B> void add(Class<A> viewClz, String namespace, String key,
                                         Class<B> valueClz, IViewParseAction<A, B> action) {
        if (TextUtils.check(viewClz, key, valueClz, action)) {
            if (TextUtils.check(namespace)) {
                key = String.format("%s:%s", namespace, key);
            }
            if (parseMap.get(key) == null) {
                parseMap.put(key, new ArrayList<>());
            }
            parseMap.get(key).add(new ViewParse(viewClz, valueClz, action));
        }
    }

    private <A extends View> void addLazy(Class<A> viewClz, String key, IViewParseAction<A, String> action) {
        addLazy(viewClz, key, String.class, action);
    }

    private <A extends View> void add(Class<A> viewClz, String key, IViewParseAction<A, String> action) {
        add(viewClz, key, String.class, action);
    }

    private <A extends View, B> void add(Class<A> viewClz, String key, Class<B> valueClz, IViewParseAction<A, B> action) {
        add(viewClz, "android", key, valueClz, action);
    }

    private <A extends View, B> void addChilds(Class<A> viewClz, Class<B> valueClz, IViewParseAction<A, B> action) {
        add(viewClz, null, "childs", valueClz, action);
    }

    private void execute(View view, String key, Object value, final Map<String, Object> m) {
        if (TextUtils.check(view, key)) {
            List<ViewParse> data = parseMap.get(key);
            if (TextUtils.check(data)) {
                for (ViewParse parse : data) {
                    if (parse != null && parse.execute(view, value, m)) {
                        return;
                    }
                }
            }
            if (!parseLazyMap.keySet().contains(key) && !RULE.contains(key)) {
                addNoParseMap(view, key);
            }
        }
    }

    private void executeLazy(View view, String key, Object value, final Map<String, Object> m) {
        if (TextUtils.check(view, key)) {
            List<ViewParse> data = parseLazyMap.get(key);
            if (TextUtils.check(data)) {
                for (ViewParse parse : data) {
                    if (parse != null && parse.execute(view, value, m)) {
                        return;
                    }
                }
            }
            if (!parseMap.keySet().contains(key) && !RULE.contains(key)) {
                addNoParseMap(view, key);
            }
        }
    }

    private void addNoParseMap(View view, String key) {
        if (TextUtils.check(view, key)) {
            String clzName = view.getClass().getName();
            if (noParseMap.get(clzName) == null) {
                noParseMap.put(clzName, new LinkedHashSet<>());
            }
            if (!noParseMap.get(clzName).contains(key)) {
                noParseMap.get(clzName).add(key);
            }
        }
    }

    private void addNoParseMap(View view, String name, String key) {
        if (TextUtils.check(view, name, key)) {
            addNoParseMap(view, String.format("%s %s", name, key));
        }
    }

    private Class getViewClz(@NonNull String name) {
        Class clz = ObjectUtil.getClass(name);
        if (clz == null && !name.contains(".")) {
            clz = ObjectUtil.getClass(String.format("android.widget.%s", name));
            if (clz == null) {
                clz = ObjectUtil.getClass(String.format("android.view.%s", name));
            }
        }
        return clz;
    }

    private void initStyle(View v, String style) {
        if (TextUtils.check(v, style) && style.startsWith(String.format(AT, STYLE))) {
            int resId = getResources().getIdentifier(
                    style.substring(String.format(AT, STYLE).length(), style.length()),
                    STYLE, getPackageName());
            if (resId != 0) {
                StyleFactory.getInstance().onStyle(v, resId);
            }
        }
    }

    private String getText(String text) {
        if (TextUtils.check(text) && text.startsWith(String.format(AT, STRING))) {
            int resId = getResources().getIdentifier(
                    text.substring(String.format(AT, STRING).length(), text.length()),
                    STRING, getPackageName());
            if (resId != 0) {
                return ResUtil.getString(resId);
            }
        }
        return text;
    }

    private Drawable getDrawable(int resId) {
        Drawable drawable = ResUtil.getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    private float getPx(String text) {
        if (TextUtils.check(text)) {
            if (TextUtils.equals(text, "match_root")) {
                return -1;
            } else if (TextUtils.equals(text, "wrap_content")) {
                return -2;
            } else if (text.startsWith(String.format(AT, DIMEN))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT, DIMEN).length(), text.length()),
                        DIMEN, getPackageName());
                if (resId != 0) {
                    return ResUtil.getDimen(resId);
                }
            } else if (text.endsWith("px")) {
                float ts = JavaTypeUtil.getfloat(text.substring(0,
                        text.length() - "px".length()), -1);
                if (ts >= 0) {
                    return ts + 0.5f;
                }
            } else if (text.endsWith("dp")) {
                float ts = DisneyUtil.dip2px(JavaTypeUtil.getfloat(text.substring(0,
                        text.length() - "dp".length()), -1));
                if (ts >= 0) {
                    return ts;
                }
            } else if (text.endsWith("sp")) {
                float ts = DisneyUtil.sp2px(JavaTypeUtil.getfloat(text.substring(0,
                        text.length() - "sp".length()), -1));
                if (ts >= 0) {
                    return ts;
                }
            }
        }
        return -1;
    }

    private int getPxInt(String text) {
        return (int) getPx(text);
    }

    private void initPxInt(String text, IWhatAction action) {
        if (TextUtils.check(text, action)) {
            int px = getPxInt(text);
            if (px >= 0) {
                action.execute(px);
            } else {
                LogUtil.ee(this, String.format("%s 未解析", text));
            }
        }
    }

    private void initLayoutParams(@NonNull View view, IObjAction<ViewGroup.LayoutParams> action) {
        if (action != null) {
            ViewGroup.LayoutParams layoutParams
                    = view.getLayoutParams();
            if(null != layoutParams){
                action.execute(layoutParams);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    private void initResId(String text, IWhatAction action) {
        if (TextUtils.check(text, action)) {
            if (text.startsWith(String.format(AT, ID))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT, ID).length(), text.length()),
                        ID, getPackageName());
                if (resId != 0) {
                    action.execute(resId);
                }
            } else if (text.startsWith(String.format(AT_ADD, ID))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT_ADD, ID).length(), text.length()),
                        ID, getPackageName());
                if (resId != 0) {
                    action.execute(resId);
                } else {
                    LogUtil.ee(this, String.format("%s数据不存在", text));
                }
            } else if (text.startsWith(String.format(AT, COLOR))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT, COLOR).length(), text.length()),
                        COLOR, getPackageName());
                if (resId != 0) {
                    action.execute(ResUtil.getColor(resId));
                }
            } else if (text.startsWith(String.format(AT, MIPMAP))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT, MIPMAP).length(), text.length()),
                        MIPMAP, getPackageName());
                if (resId != 0) {
                    action.execute(resId);
                }
            } else if (text.startsWith(String.format(AT, DRAWABLE))) {
                int resId = getResources().getIdentifier(
                        text.substring(String.format(AT, DRAWABLE).length(), text.length()),
                        DRAWABLE, getPackageName());
                if (resId != 0) {
                    action.execute(resId);
                }
            } else if (text.startsWith(WELL)) {
                try {
                    action.execute(Color.parseColor(text));
                } catch (IllegalArgumentException e) {
                    LogUtil.ee(this, String.format("%s是不识别的颜色", text));
                }
            } else if (TextUtils.equals(text, "@null")) {
                action.execute(android.R.color.transparent);
            } else {
                LogUtil.ee(this, String.format("%s不识别", text));
            }
        }
    }

    @SuppressLint("RtlHardcoded")
    private int getGravity(String value) {
        if (TextUtils.check(value)) {
            switch (value) {
                case "center":
                    return Gravity.CENTER;
                case "bottom":
                    return Gravity.BOTTOM;
                case "center_horizontal":
                    return Gravity.CENTER_HORIZONTAL;
                case "center_vertical":
                    return Gravity.CENTER_VERTICAL;
                case "clip_horizontal":
                    return Gravity.CLIP_HORIZONTAL;
                case "clip_vertical":
                    return Gravity.CLIP_VERTICAL;
                case "end":
                    return Gravity.END;
                case "fill":
                    return Gravity.FILL;
                case "fill_horizontal":
                    return Gravity.FILL_HORIZONTAL;
                case "fill_vertical":
                    return Gravity.FILL_VERTICAL;
                case "left":
                    return Gravity.LEFT;
                case "right":
                    return Gravity.RIGHT;
                case "start":
                    return Gravity.START;
                case "top":
                    return Gravity.TOP;
            }
        }
        return -1;
    }

    private void initGravity(View view, String value, IWhatAction action) {
        if (TextUtils.check(view, value, action)) {
            int g = 0;
            for (String text : value.split(ResUtil.getString(R.string.shu))) {
                if (TextUtils.check(text)) {
                    int gt = getGravity(text);
                    if (gt > 0) {
                        g = g == 0 ? gt : g | gt;
                    } else {
                        addNoParseMap(view, "gravity", text);
                    }
                }
            }
            if (g > 0) {
                action.execute(g);
            }
        }
    }

    private void onVisible(View view, String visible) {
        if (TextUtils.check(view, visible)) {
            switch (visible) {
                default:
                case "visible":
                    view.setVisibility(View.VISIBLE);
                    break;
                case "invisible":
                    view.setVisibility(View.INVISIBLE);
                    break;
                case "gone":
                    view.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @NonNull
    private ViewGroup.LayoutParams lp(@NonNull Map<String, Object> map, final ViewGroup vg) {
        int width = getPxInt(gs(map.get("android:layout_width")));
        int height = getPxInt(gs(map.get("android:layout_height")));
        if(null != vg){
            Class clz = ObjectUtil.getClass(vg.getClass().getName() + "$LayoutParams");
            if (clz != null) {
                Object obj = ObjectUtil.getObject(clz, new Class[]{int.class, int.class},
                        new Object[]{width, height});
                if (obj instanceof ViewGroup.LayoutParams) {
                    return (ViewGroup.LayoutParams) obj;
                }
            }
        }
        return new ViewGroup.LayoutParams(width, height);
    }

    private String gs(Object obj) {
        return gs(obj, String.class);
    }

    private <A> A gs(Object obj, Class<A> clz) {
        if (clz != null && clz.isInstance(obj)) {
            return (A) obj;
        }
        return null;
    }

    public ViewFactory set(ViewFactory factory) {
        ViewFactory.instance = factory;
        return getInstance();
    }

    public ViewFactory set(boolean xmlPriority) {
        ViewFactory.XML_PRIORITY = xmlPriority;
        return getInstance();
    }

    public void init(CoreControllerApi api) {
        this.api = api;
        init();
        initAdd();
    }

    protected void init() {
        this.parseMap = new LinkedHashMap<>();
        this.parseLazyMap = new LinkedHashMap<>();
        this.noParseMap = new LinkedHashMap<>();
    }

    public CoreControllerApi getApi() {
        return api;
    }

    private Context getApplicationContext() {
        return getApi().getContext();
    }

    private String getPackageName() {
        return getApplicationContext().getPackageName();
    }

    private Resources getResources() {
        return getApplicationContext().getResources();
    }

    public View createJsonView(Context context, Map<String, Object> map, ViewGroup root,
                               boolean attachToRoot) {
        if (TextUtils.check(map, context)) {
            this.noParseMap.clear();
            View view = parse(context, map, root, attachToRoot);
            int noParseCount = TextUtils.count(noParseMap);
            if (noParseCount > 0) {
                AtomicInteger count = new AtomicInteger();
                execute(noParseMap, (key, value) -> count.addAndGet(TextUtils.count(value)));
                LogUtil.ee(this, m -> m
                        .put("未解析类数", noParseCount)
                        .put("未解析总数", count.get())
                        .put("未解析数据", noParseMap));
            }
            return view;
        }
        return null;
    }

    private View parse(Context context, Map<String, Object> map, ViewGroup root) {
        return parse(context, map, root, root != null);
    }

    private View parse(Context context, Map<String, Object> map, ViewGroup root, boolean attachToRoot) {
        if (TextUtils.check(map)) {
            String name = gs(map.get("cname"));
            if (TextUtils.check(name)) {
                if (TextUtils.equals("include", name)) {
                    return parseInclude(context, map, root, attachToRoot);
                } else {
                    return parseView(context, map, name, root, attachToRoot);
                }
            }
        }
        return null;
    }

    private View parseInclude(Context context, @NonNull Map<String, Object> map, ViewGroup root,
                              boolean attachToRoot) {
        String layout = gs(map.get(LAYOUT));
        if (TextUtils.check(layout) && layout.startsWith(String.format(AT, LAYOUT))) {
            String name = layout.substring(String.format(AT, MIPMAP).length(), layout.length());
            boolean xmlPriority = XML_PRIORITY && TextUtils.equals(gs(map.get("xml_priority")), "true");
            String jsonName = String.format("%s.json", name);
            View view = null;
            if (xmlPriority) {
                int resId = getResources().getIdentifier(name, LAYOUT, getPackageName());
                if (resId != 0) {
                    view = LayoutInflater.from(context).inflate(resId, root, attachToRoot);
                }
                if (view == null) {
                    getApi().createAssetsJsonView(jsonName, root, attachToRoot);
                }
            } else {
                view = getApi().createAssetsJsonView(jsonName, root, attachToRoot);
                if (view == null) {
                    int resId = getResources().getIdentifier(name, LAYOUT, getPackageName());
                    if (resId != 0) {
                        view = LayoutInflater.from(context).inflate(resId, root, attachToRoot);
                    }
                }
            }
            if (view != null) {
                View finalView = view;
                if (map.containsKey("android:layout_width") && map.containsKey("android:layout_height")) {
                    finalView.setLayoutParams(lp(map, root));
                }
                initStyle(finalView, gs(map.get("style")));
                initResId(gs(map.get("android:id")), what -> {
                    if (what != View.NO_ID) {
                        finalView.setId(what);
                    }
                });
                float weight = JavaTypeUtil.getfloat(gs(map.get("android:layout_weight")), -1.0f);
                initLayoutParams(view, layoutParams -> {
                    if (layoutParams instanceof LinearLayout.LayoutParams) {
                        ((LinearLayout.LayoutParams) layoutParams).weight = weight < 0 ? 0 : weight;
                    }
                });
                onVisible(finalView, gs(map.get("android:visible")));
                return finalView;
            }
        }
        return null;
    }

    private View parseView(Context context, @NonNull Map<String, Object> map, @NonNull String name,
                           ViewGroup root, boolean attachToRoot) {
        Class viewClz = getViewClz(name);
        Object obj = ObjectUtil.getObject(viewClz, Context.class, context);
        if (obj instanceof View) {
            View view = (View) obj;
            ViewGroup.LayoutParams lp = lp(map, root);
            if (lp != null) {
                if (root == null) {
                    view.setLayoutParams(lp);
                } else if (attachToRoot) {
                    root.addView(view, lp);
                }
            }
            initStyle(view, gs(map.get("style")));
            execute(map, (key, value) -> {
                for (String ft : KEY_FILTER) {
                    if (ft != null && key.startsWith(ft)) {
                        return;
                    }
                }
                List<Class<? extends View>> data = ALLOW.get(key);
                if (TextUtils.check(data)) {
                    for (Class<? extends View> clz : data) {
                        if (clz != null && clz.isInstance(view)) {
                            execute(view, key, value, map);
                        }
                    }
                } else {
                    execute(view, key, value, map);
                }
            });
            return view;
        }
        LogUtil.ee(this, String.format("%s 无法识别", name));
        return null;
    }

    //自定义级功能

    private void initAdd() {
        initAddChilds();
        initAddView();
        initAddLinearLayout();
        initAddRelativeLayout();
        initAddFrameLayout();
        initAddTextView();
        initAddImageView();
        initAddProgressBar();
    }

    private void initAddChilds() {
        addChilds(ViewGroup.class, List.class, (view, value, map) -> {
            Map<View, Map<String, Object>> m = new LinkedHashMap<>();
            execute(value, item -> {
                Map<String, Object> childMap = TextUtils.toJSONMap(String.class, Object.class, item);
                if (TextUtils.check(childMap)) {
                    m.put(parse(view.getContext(), childMap, view), childMap);
                }
            });
            execute(m, (k, v) -> execute(v, (kk, vv) -> executeLazy(k, kk, vv, v)));
        });
    }

    private void initAddView() {
        add(View.class, "id", (view, value, map) -> initResId(value, view::setId));
        add(View.class, "tag", (view, value, map) -> view.setTag(value));
        add(View.class, "visibility", (view, value, map) -> onVisible(view, value));
        add(View.class, "clickable", boolean.class, (view, value, map)
                -> view.setClickable(value));
        add(View.class, "focusable", boolean.class, (view, value, map)
                -> view.setFocusable(value));
        add(View.class, "focusableInTouchMode", boolean.class, (view, value, map)
                -> view.setFocusableInTouchMode(value));
        add(View.class, "overScrollMode", (view, value, map) -> {
            switch (value) {
                case "always":
                    view.setOverScrollMode(OVER_SCROLL_ALWAYS);
                    break;
                case "ifContentScrolls":
                    view.setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
                    break;
                case "never":
                    view.setOverScrollMode(OVER_SCROLL_NEVER);
                    break;
            }
        });
        add(View.class, "scrollbars", (view, value, map) -> {
            switch (value) {
                case "none":
                    view.setVerticalScrollBarEnabled(false);
                    view.setHorizontalScrollBarEnabled(false);
                    break;
                case "horizontal":
                    view.setHorizontalScrollBarEnabled(true);
                    break;
                case "vertical":
                    view.setVerticalScrollBarEnabled(true);
                    break;
            }
        });
        add(View.class, "padding", (view, value, map) -> initPxInt(value, what
                -> view.setPadding(what, what, what, what)));
        add(View.class, "paddingTop", (view, value, map) -> initPxInt(value, what
                -> view.setPadding(view.getPaddingLeft(), what, view.getPaddingRight(),
                view.getPaddingBottom())));
        add(View.class, "paddingBottom", (view, value, map) -> initPxInt(value, what
                -> view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), what)));
        add(View.class, "paddingLeft", (view, value, map) -> initPxInt(value, what
                -> view.setPadding(what, view.getPaddingTop(), view.getPaddingRight(),
                view.getPaddingBottom())));
        add(View.class, "paddingRight", (view, value, map) -> initPxInt(value, what
                -> view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), what,
                view.getPaddingBottom())));
        add(View.class, "paddingStart", (view, value, map) -> initPxInt(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setPaddingRelative(what, view.getPaddingTop(),
                        view.getPaddingEnd(), view.getPaddingBottom());
            }
        }));
        add(View.class, "paddingEnd", (view, value, map) -> initPxInt(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(),
                        what, view.getPaddingBottom());
            }
        }));
        add(View.class, "background", (view, value, map) -> initResId(value, resId -> {
            if (value.contains(COLOR) || value.startsWith(WELL)) {
                view.setBackgroundColor(resId);
            } else if (value.contains(DRAWABLE) || value.contains(MIPMAP)) {
                view.setBackgroundResource(resId);
            } else if (TextUtils.equals(value, "@null")) {
                view.setBackgroundResource(resId);
            } else {
                addNoParseMap(view, "background", value);
            }
        }));
        addLazy(View.class, "layout_toLeftOf", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.LEFT_OF, resId));
            } else {
                addNoParseMap(view, "layout_toLeftOf", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_toRightOf", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.RIGHT_OF, resId));
            } else {
                addNoParseMap(view, "layout_toRightOf", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_above", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ABOVE, resId));
            } else {
                addNoParseMap(view, "layout_above", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_below", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.BELOW, resId));
            } else {
                addNoParseMap(view, "layout_below", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_alignBaseline", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_BASELINE, resId));
            } else {
                addNoParseMap(view, "layout_alignBaseline", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_alignLeft", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_LEFT, resId));
            } else {
                addNoParseMap(view, "layout_alignLeft", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_alignTop", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_TOP, resId));
            } else {
                addNoParseMap(view, "layout_alignTop", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_alignRight", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_RIGHT, resId));
            } else {
                addNoParseMap(view, "layout_alignRight", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_alignBottom", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_BOTTOM, resId));
            } else {
                addNoParseMap(view, "layout_alignBottom", TextUtils.getClassName(layoutParams));
            }
        }));
        addLazy(View.class, "layout_toStartOf", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_BOTTOM, resId));
                } else {
                    addNoParseMap(view, "layout_toStartOf", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        addLazy(View.class, "layout_toEndOf", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.END_OF, resId));
                } else {
                    addNoParseMap(view, "layout_toEndOf", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        addLazy(View.class, "layout_alignStart", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_START, resId));
                } else {
                    addNoParseMap(view, "layout_alignStart", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        addLazy(View.class, "layout_alignEnd", (view, value, map) -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_END, resId));
                } else {
                    addNoParseMap(view, "layout_alignEnd", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        add(View.class, "layout_alignWithParentIfMissing", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).alignWithParent = value;
            } else {
                addNoParseMap(view, "layout_alignWithParentIfMissing", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_margin", (view, value, map) -> initPxInt(value, what
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(what, what, what, what);
            } else {
                addNoParseMap(view, "layout_marginLeft", TextUtils.getClassName(layoutParams));
            }
        })));
        add(View.class, "layout_marginLeft", (view, value, map) -> initPxInt(value, what
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = what;
            } else {
                addNoParseMap(view, "layout_marginLeft", TextUtils.getClassName(layoutParams));
            }
        })));
        add(View.class, "layout_marginRight", (view, value, map) -> initPxInt(value, what
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = what;
            } else {
                addNoParseMap(view, "layout_marginRight", TextUtils.getClassName(layoutParams));
            }
        })));
        add(View.class, "layout_marginStart", (view, value, map) -> initLayoutParams(view, layoutParams
                -> initPxInt(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(what);
                } else {
                    addNoParseMap(view, "layout_marginStart", TextUtils.getClassName(layoutParams));
                }
            }
        })));
        add(View.class, "layout_marginEnd", (view, value, map) -> initLayoutParams(view, layoutParams
                -> initPxInt(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(what);
                } else {
                    addNoParseMap(view, "layout_marginEnd", TextUtils.getClassName(layoutParams));
                }
            }
        })));
        add(View.class, "layout_marginTop", (view, value, map) -> initLayoutParams(view, layoutParams
                -> initPxInt(value, what -> {
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = what;
            } else {
                addNoParseMap(view, "layout_marginTop", TextUtils.getClassName(layoutParams));
            }
        })));
        add(View.class, "layout_marginBottom", (view, value, map) -> initLayoutParams(view, layoutParams
                -> initPxInt(value, what -> {
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = what;
            } else {
                addNoParseMap(view, "layout_marginBottom", TextUtils.getClassName(layoutParams));
            }
        })));
        add(View.class, "layout_alignParentLeft", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_alignParentLeft", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_alignParentTop", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_alignParentTop", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_alignParentRight", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_alignParentRight", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_alignParentBottom", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_alignParentBottom", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_alignParentStart", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    lp.addRule(RelativeLayout.ALIGN_PARENT_START, value ? RelativeLayout.TRUE : 0);
                } else {
                    addNoParseMap(view, "layout_alignParentStart", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        add(View.class, "layout_alignParentEnd", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    lp.addRule(RelativeLayout.ALIGN_PARENT_END, value ? RelativeLayout.TRUE : 0);
                } else {
                    addNoParseMap(view, "layout_alignParentEnd", TextUtils.getClassName(layoutParams));
                }
            }
        }));
        add(View.class, "layout_centerInParent", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_centerInParent", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_centerHorizontal", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_centerHorizontal", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_centerVertical", boolean.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_VERTICAL, value ? RelativeLayout.TRUE : 0);
            } else {
                addNoParseMap(view, "layout_centerVertical", TextUtils.getClassName(layoutParams));
            }
        }));
        add(View.class, "layout_weight", float.class, (view, value, map)
                -> initLayoutParams(view, layoutParams -> {
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).weight = value < 0 ? 0 : value;
            }
        }));
        add(View.class, "layout_gravity", (view, value, map) -> initLayoutParams(view, layoutParams
                -> initGravity(view, value, what -> {
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = what;
            } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = what;
            } else {
                addNoParseMap(view, "layout_gravity", TextUtils.getClassName(layoutParams));
            }
        })));
    }

    private void initAddLinearLayout() {
        add(LinearLayout.class, "gravity", (view, value, map)
                -> initGravity(view, value, view::setGravity));
        add(LinearLayout.class, "orientation", (view, value, map) -> {
            if (TextUtils.equals("horizontal", value)) {
                view.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                view.setOrientation(LinearLayout.VERTICAL);
            }
        });
        add(LinearLayout.class, "weightSum", float.class, (view, value, map)
                -> view.setWeightSum(value <= 0 ? -1.0f : value));
    }

    private void initAddRelativeLayout() {
        add(RelativeLayout.class, "gravity", (view, value, map)
                -> initGravity(view, value, view::setGravity));
    }

    private void initAddFrameLayout() {
    }

    private void initAddTextView() {
        add(TextView.class, "text", (view, value, map) -> getApi().setText(view, getText(value)));
        add(TextView.class, "maxLines", int.class, (view, value, map) -> view.setMaxLines(value));
        add(TextView.class, "textColor", (view, value, map) -> initResId(value, view::setTextColor));
        add(TextView.class, "textSize", (view, value, map) -> initPxInt(value, what
                -> view.setTextSize(TypedValue.COMPLEX_UNIT_PX, what)));
        add(TextView.class, "gravity", (view, value, map)
                -> initGravity(view, value, view::setGravity));
        add(TextView.class, "ellipsize", (view, value, map) -> {
            switch (value) {
                case "start":
                    view.setEllipsize(android.text.TextUtils.TruncateAt.START);
                    break;
                case "middle":
                    view.setEllipsize(android.text.TextUtils.TruncateAt.MIDDLE);
                    break;
                case "end":
                    view.setEllipsize(android.text.TextUtils.TruncateAt.END);
                    break;
                case "marquee":
                    view.setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
                    break;
                case "none":
                    view.setEllipsize(null);
                    break;
                default:
                    addNoParseMap(view, "ellipsize", value);
                    break;
            }
        });
        add(TextView.class, "includeFontPadding", boolean.class, (view, value, map)
                -> view.setIncludeFontPadding(value));
        add(TextView.class, "lines", int.class, (view, value, map)
                -> view.setLines(value));
        add(TextView.class, "hint", (view, value, map) -> view.setHint(getText(value)));
        add(TextView.class, "inputType", (view, value, map) -> setInputType(view, value));
        add(TextView.class, "textColorHint", (view, value, map)
                -> initResId(value, view::setHintTextColor));
        add(TextView.class, "drawablePadding", (view, value, map)
                -> initPxInt(value, view::setCompoundDrawablePadding));
        add(TextView.class, "drawableLeft", (view, value, map) -> initResId(value, what
                -> view.setCompoundDrawablesWithIntrinsicBounds(getDrawable(what), null, null, null)));
        add(TextView.class, "drawableTop", (view, value, map) -> initResId(value, what
                -> view.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(what), null, null)));
        add(TextView.class, "drawableRight", (view, value, map) -> initResId(value, what
                -> view.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(what), null)));
        add(TextView.class, "drawableBottom", (view, value, map) -> initResId(value, what
                -> view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getDrawable(what))));
        add(TextView.class, "drawableStart", (view, value, map) -> initResId(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(what), null, null, null);
            }
        }));
        add(TextView.class, "drawableEnd", (view, value, map) -> initResId(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getDrawable(what), null);
            }
        }));
        add(TextView.class, "imeOptions", (view, value, map) -> setImeOptions(view, value));
        add(TextView.class, "minLines", int.class, (view, value, map) -> view.setMinLines(value));
        add(TextView.class, "maxLines", int.class, (view, value, map) -> view.setMaxLines(value));
        add(TextView.class, "singleLine", (view, value, map) -> view.setSingleLine());
    }

    private void initAddImageView() {
        add(ImageView.class, "src", (view, value, map) -> initResId(value, resId
                -> getApi().setImage(view, resId)));
        add(ImageView.class, "scaleType", (view, value, map) -> {
            switch (value) {
                case "matrix":
                    view.setScaleType(ImageView.ScaleType.MATRIX);
                    break;
                case "fitXY":
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case "fitStart":
                    view.setScaleType(ImageView.ScaleType.FIT_START);
                    break;
                case "fitCenter":
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case "fitEnd":
                    view.setScaleType(ImageView.ScaleType.FIT_END);
                    break;
                case "center":
                    view.setScaleType(ImageView.ScaleType.CENTER);
                    break;
                case "centerCrop":
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
                case "centerInside":
                    view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;
            }
        });
    }

    private void initAddProgressBar() {
        add(ProgressBar.class, "max", int.class, (view, value, map) -> view.setMax(value));
        add(ProgressBar.class, "min", int.class, (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                view.setMin(value);
            }
        });
        add(ProgressBar.class, "progress", int.class, (view, value, map) -> view.setProgress(value));
        add(ProgressBar.class, "progressDrawable", (view, value, map)
                -> initResId(value, what -> view.setProgressDrawable(ResUtil.getDrawable(what))));
    }

    private void setImeOptions(@NonNull TextView view, @NonNull String value){
        switch (value){
            case "normal":
                view.setImeOptions(0x00000000);
                break;
            case "actionUnspecified":
                view.setImeOptions(0x00000000);
                break;
            case "actionNone":
                view.setImeOptions(0x00000001);
                break;
            case "actionGo":
                view.setImeOptions(0x00000002);
                break;
            case "actionSearch":
                view.setImeOptions(0x00000003);
                break;
            case "actionSend":
                view.setImeOptions(0x00000004);
                break;
            case "actionNext":
                view.setImeOptions(0x00000005);
                break;
            case "actionDone":
                view.setImeOptions(0x00000006);
                break;
            case "actionPrevious":
                view.setImeOptions(0x00000007);
                break;
            case "flagNoPersonalizedLearning":
                view.setImeOptions(0x1000000);
                break;
            case "flagNoFullscreen":
                view.setImeOptions(0x2000000);
                break;
            case "flagNavigatePrevious":
                view.setImeOptions(0x4000000);
                break;
            case "flagNavigateNext":
                view.setImeOptions(0x8000000);
                break;
            case "flagNoExtractUi":
                view.setImeOptions(0x10000000);
                break;
            case "flagNoAccessoryAction":
                view.setImeOptions(0x20000000);
                break;
            case "flagNoEnterAction":
                view.setImeOptions(0x40000000);
                break;
            case "flagForceAscii":
                view.setImeOptions(0x80000000);
                break;
        }
    }

    private void setInputType(@NonNull TextView view, @NonNull String value) {
        switch (value) {
            case "none":
                view.setInputType(0x00000000);
                break;
            case "text":
                view.setInputType(0x00000001);
                break;
            case "textCapCharacters":
                view.setInputType(0x00001001);
                break;
            case "textCapWords":
                view.setInputType(0x00002001);
                break;
            case "textCapSentences":
                view.setInputType(0x00004001);
                break;
            case "textAutoCorrect":
                view.setInputType(0x00008001);
                break;
            case "textAutoComplete":
                view.setInputType(0x00010001);
                break;
            case "textMultiLine":
                view.setInputType(0x00020001);
                break;
            case "textImeMultiLine":
                view.setInputType(0x00040001);
                break;
            case "textNoSuggestions":
                view.setInputType(0x00080001);
                break;
            case "textUri":
                view.setInputType(0x00000011);
                break;
            case "textEmailAddress":
                view.setInputType(0x00000021);
                break;
            case "textEmailSubject":
                view.setInputType(0x00000031);
                break;
            case "textShortMessage":
                view.setInputType(0x00000041);
                break;
            case "textLongMessage":
                view.setInputType(0x00000051);
                break;
            case "textPersonName":
                view.setInputType(0x00000061);
                break;
            case "textPostalAddress":
                view.setInputType(0x00000071);
                break;
            case "textPassword":
                view.setInputType(0x00000081);
                break;
            case "textVisiblePassword":
                view.setInputType(0x00000091);
                break;
            case "textWebEditText":
                view.setInputType(0x000000a1);
                break;
            case "textFilter":
                view.setInputType(0x000000b1);
                break;
            case "textPhonetic":
                view.setInputType(0x000000c1);
                break;
            case "textWebEmailAddress":
                view.setInputType(0x000000d1);
                break;
            case "textWebPassword":
                view.setInputType(0x000000e1);
                break;
            case "number":
                view.setInputType(0x00000002);
                break;
            case "numberSigned":
                view.setInputType(0x00001002);
                break;
            case "numberDecimal":
                view.setInputType(0x00002002);
                break;
            case "numberPassword":
                view.setInputType(0x00000012);
                break;
            case "phone":
                view.setInputType(0x00000003);
                break;
            case "datetime":
                view.setInputType(0x00000004);
                break;
            case "date":
                view.setInputType(0x00000014);
                break;
            case "time":
                view.setInputType(0x00000024);
                break;
        }
    }

}