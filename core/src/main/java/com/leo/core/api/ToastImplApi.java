package com.leo.core.api;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.leo.core.iapi.IShowApi;

/**
 * ToastApi类
 */
public class ToastImplApi extends BindContextApi<ToastImplApi> implements IShowApi<ToastImplApi, Object, Integer> {

    /**
     * 显示数据
     * @param in 输入数据
     * @param duration 显示参数
     * @return 本身
     */
    @Override
    public ToastImplApi show(Object in, @NonNull Integer duration) {
        checkContext();
        if (in instanceof CharSequence){
            Toast.makeText(getContext(), (CharSequence) in, duration).show();
        }else if (in instanceof Integer){
            Toast.makeText(getContext(), (Integer) in, duration).show();
        }else if(in != null){
            Toast.makeText(getContext(), in.toString(), duration).show();
        }
        return this;
    }

    /**
     * 显示数据
     * @param in 输入数据
     * @return 本身
     */
    public ToastImplApi show(@NonNull CharSequence in) {
        show(in, Toast.LENGTH_SHORT);
        return this;
    }

    /**
     * 显示数据
     * @param resId string id
     * @return 本身
     */
    public ToastImplApi show(@StringRes int resId) {
        show(resId, Toast.LENGTH_SHORT);
        return this;
    }

    private void checkContext(){
        if (getContext() == null){
            throw new NullPointerException("ToastImplApi 没有bind(context)");
        }
    }

}
