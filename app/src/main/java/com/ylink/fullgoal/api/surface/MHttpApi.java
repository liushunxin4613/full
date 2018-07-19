package com.ylink.fullgoal.api.surface;

import android.support.annotation.NonNull;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HttpApi;
import com.leo.core.net.AuxiliaryFactory;
import com.leo.core.net.RetrofitSubscriber;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.UrlConfig;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.Map;

import rx.Observable;

import static com.leo.core.util.TextUtils.getHashMap;
import static com.leo.core.util.TextUtils.getUriParams;
import static com.ylink.fullgoal.config.Config.SIMULATE_HTTP;

public class MHttpApi<T extends MHttpApi> extends HttpApi<T> {

    public MHttpApi(CoreControllerApi controllerApi, Observable.Transformer newTransformer) {
        super(controllerApi, newTransformer);
    }

    private void showLoading() {
        if (controllerApi() instanceof SurfaceControllerApi) {
            ((SurfaceControllerApi) controllerApi()).showLoading();
        }
    }

    @Override
    protected <B> boolean checkObservable(@NonNull Observable<B> observable, String startUrl,
                                          String path, Map<String, String> map, int what, String tag) {
        if (SIMULATE_HTTP) {//拦截并处理代码
            String url = startUrl + path;
            String params = getUriParams(map);
            LogUtil.ii(this, "url: " + url);
            LogUtil.ii(this, "params: " + params);
            AuxiliaryFactory.getInstance().postSimulate(getHashMap(m -> {
                m.put("url", url);
                m.put("params", params);
            }), new RetrofitSubscriber<>(controllerApi().parseApi().copy()).init(path, what, tag));
            showLoading(path);
            return false;
        }
        return true;
    }

    @Override
    protected <B> void onObservable(@NonNull Observable<B> observable, String startUrl, String path,
                                    Map<String, String> map, int what, String tag) {
        super.onObservable(observable, startUrl, path, map, what, tag);
        showLoading(path);
    }

    private void showLoading(String path) {
        if (TextUtils.getListData(UrlConfig.LOADING_DIALOGS).contains(path)) {
            showLoading();
        }
    }

}