package com.ylink.fullgoal.api.surface;

import android.support.annotation.NonNull;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HttpApi;
import com.leo.core.net.AuxiliaryFactory;
import com.leo.core.net.RetrofitSubscriber;
import com.leo.core.util.JsonShowUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.UrlConfig;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.db.table.Request;
import com.ylink.fullgoal.db.table.Times;

import java.util.Map;

import rx.Observable;

import static com.leo.core.util.TextUtils.getUriParams;
import static com.ylink.fullgoal.config.ComConfig.SHOW_LOADING_NO;
import static com.ylink.fullgoal.config.ComConfig.SHOW_LOADING_VIEW_NO;
import static com.ylink.fullgoal.config.ComConfig.SHOW_LOADING_YES;
import static com.ylink.fullgoal.config.Config.HTTP_CACHE;
import static com.ylink.fullgoal.config.Config.SIMULATE_HTTP;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_TRANS_FILE;

public class MHttpApi<T extends MHttpApi> extends HttpApi<T> {

    public MHttpApi(CoreControllerApi controllerApi, Observable.Transformer newTransformer) {
        super(controllerApi, newTransformer);
    }

    private void showLoading(boolean hide) {
        if (controllerApi() instanceof SurfaceControllerApi) {
            ((SurfaceControllerApi) controllerApi()).showLoading(hide);
        }
    }

    @Override
    protected <B> boolean checkObservable(@NonNull Observable<B> observable, String type, String baseUrl,
                                          String path, Map<String, String> map, int what, String tag) {
        Times.sav(String.format("%s#%s", path, controllerApi().encode(map)), "开始");
        if (HTTP_CACHE) {//拦截并处理代码
            Request.query(baseUrl, path, controllerApi().encode(map), response -> {
                if (TextUtils.check(path)) {
                    if (TextUtils.isEmpty(response)) {
                        onObservable(observable, type, baseUrl, path, map, what, tag);
                    } else {
                        switch (path) {
                            case PATH_QUERY_TRANS_FILE://配置文件
                                break;
                            default:
                                String params = TextUtils.getUriParams(map);
                                String url = String.format("%s/%s%s", baseUrl, path,
                                        TextUtils.check(params) ? String.format("?%s", params) : "");
                                LogUtil.ii(this, String.format("%s\n%s", url,
                                        JsonShowUtil.getShowJson(response)));
                                onObservable(getObservable(response), type, baseUrl, path, map, what, tag);
                                break;
                        }
                    }
                }
            });
            return false;
        } else if (SIMULATE_HTTP) {//拦截并处理代码
            String url = baseUrl + path;
            String params = getUriParams(map);
            LogUtil.ii(this, "url: " + url);
            LogUtil.ii(this, "params: " + params);
            /*observable(controllerApi().encode(map(m -> m.put("url", url).put("params", params))),
                    AuxiliaryFactory.getInstance().getRootUrl(), "simulate", map, what, tag);*/
            AuxiliaryFactory.getInstance().postSimulate(map(m -> {
                m.put("url", url);
                m.put("params", params);
            }), new RetrofitSubscriber<>(controllerApi()).init(type, baseUrl, path, map, what, tag));
            showLoading(what, path);
            return false;
        }
        return true;
    }

    @Override
    protected void onObservable(@NonNull Observable observable, String type, String baseUrl, String path,
                                Map<String, String> map, int what, String tag) {
        showLoading(what, path);//必须放在具体请求之前
        super.onObservable(observable, type, baseUrl, path, map, what, tag);
    }

    private void showLoading(int what, String path) {
        switch (what) {
            case SHOW_LOADING_YES:
                showLoading(true);
                break;
            case SHOW_LOADING_NO:
                break;
            case SHOW_LOADING_VIEW_NO:
                showLoading(false);
                break;
            default:
                if (TextUtils.getListData(UrlConfig.LOADING_DIALOGS).contains(path)) {
                    showLoading(true);
                }
                break;
        }
    }

}