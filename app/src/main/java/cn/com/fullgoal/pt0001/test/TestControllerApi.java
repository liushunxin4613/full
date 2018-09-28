package cn.com.fullgoal.pt0001.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.func.MainControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class TestControllerApi<T extends TestControllerApi, C> extends MainControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    private Map<String, String> layoutMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view instanceof BaseControllerApiView) {
            BaseControllerApiView apiView = (BaseControllerApiView) view;
            Map<String, Object> root = TextUtils.toJSONMap(getAssetsString("controller/main.json"));
            if (TextUtils.check(root)) {
                executeNon(root.get("layout"), obj -> {
                    layoutMap = new LinkedHashMap<>();
                    execute((Map<String, Object>) obj, (key, value) -> {
                        if (TextUtils.check(key, value)) {
                            layoutMap.put(key, value instanceof String ? (String) value : encode(value));
                        }
                    });
                });
                executeNon(root.get("data"), obj -> onData(apiView, (Map<String, Object>) obj));
            }
        }
        return view;
    }

    private void onData(@Nullable BaseControllerApiView view, @Nullable Map<String, Object> map) {
        Class clz = ObjectUtil.getClass((String) map.get("controllerApi"));
        if (clz != null && IControllerApi.class.isAssignableFrom(clz)) {
            IControllerApi api = (IControllerApi) ObjectUtil.getObject(clz, Object.class, view);
            if (api != null) {
                view.init(api);
                if (api instanceof RecycleBarControllerApi) {
                    onRecycleBarControllerApi((RecycleBarControllerApi) api, map);
                }
            }
        }
    }

    private void onRecycleBarControllerApi(@Nullable RecycleBarControllerApi api,
                                           @Nullable Map<String, Object> map) {
        initValue(map.get("hideBackIv"), boolean.class, obj -> obj, obj -> api.hideBackIv());
        initValue(map.get("title"), String.class, api::setTitle);
        RecyclerView recyclerView = api.getRecyclerView();
        ViewGroup vg = (ViewGroup) recyclerView.getParent();
        vg.removeView(recyclerView);
        initValue(map.get("recycle"), List.class, data -> execute(data, item -> {
            Map<String, Object> itemMap = (Map<String, Object>) item;
            Map<String, Object> itemDataMap = (Map<String, Object>) itemMap.get("data");
            AtomicReference<String> json = new AtomicReference<>(layoutMap.get(itemMap.get("layout")));
            if (TextUtils.check(json.get(), itemDataMap)) {
                execute(itemDataMap, (key, value) -> {
                    if (value instanceof String) {
                        json.set(json.get().replace(String.format("${%s}", key), (String) value));
                    }
                });
            }
            View view = createJsonView(json.get(), vg);
            if (TextUtils.check(itemDataMap) && view != null) {
                String onClick = (String) itemDataMap.get("onClick");
                if (TextUtils.check(onClick) && onClick.startsWith("app://route/")) {
                    setOnClickListener(view, v -> routeApi().route(onClick));
                }
            }
        }));
    }

    private <A> void initValue(Object value, Class<A> clz, IObjAction<A> action) {
        initValue(value, clz, obj -> true, action);
    }

    private <A> void initValue(Object value, Class<A> clz, IReturnAction<A, Boolean> a,
                               IObjAction<A> action) {
        if (TextUtils.check(value, clz, a, action)) {
            A aa = null;
            if (String.class.isAssignableFrom(clz)) {
                aa = (A) value;
            } else if (int.class.isAssignableFrom(clz)) {
                aa = (A) ((Integer) JavaTypeUtil.getInt(value, 0));
            } else if (float.class.isAssignableFrom(clz)) {
                aa = (A) ((Float) JavaTypeUtil.getfloat(value, 0));
            } else if (double.class.isAssignableFrom(clz)) {
                aa = (A) ((Double) JavaTypeUtil.getdouble(value, 0));
            } else if (long.class.isAssignableFrom(clz)) {
                aa = (A) ((Long) JavaTypeUtil.getlong(value, 0));
            } else if (boolean.class.isAssignableFrom(clz)) {
                aa = (A) ((Boolean) JavaTypeUtil.getboolean(value, false));
            } else if (clz.isInstance(value)) {
                aa = (A) value;
            }
            if (aa != null && a.execute(aa)) {
                action.execute(aa);
            }
        }
    }

}