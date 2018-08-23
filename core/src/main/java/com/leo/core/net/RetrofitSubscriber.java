package com.leo.core.net;

import com.blankj.utilcode.util.NetworkUtils;
import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.bean.Completed;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.main.IControllerApi;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;

@SuppressWarnings("rawtypes")
public class RetrofitSubscriber<T extends RetrofitSubscriber, B> extends MsgSubscriber<T, B> {

    private IParseApi api;
    private CoreControllerApi controllerApi;

    public RetrofitSubscriber(CoreControllerApi controllerApi) {
        this.controllerApi = controllerApi;
        this.api = controllerApi.parseApi().copy();
    }

    private IControllerApi getControllerApi() {
        return controllerApi;
    }

    @Override
    public T init(String type, String baseUrl, String path, Map<String, String> map, int what, String msg) {
        if (api != null) {
            api.init(type, baseUrl, path, map, what, msg);
        }
        return (T) this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getControllerApi() != null) {
            getControllerApi().onStartHttpRequest();
        }
    }

    @Override
    public void onCompleted() {
        parse(new Completed());
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException) {
            if (!NetworkUtils.isConnected()) {
                parse("网络不可用", HttpError.ERROR_CONNECT, e);
            } else {
                parse("网络连接异常", HttpError.ERROR_CONNECT, e);
            }
        } else if (e instanceof NoRouteToHostException) {
            parse("没有到主机的路由", HttpError.ERROR_NO_ROUTE_TO_HOST, e);
        } else if (e instanceof HttpException) {
            parse("服务器异常", HttpError.ERROR_HTTP, e);
        } else if (e instanceof SocketTimeoutException) {
            parse("连接超时", HttpError.ERROR_SOCKET_TIME_OUT, e);
        } else {
            parse("数据异常", HttpError.ERROR_DATA, e);
        }
    }

    @Override
    public void onNext(B next) {
        if (next == null) {
            parse("next数据为空", HttpError.ERROR_DATA_NULL,
                    new NullPointerException("onNext数据为空"));
        } else {
            parse(next);
        }
    }

    private void parse(String message, int code, Throwable e) {
        parse(new Exceptions<>(message, code, e));
    }

    /**
     * 检测是否是辅助类型
     *
     * @param obj obj
     */
    private boolean checkThread(Object obj) {
        return !(obj == null
                || obj instanceof Completed
                || obj instanceof Exceptions);
    }

    private void parse(Object obj) {
        if (obj != null && api != null) {
            if (checkThread(obj)) {
                new Thread(() -> api.parse(obj)).start();
            } else {
                api.parse(obj);
            }
        }
    }

}