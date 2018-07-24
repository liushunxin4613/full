package com.ylink.fullgoal.api.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.CoreRouteApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.other.MMap;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.full.FullAutoSearchControllerApi;
import com.ylink.fullgoal.api.full.FullBankControllerApiV1;
import com.ylink.fullgoal.api.full.FullBillControllerApi;
import com.ylink.fullgoal.api.full.FullCostIndexControllerApi;
import com.ylink.fullgoal.api.full.FullEvectionControllerApi;
import com.ylink.fullgoal.api.full.FullEvectionControllerApiV2;
import com.ylink.fullgoal.api.full.FullGeneralControllerApi;
import com.ylink.fullgoal.api.full.FullGeneralControllerApiV1;
import com.ylink.fullgoal.api.full.FullGeneralControllerApiV2;
import com.ylink.fullgoal.api.full.FullReimburseDataControllerApi;
import com.ylink.fullgoal.api.full.FullSearchControllerApi;
import com.ylink.fullgoal.api.full.FullSearchControllerApiV1;
import com.ylink.fullgoal.api.full.FullSearchControllerApiV2;
import com.ylink.fullgoal.main.SurfaceActivity;

import java.lang.reflect.Type;
import java.util.List;

import cn.com.fullgoal.pt0001.MainActivity;

import static com.ylink.fullgoal.config.Config.FILTERS;
import static com.ylink.fullgoal.config.Config.KEY;
import static com.ylink.fullgoal.config.Config.SEARCH;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.VALUE;
import static com.ylink.fullgoal.config.Config.VERSION;

public class RouteApi extends CoreRouteApi {

    public final static String MODULE_APP = "app";
    public final static String MODULE_V1 = "v1";
    public final static String MODULE_V2 = "v2";

    private final static String MODULE = MODULE_APP;
    private final static String[] MODULE_ARGS = {MODULE_APP, MODULE_V1, MODULE_V2};

    public RouteApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    private void routeSurface(String name) {
        routeSurface(name, null);
    }

    private void routeSurface(String name, IObjAction<MMap<String, String>> action) {
        route(MODULE, String.format("surface/%s", no(name)), null, action);
    }

    private IObjAction<MMap<String, String>> getAction(Object... args) {
        return map -> execute(args, item -> map.put(getObjectType(item).toString(),
                controllerApi().encode(item)));
    }

    private Type getObjectType(Object obj) {
        if (obj instanceof List) {
            List data = (List) obj;
            if (!TextUtils.isEmpty(data)) {
                for (Object item : data) {
                    if (item != null) {
                        return TypeToken.getParameterized(List.class,
                                getObjectType(item)).getType();
                    }
                }
            }
            return List.class;
        } else if (obj != null) {
            return obj.getClass();
        }
        return null;
    }

    private void onSearch() {
        SoftInputUtil.hidSoftInput(controllerApi().getRootView());
    }

    /**
     * 一般费用报销
     */
    public void general(String state) {
        general(state, null);
    }

    /**
     * 出差费用报销
     */
    public void evection(String state) {
        evection(state, null);
    }

    /**
     * 搜索页面
     */
    public void search(String search) {
        search(search, null, null, null);
    }

    /**
     * 搜索页面
     */
    public void search(String search, String key) {
        search(search, key, null, null);
    }

    /**
     * 搜索页面
     */
    public void search(String search, List<String> filterData) {
        search(search, null, null, filterData);
    }

    /**
     * 搜索页面
     */
    public void search(String search, String key, List<String> filterData) {
        search(search, key, null, filterData);
    }

    /**
     * 搜索页面
     */
    public void search(String search, String key, String value) {
        search(search, key, value, null);
    }

    @Override
    protected String[] getModuleArgs() {
        return MODULE_ARGS;
    }

    @Override
    protected Class<? extends Activity> getActivityClz(@NonNull String module, @NonNull String activity) {
        switch (activity) {
            case "main":
                return MainActivity.class;
            case "surface":
                return SurfaceActivity.class;
        }
        return null;
    }

    @Override
    protected Class<? extends IControllerApi> getApiClz(@NonNull String module, @NonNull String api) {
        switch (api) {
            case "general"://一般费用普票报销
                return getClz(module, FullGeneralControllerApi.class,
                        FullGeneralControllerApiV1.class,
                        FullGeneralControllerApiV2.class);
            case "evection"://出差费用普票报销
                return getClz(module, FullEvectionControllerApi.class,
                        FullEvectionControllerApi.class,
                        FullEvectionControllerApiV2.class);
            case "queryReimburse"://报销列表查询
                return getClz(module, FullReimburseDataControllerApi.class);
            case "selectBank"://选择银行卡
                return getClz(module, FullBankControllerApiV1.class);
            case "costIndex"://费用指标
                return getClz(module, FullCostIndexControllerApi.class);
            case "bill"://票据
                return getClz(module, FullBillControllerApi.class);
            case "search"://搜索
                return getClz(module, FullSearchControllerApi.class,
                        FullSearchControllerApiV1.class,
                        FullSearchControllerApiV2.class);
            case "searchApply"://搜索申请单
                return getClz(module, FullSearchControllerApiV2.class);
            case "searchApplyContent"://搜索申请单内容
                return getClz(module, FullAutoSearchControllerApi.class);
        }
        return null;
    }

    @Override
    protected Class<? extends IControllerApi> getViewApiClz(@NonNull String module, @NonNull String viewApi) {
        return null;
    }

    /**
     * 一般费用报销
     */
    public void general(String state, String serialNo) {
        route(VERSION, "surface/general", null, map -> map.put(STATE, state).put(SERIAL_NO, serialNo));
    }

    /**
     * 出差费用报销
     */
    public void evection(String state, String serialNo) {
        route(VERSION, "surface/evection", null, map -> map.put(STATE, state).put(SERIAL_NO, serialNo));
    }

    /**
     * 报销列表查询
     */
    public void queryReimburse() {
        routeSurface("queryReimburse");
    }

    /**
     * 选择银行卡
     */
    public void selectBank() {
        routeSurface("selectBank");
    }

    /**
     * 费用指标
     */
    public void costIndex(IObjAction<MMap<String, String>> action) {
        routeSurface("costIndex", action);
    }

    /**
     * 票据
     */
    public void bill(Object... args) {
        routeSurface("bill", getAction(args));
    }

    /**
     * 主activity
     */
    public void main() {
        route(MODULE, "main", map -> map.put(FINISH, FINISH), null);
    }

    /**
     * 搜索页面
     */
    public void search(String search, String key, String value, List<String> filterData) {
        onSearch();
        route(MODULE, "surface/search", null, map -> map.put(SEARCH, search).put(KEY, key)
                .put(VALUE, value).put(FILTERS, controllerApi().encode(filterData)));
    }

    /**
     * 搜索申请单
     */
    public void searchApply(String search, String key, String value) {
        onSearch();
        route(MODULE, "surface/searchApply", null, map -> map.put(SEARCH, search).put(KEY, key).put(VALUE, value));
    }

    /**
     * 搜索申请单内容
     */
    public void searchApplyContent(String search, String key) {
        onSearch();
        route(MODULE, "surface/searchApplyContent", null, map -> map.put(SEARCH, search).put(KEY, key));
    }

}