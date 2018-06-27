package com.leo.core.net;

import com.leo.core.api.inter.MsgSubscriber;
import com.leo.core.bean.Completed;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.util.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

@SuppressWarnings("rawtypes")
public class RetrofitSubscriber<T extends RetrofitSubscriber, B> extends MsgSubscriber<T, B> {

    private IParseApi api;

    public RetrofitSubscriber(IParseApi api) {
        this.api = api;
    }

    @Override
    public T init(String msg, int what, String tag) {
        if(api != null){
            api.init(msg, what, tag);
        }
        return (T) this;
    }

    @Override
    public void onCompleted() {
        execute(new Completed());
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException) {
            execute("网络异常", HttpError.ERROR_CONNECT, e);
        } else if (e instanceof HttpException) {
            execute("服务器异常", HttpError.ERROR_HTTP, e);
        } else if (e instanceof SocketTimeoutException) {
            execute("连接超时", HttpError.ERROR_SOCKET_TIME_OUT, e);
        } else {
            execute("数据异常", HttpError.ERROR_DATA, e);
        }
    }

    @Override
    public void onNext(B next) {
        if (next == null) {
            execute("next数据为空", HttpError.ERROR_DATA_NULL, new NullPointerException("onNext数据为空"));
        } else {
            execute(next);
        }
    }

    private void execute(Object obj) {
        if (obj != null && api != null) {
            api.execute(obj);
        }
    }

    private void execute(String message, int code, Throwable e) {
        execute(new Exceptions<>(message, code, e));
    }

}
