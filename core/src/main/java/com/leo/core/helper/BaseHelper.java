package com.leo.core.helper;

import com.leo.core.api.core.ThisApi;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.ToastUtil;

public class BaseHelper<T extends BaseHelper> extends ThisApi<T> {

    protected synchronized <R> R decode(String in, Class<R> clz) {
        return GsonDecodeUtil.decode(in, clz);
    }

    public T ee(String title, String text) {
        return getThis();
    }

    public T ee(String title, Object obj) {
        return getThis();
    }

    public T show(String text){
        ToastUtil.show(text);
        return getThis();
    }

}
