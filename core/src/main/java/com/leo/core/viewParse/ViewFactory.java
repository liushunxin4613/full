package com.leo.core.viewParse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leo.core.R;
import com.leo.core.api.api.VsApi;
import com.leo.core.api.main.CoreControllerApi;
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
    private final static String COLOR = "color";
    private final static String MIPMAP = "mipmap";
    private final static String LAYOUT = "layout";
    private final static String DRAWABLE = "drawable";
    private final static Map<String, String> VIEW_NAME_FILTER = TextUtils.map(m
            -> m.put("View", View.class.getName()));
    private final static List<String> KEY_FILTER = TextUtils.getListData("name", "xmlns:",
            "tools:", "android:layout_width", "android:layout_height");

    private static boolean XML_PRIORITY;

    private Context context;
    private CoreControllerApi api;
    private Map<String, Set<String>> noParseMap;
    private Map<String, List<ViewParse>> parseMap;

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
            addNoParseMap(view, key);
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

    private float getPx(String text) {
        if (TextUtils.check(text)) {
            if (TextUtils.equals(text, "match_root")) {
                return -1;
            } else if (TextUtils.equals(text, "wrap_content")) {
                return -2;
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
                    int resId = Color.parseColor(text);
                    if (resId != 0) {
                        action.execute(resId);
                    }
                } catch (IllegalArgumentException e) {
                    LogUtil.ee(this, String.format("%s是不识别的颜色", text));
                }
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
    private ViewGroup.LayoutParams lp(@NonNull Map<String, Object> map,
                                      @NonNull final View view, @NonNull String name) {
        int width = getPxInt(gs(map.get("android:layout_width")));
        int height = getPxInt(gs(map.get("android:layout_height")));
        if (view instanceof ViewGroup) {
            return (ViewGroup.LayoutParams) ObjectUtil.getObject(name + "$LayoutParams",
                    new Class[]{int.class, int.class}, new Object[]{width, height});
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

    private Context getContext() {
        return context;
    }

    public View createJsonView(Map<String, Object> map, ViewGroup root, boolean attachToRoot, Context context) {
        if (TextUtils.check(map, context)) {
            this.context = context;
            this.noParseMap.clear();
            View view = parse(map, root, attachToRoot);
            this.context = null;
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

    private void parse(Map<String, Object> map, ViewGroup root) {
        parse(map, root, root != null);
    }

    private View parse(Map<String, Object> map, ViewGroup root, boolean attachToRoot) {
        if (TextUtils.check(map)) {
            String name = gs(map.get("name"));
            if (TextUtils.check(name)) {
                if (TextUtils.equals("include", name)) {
                    return parseInclude(map, root, attachToRoot);
                } else {
                    return parseView(map, name, root, attachToRoot);
                }
            }
        }
        return null;
    }

    private View parseInclude(@NonNull Map<String, Object> map, ViewGroup root, boolean attachToRoot) {
        String layout = gs(map.get(LAYOUT));
        if (TextUtils.check(layout) && layout.startsWith(String.format(AT, LAYOUT))) {
            String name = layout.substring(String.format(AT, MIPMAP).length(), layout.length());
            boolean xmlPriority = XML_PRIORITY && TextUtils.equals(gs(map.get("xml_priority")), "true");
            String jsonName = String.format("%s.json", name);
            View view = null;
            if (xmlPriority) {
                int resId = getResources().getIdentifier(name, LAYOUT, getPackageName());
                if (resId != 0) {
                    view = LayoutInflater.from(getContext()).inflate(resId, root, attachToRoot);
                }
                if (view == null) {
                    getApi().createAssetsJsonView(jsonName, root, attachToRoot);
                }
            } else {
                view = getApi().createAssetsJsonView(jsonName, root, attachToRoot);
                if (view == null) {
                    int resId = getResources().getIdentifier(name, LAYOUT, getPackageName());
                    if (resId != 0) {
                        view = LayoutInflater.from(getContext()).inflate(resId, root, attachToRoot);
                    }
                }
            }
            if (view != null) {
                View finalView = view;
                if (map.containsKey("android:layout_width") && map.containsKey("android:layout_height")) {
                    finalView.setLayoutParams(lp(map, finalView, name));
                }
                initResId(gs(map.get("android:id")), what -> {
                    if (what != View.NO_ID) {
                        finalView.setId(what);
                    }
                });
                onVisible(finalView, gs(map.get("android:visible")));
                return finalView;
            }
        }
        return null;
    }

    private View parseView(@NonNull Map<String, Object> map, @NonNull String name,
                           ViewGroup root, boolean attachToRoot) {
        String text = VIEW_NAME_FILTER.get(name);
        if (TextUtils.check(text)) {
            name = text;
        }
        if (!name.contains(".")) {
            name = "android.widget." + name;
        }
        Object obj = ObjectUtil.getObject(name, Context.class, getContext());
        if (obj instanceof View) {
            View view = (View) obj;
            ViewGroup.LayoutParams lp = lp(map, view, name);
            if (root == null) {
                view.setLayoutParams(lp);
            } else if (attachToRoot) {
                root.addView(view, lp);
            }
            execute(map, (key, value) -> {
                for (String ft : KEY_FILTER) {
                    if (ft != null && key.startsWith(ft)) {
                        return;
                    }
                }
                execute(view, key, value, map);
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
        initAddTextView();
        initAddImageView();
    }

    private void initAddChilds() {
        addChilds(ViewGroup.class, List.class, (view, value, map) -> execute(value, item -> {
            Map<String, Object> childMap = TextUtils.toJSONMap(String.class, Object.class, item);
            if (TextUtils.check(childMap)) {
                parse(childMap, view);
            }
        }));
    }

    private void initAddView() {
        add(View.class, "id", (view, value, map) -> initResId(value, view::setId));
        add(View.class, "tag", (view, value, map) -> view.setTag(value));
        add(View.class, "visibility", (view, value, map) -> onVisible(view, value));
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
            } else if (value.contains(DRAWABLE)) {
                view.setBackgroundResource(resId);
            }
        }));
        add(View.class, "layout_toLeftOf", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.LEFT_OF, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_toRightOf", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.RIGHT_OF, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_above", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ABOVE, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_below", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.BELOW, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignBaseline", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_BASELINE, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignLeft", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_LEFT, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignTop", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_TOP, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignRight", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_RIGHT, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignBottom", (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_BOTTOM, resId));
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_toStartOf", (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.START_OF, resId));
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_toEndOf", (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.END_OF, resId));
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_alignStart", (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_START, resId));
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_alignEnd", (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    initResId(value, resId -> lp.addRule(RelativeLayout.ALIGN_END, resId));
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_alignWithParentIfMissing", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).alignWithParent = value;
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_marginLeft", (view, value, map) -> initPxInt(value, what -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = what;
            }
            view.setLayoutParams(layoutParams);
        }));
        add(View.class, "layout_marginStart", (view, value, map) -> initPxInt(value, what -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(what);
                }
                view.setLayoutParams(layoutParams);
            }
        }));
        add(View.class, "layout_marginTop", (view, value, map) -> initPxInt(value, what -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = what;
            }
            view.setLayoutParams(layoutParams);
        }));
        add(View.class, "layout_alignParentLeft", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignParentTop", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignParentRight", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignParentBottom", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_alignParentStart", boolean.class, (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    lp.addRule(RelativeLayout.ALIGN_PARENT_START, value ? RelativeLayout.TRUE : 0);
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_alignParentEnd", boolean.class, (view, value, map) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;

                    lp.addRule(RelativeLayout.ALIGN_PARENT_END, value ? RelativeLayout.TRUE : 0);
                }
                view.setLayoutParams(layoutParams);
            }
        });
        add(View.class, "layout_centerInParent", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_centerHorizontal", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_centerVertical", boolean.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                lp.addRule(RelativeLayout.CENTER_VERTICAL, value ? RelativeLayout.TRUE : 0);
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_weight", float.class, (view, value, map) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).weight = value < 0 ? 0 : value;
            }
            view.setLayoutParams(layoutParams);
        });
        add(View.class, "layout_gravity", (view, value, map) -> initGravity(view, value, what -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = what;
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = what;
            }
            view.setLayoutParams(layoutParams);
        }));
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

    private void initAddTextView() {
        add(TextView.class, "text", (view, value, map) -> getApi().setText(view, value));
        add(TextView.class, "maxLines", int.class, (view, value, map) -> view.setMaxLines(value));
        add(TextView.class, "textColor", (view, value, map) -> initResId(value, view::setTextColor));
        add(TextView.class, "textSize", (view, value, map) -> {
            if (value.endsWith("px")) {
                float ts = JavaTypeUtil.getfloat(value.substring(0,
                        value.length() - "px".length()), -1);
                if (ts >= 0) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
                }
            } else if (value.endsWith("sp")) {
                float ts = JavaTypeUtil.getfloat(value.substring(0,
                        value.length() - "sp".length()), -1);
                if (ts >= 0) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, ts);
                }
            }
        });
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
    }

    private void initAddImageView() {
        add(ImageView.class, "src", (view, value, map) -> initResId(value, resId
                -> getApi().setImage(view, resId)));
    }

}
