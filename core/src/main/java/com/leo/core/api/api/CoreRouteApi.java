package com.leo.core.api.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IRouteApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.other.MMap;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static com.leo.core.iapi.api.IStartApi.CONTROLLER_API;
import static com.leo.core.iapi.api.IStartApi.ROOT_VIEW_CLZ_API;
import static com.leo.core.util.LogUtil.ii;

public abstract class CoreRouteApi extends HasCoreControllerApi<CoreRouteApi> implements IRouteApi {

    private final static String URL_START = "app://route";
    private final static String COMMAND = "command";
    private final static String PARAMS = "params";
    protected final static String FINISH = "finish";
    private final static int CHECK_TIME = 500;
    private long checkTime;

    public CoreRouteApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    protected abstract String[] getModuleArgs();

    protected abstract Class<? extends Activity> getActivityClz(@NonNull String module, @NonNull String activity);

    protected abstract Class<? extends IControllerApi> getApiClz(@NonNull String module, @NonNull String api);

    protected abstract Class<? extends IControllerApi> getViewApiClz(@NonNull String module, @NonNull String viewApi);

    protected <A> Class<? extends A> getClz(String module, Class<? extends A>... args) {
        int index = TextUtils.indexOf(getModuleArgs(), module);
        if (index >= 0 && index < args.length) {
            return args[index];
        }
        if (args.length > 0) {
            return args[0];
        }
        return null;
    }

    private <A> A get(A[] args, int position) {
        if (args != null && position < args.length) {
            return args[position];
        }
        return null;
    }

    private Intent getIntent(String module, String activity, String api, String viewApi,
                             Map<String, String> paramsMap) {
        if (TextUtils.checkNull(module, activity)) {
            Class<? extends Activity> clz = getActivityClz(module, activity);
            if (clz != null) {
                Intent intent = new Intent(controllerApi().getActivity(), clz);
                intent.putExtras(getBundle(paramsMap));
                if (TextUtils.checkNull(api)) {
                    intent.putExtra(CONTROLLER_API, getApiClz(module, api));
                }
                if (TextUtils.checkNull(viewApi)) {
                    intent.putExtra(ROOT_VIEW_CLZ_API, getViewApiClz(module, viewApi));
                }
                return intent;
            }
        }
        return null;
    }

    private Map<String, String> getStringMap(String text) {
        Map<String, String> map = new HashMap<>();
        execute(TextUtils.toJSONMap(text), (key, value) -> {
            if (TextUtils.check(key) && value instanceof String) {
                map.put(key, (String) value);
            }
        });
        return map;
    }

    @SuppressLint("NewApi")
    private Bundle getBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        execute(map, bundle::putString);
        return bundle;
    }

    @Override
    public void route(String url) {
        if (TextUtils.check(url) && url.startsWith(URL_START)) {
            Uri uri = Uri.parse(url);
            ii(this, "uri ==> " + uri.toString());
            route(getPath(uri.getPath()), getStringMap(uri.getQueryParameter(COMMAND)),
                    getStringMap(uri.getQueryParameter(PARAMS)));
        }
    }

    @Override
    public void route(String path, String command, String params) {
        route(path, getStringMap(command), getStringMap(command));
    }

    @Override
    public void route(String path, IObjAction<MMap<String, String>> commandAction,
                      IObjAction<MMap<String, String>> paramsAction) {
        route(path, map(commandAction), map(paramsAction));
    }

    @Override
    public void route(String module, String path, IObjAction<MMap<String, String>> commandAction,
                      IObjAction<MMap<String, String>> paramsAction) {
        route(String.format("%s/%s", no(module), no(path)), map(commandAction), map(paramsAction));
    }

    @Override
    public void route(String path, Map<String, String> commandMap, Map<String, String> paramsMap) {
        if (TextUtils.check(path)) {
            String[] args = path.split("/");
            route(get(args, 0), get(args, 1), get(args, 2), get(args, 3), commandMap, paramsMap);
        }
    }

    @Override
    public void route(String module, String activity, String api, String viewApi, Map<String, String>
            commandMap, Map<String, String> paramsMap) {
        if(checkTime()){
            onRoute(module, activity, api, viewApi, commandMap, paramsMap);
            String finish = vr(commandMap, map -> map.get(FINISH));
            if (TextUtils.equals(finish, FINISH)) {
                controllerApi().getActivity().finish();
            }
            Intent intent = getIntent(module, activity, api, viewApi, paramsMap);
            if (intent != null) {
                controllerApi().getActivity().startActivity(intent);
                checkTime = System.currentTimeMillis();
            }
        }
    }

    private void onRoute(String module, String activity, String api, String viewApi, Map<String, String>
            commandMap, Map<String, String> paramsMap) {
        String path = getPath(module, activity, api, viewApi);
        if (TextUtils.check(path)) {
            String query = getQuery(map(map -> map.put(COMMAND, LogUtil.getLog(false, commandMap))
                    .put(PARAMS, LogUtil.getLog(false, paramsMap))));
            String url = String.format("%s/%s%s", URL_START, path, no(query));
            ii(this, "uri <== " + url);
        }
    }

    private String getPath(String... args) {
        if (!TextUtils.isEmpty(args)) {
            StringBuilder builder = new StringBuilder();
            for (int i = args.length - 1; i >= 0; i--) {
                String text = args[i];
                if (TextUtils.check(text)) {
                    if (builder.length() > 0) {
                        builder.insert(0, "/").insert(0, text);
                    } else {
                        builder.append(text);
                    }
                } else if (builder.length() > 0) {
                    return null;
                }
            }
            return builder.toString();
        }
        return null;
    }

    private String getQuery(Map<String, String> map) {
        if (TextUtils.check(map)) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (TextUtils.check(entry.getKey(), entry.getValue())) {
                    builder.append(builder.length() > 0 ? "&" : "?")
                            .append(entry.getKey()).append("=")
                            .append(entry.getValue());
                }
            }
            return builder.toString();
        }
        return null;
    }

    private String getPath(String path) {
        if (path != null && path.startsWith("/")) {
            return path.substring(1, path.length());
        }
        return path;
    }

    protected IObjAction<MMap<String, String>> getFinishCommandMapAction() {
        return m -> m.put(FINISH, FINISH);
    }

    private boolean checkTime(){
        return System.currentTimeMillis() - checkTime > CHECK_TIME;
    }

}