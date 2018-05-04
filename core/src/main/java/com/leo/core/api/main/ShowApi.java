package com.leo.core.api.main;

import android.support.annotation.NonNull;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IDecodeApi;
import com.leo.core.iapi.main.IShowApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.util.ToastUtil;

public class ShowApi<T extends ShowApi> extends ThisApi<T> implements IShowApi<T> {

    private Object thisObj;
    private IDecodeApi decodeApi;
    private IDecodeApi newDecodeApi;

    public ShowApi(Object thisObj, IDecodeApi newDecodeApi) {
        this.thisObj = thisObj;
        this.newDecodeApi = newDecodeApi;
    }

    @Override
    public IDecodeApi<IDecodeApi, Object, String, Object, Object> decodeApi() {
        if (decodeApi == null){
            decodeApi = newDecodeApi();
            if(decodeApi == null){
                throw new NullPointerException("newDecodeApi 不能为空");
            }
        }
        return decodeApi;
    }

    @Override
    public IDecodeApi newDecodeApi() {
        return newDecodeApi;
    }

    @Override
    public String encode(Object obj) {
        return decodeApi().encode(obj);
    }

    @Override
    public <R> R decode(Object in, Object param) {
        return decodeApi().decode(in, param);
    }

    @Override
    public T show(@NonNull CharSequence text) {
        ToastUtil.show(text);
        return getThis();
    }

    @Override
    public String getLog(Object obj) {
        return LogUtil.getLog(obj);
    }

    @Override
    public T i(Object obj) {
        LogUtil.i(thisObj, getLog(obj));
        return getThis();
    }

    @Override
    public T i(CharSequence title, Object obj) {
        String text = LogUtil.getLog(obj);
        if (!TextUtils.isTrimEmpty(title)){
            if (!TextUtils.isTrimEmpty(text)){
                return i(title + ": " + text);
            }else {
                return i(title);
            }
        }else if (!TextUtils.isTrimEmpty(text)){
            return i(text);
        }
        return getThis();
    }

    @Override
    public T e(Object obj) {
        LogUtil.e(thisObj, getLog(obj));
        return getThis();
    }

    @Override
    public T e(CharSequence title, Object obj) {
        String text = LogUtil.getLog(obj);
        if (!TextUtils.isTrimEmpty(title)){
            if (!TextUtils.isTrimEmpty(text)){
                return e(title + ": " + text);
            }else {
                return e(title);
            }
        }else if (!TextUtils.isTrimEmpty(text)){
            return e(text);
        }
        return getThis();
    }

}
