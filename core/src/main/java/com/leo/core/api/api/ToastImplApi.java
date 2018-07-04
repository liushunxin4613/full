package com.leo.core.api.api;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.leo.core.iapi.api.IShowApi;
import com.leo.core.util.TextUtils;

/**
 * ToastApi类
 */
public class ToastImplApi extends BindContextApi<ToastImplApi> implements IShowApi<ToastImplApi, Object, Integer> {

    private final static int interval = 1000;//间隔时间

    private long time;
    private Object text;
    private Object thisObj;

    private boolean check(Object thisObj, Object text) {
        if(thisObj != null){
            long time = System.currentTimeMillis();
            boolean check = !(TextUtils.equals(thisObj, this.thisObj)
                    && TextUtils.equals(text, this.text)
                    && time - this.time <= interval);
            this.text = text;
            this.thisObj = thisObj;
            this.time = time;
            return check;
        }
        return true;
    }

    /**
     * 显示数据
     *
     * @param in       输入数据
     * @param duration 显示参数
     * @return 本身
     */
    @Override
    public ToastImplApi show(Object thisObj, @NonNull Object in, @NonNull Integer duration) {
        checkContext();
        if (check(thisObj, in)) {
            if (in instanceof CharSequence) {
                Toast.makeText(getContext(), (CharSequence) in, duration).show();
            } else if (in instanceof Integer) {
                Toast.makeText(getContext(), (Integer) in, duration).show();
            } else if (in != null) {
                Toast.makeText(getContext(), in.toString(), duration).show();
            }
        }
        return this;
    }

    /**
     * 显示数据
     *
     * @param in 输入数据
     * @return 本身
     */
    public ToastImplApi show(Object thisObj, @NonNull CharSequence in) {
        show(thisObj, in, Toast.LENGTH_SHORT);
        return this;
    }

    /**
     * 显示数据
     *
     * @param resId string id
     * @return 本身
     */
    public ToastImplApi show(Object thisObj, @StringRes int resId) {
        show(thisObj, resId, Toast.LENGTH_SHORT);
        return this;
    }

    private void checkContext() {
        if (getContext() == null) {
            throw new NullPointerException("ToastImplApi 没有bind(context)");
        }
    }

}