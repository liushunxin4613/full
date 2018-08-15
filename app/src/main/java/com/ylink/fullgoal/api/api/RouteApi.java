package com.ylink.fullgoal.api.api;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.api.CoreRouteApi;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.other.MMap;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ylink.fullgoal.api.full.FullAutoSearchControllerApi;
import com.ylink.fullgoal.api.full.FullBankControllerApi;
import com.ylink.fullgoal.api.full.FullBillControllerApi;
import com.ylink.fullgoal.api.full.FullCostIndexControllerApi;
import com.ylink.fullgoal.api.full.FullEvectionControllerApi;
import com.ylink.fullgoal.api.full.FullGeneralControllerApi;
import com.ylink.fullgoal.api.full.FullReimburseDataControllerApi;
import com.ylink.fullgoal.api.full.FullSearchControllerApi;
import com.ylink.fullgoal.api.full.FullSearchControllerApiV1;
import com.ylink.fullgoal.api.item.ImageTailorControllerApi;
import com.ylink.fullgoal.api.surface.Camera2ControllerApi;
import com.ylink.fullgoal.main.SurfaceActivity;

import java.lang.reflect.Type;
import java.util.List;

import cn.com.fullgoal.pt0001.MainActivity;

import static com.ylink.fullgoal.config.Config.FILE_PATH;
import static com.ylink.fullgoal.config.Config.FILTERS;
import static com.ylink.fullgoal.config.Config.IMAGE_TYPE;
import static com.ylink.fullgoal.config.Config.JSON;
import static com.ylink.fullgoal.config.Config.KEY;
import static com.ylink.fullgoal.config.Config.MAIN_APP;
import static com.ylink.fullgoal.config.Config.SEARCH;
import static com.ylink.fullgoal.config.Config.SEARCH_EVECTION;
import static com.ylink.fullgoal.config.Config.SEARCH_TITLE;
import static com.ylink.fullgoal.config.Config.SERIAL_NO;
import static com.ylink.fullgoal.config.Config.STATE;
import static com.ylink.fullgoal.config.Config.TAG;
import static com.ylink.fullgoal.config.Config.TITLE;
import static com.ylink.fullgoal.config.Config.VALUE;
import static com.ylink.fullgoal.config.Config.VERSION;

public class RouteApi extends CoreRouteApi {

    public final static String MODULE_APP = "app";

    private final static String MODULE = MODULE_APP;
    private final static String[] MODULE_ARGS = {MODULE_APP};

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
        general(state, null, null);
    }

    /**
     * 出差费用报销
     */
    public void evection(String state) {
        evection(state, null, null);
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
    public void searchValue(String search, String value) {
        search(search, null, value, null);
    }

    /**
     * 搜索页面
     */
    public void searchKV(String search, String kv) {
        search(search, kv, kv, null);
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
                return FullGeneralControllerApi.class;
            case "evection"://出差费用普票报销
                return FullEvectionControllerApi.class;
            case "queryReimburse"://报销列表查询
                return FullReimburseDataControllerApi.class;
            case "selectBank"://选择银行卡
                return FullBankControllerApi.class;
            case "costIndex"://费用指标
                return FullCostIndexControllerApi.class;
            case "bill"://票据
                return FullBillControllerApi.class;
            case "search"://搜索
                return FullSearchControllerApi.class;
            case "searchApply"://搜索申请单
                return FullSearchControllerApiV1.class;
            case "searchApplyContent"://搜索申请单内容
                return FullAutoSearchControllerApi.class;
            case "camera"://拍照
                return Camera2ControllerApi.class;
            case "tailor"://裁剪
                return ImageTailorControllerApi.class;
        }
        return null;
    }

    @Override
    protected Class<? extends IControllerApi> getViewApiClz(@NonNull String module,
                                                            @NonNull String viewApi) {
        return null;
    }

    /**
     * 一般费用报销
     */
    public void general(String state, String title, String serialNo) {
        route(VERSION, "surface/general", null, map
                -> map.put(STATE, state)
                .put(TITLE, title)
                .put(SERIAL_NO, serialNo));
    }

    /**
     * 出差费用报销
     */
    public void evection(String state, String title, String serialNo) {
        route(VERSION, "surface/evection", null, map
                -> map.put(STATE, state)
                .put(TITLE, title)
                .put(SERIAL_NO, serialNo));
    }

    /**
     * 一般费用报销
     */
    public void generalMain(String state, String json) {
        route(VERSION, "surface/general", null, map
                -> map.put(STATE, state)
                .put(MAIN_APP, MAIN_APP)
                .put(JSON, json));
    }

    /**
     * 出差费用报销
     */
    public void evectionMain(String state, String json) {
        route(VERSION, "surface/evection", null, map
                -> map.put(STATE, state)
                .put(MAIN_APP, MAIN_APP)
                .put(JSON, json));
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
        route(MODULE, "surface/search", null, map
                -> map.put(SEARCH_TITLE, search)
                .put(SEARCH, search)
                .put(KEY, key)
                .put(VALUE, value)
                .put(FILTERS, controllerApi().encode(filterData)));
    }

    /**
     * 搜索页面
     */
    public void searchEvection(String search, String key, String value) {
        onSearch();
        route(MODULE, "surface/search", null, map
                -> map.put(SEARCH_TITLE, search)
                .put(SEARCH, search)
                .put(KEY, key)
                .put(VALUE, value)
                .put(TAG, SEARCH_EVECTION));
    }

    /**
     * 搜索申请单
     */
    public void searchApply(String search, String key, String value) {
        onSearch();
        route(MODULE, "surface/searchApply", null, map
                -> map.put(SEARCH, search)
                .put(KEY, key)
                .put(VALUE, value));
    }

    /**
     * 搜索申请单内容
     */
    public void searchApplyContent(String search, String key, String value) {
        onSearch();
        route(MODULE, "surface/searchApplyContent", null, map
                -> map.put(SEARCH, search)
                .put(KEY, key)
                .put(VALUE, value));
    }

    /**
     * 拍照
     */
    public void camera(int type) {
        checkCamera(() -> routeSurface("camera", m -> m.put(IMAGE_TYPE, String.valueOf(type))));
    }

    /**
     * 裁剪
     */
    public void tailor(String imageType, String filePath) {
        route(MODULE, "surface/tailor", null, m -> m.put(FILE_PATH, filePath)
                .put(IMAGE_TYPE, imageType));
    }

    //私有的

    @SuppressLint("CheckResult")
    private void checkCamera(IAction action) {
        if (action != null) {
            if (isCameraCanUse()) {
                action.execute();
            } else {
                final RxPermissions rxPermissions = new RxPermissions(controllerApi().getFragmentActivity());
                rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(permission -> {
                            if (permission.granted && isCameraCanUse()) {
                                action.execute();
                            } else {
                                show("请开启拍照权限");
                            }
                        });
            }
        }
    }

    private static boolean isCameraCanUse() {
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            mCamera.setParameters(mCamera.getParameters());
            return true;
        } catch (Exception ignored) {
        } finally {
            if (mCamera != null) {
                mCamera.release();
            }
        }
        return false;
    }

}