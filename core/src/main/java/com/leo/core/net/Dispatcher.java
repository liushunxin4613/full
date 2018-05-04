package com.leo.core.net;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IDispatcher;
import com.leo.core.util.TextUtils;

import java.util.List;


public class Dispatcher<T extends Dispatcher> extends ThisApi<T> implements IDispatcher {

    @Override
    public void onDispatcher(Object obj) {
        onStart(obj);
        onData(obj);
    }

    @Override
    public void onStart(Object obj) {
//        if (getShow()){
//            ee("onStart", obj);
//        }
    }

    @Override
    public void onError(Throwable e, int what, String msg) {
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onData(Object obj) {
        if (obj instanceof List) {
            List data = (List) obj;
            if (!TextUtils.isEmpty(data)) {
                for (int i = 0; i < data.size(); i++) {
                    onData(data.get(i));
                }
            }
        }
    }

}
