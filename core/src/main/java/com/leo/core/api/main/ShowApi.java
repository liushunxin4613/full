package com.leo.core.api.main;

import android.support.annotation.NonNull;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.IDecodeApi;
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
        if (decodeApi == null) {
            decodeApi = newDecodeApi();
            if (decodeApi == null) {
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
        ToastUtil.show(thisObj, text);
        return getThis();
    }

    @Override
    public String getLog(Object obj) {
        return LogUtil.getLog(obj);
    }

    @Override
    public T ii(Object obj) {
        LogUtil.ii(thisObj, getLog(obj));
        return getThis();
    }

    @Override
    public T ii(CharSequence title, Object obj) {
        String text = LogUtil.getLog(obj);
        if (!TextUtils.isTrimEmpty(title)) {
            if (!TextUtils.isTrimEmpty(text)) {
                return ii(title + ": " + text);
            } else {
                return ii(title);
            }
        } else if (!TextUtils.isTrimEmpty(text)) {
            return ii(text);
        }
        return getThis();
    }

    @Override
    public T ee(Object obj) {
        LogUtil.ee(thisObj, getLog(obj));
        return getThis();
    }

    @Override
    public T ee(CharSequence title, Object obj) {
        String text = LogUtil.getLog(obj);
        if (!TextUtils.isTrimEmpty(title)) {
            if (!TextUtils.isTrimEmpty(text)) {
                return ee(title + ": " + text);
            } else {
                return ee(title);
            }
        } else if (!TextUtils.isTrimEmpty(text)) {
            return ee(text);
        }
        return getThis();
    }

}
