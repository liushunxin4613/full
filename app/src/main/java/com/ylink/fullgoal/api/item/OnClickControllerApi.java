package com.ylink.fullgoal.api.item;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.OnClickNorm;

public class OnClickControllerApi<T extends OnClickControllerApi, C, N extends OnClickNorm> extends
        NormControllerApi<T, C, N> {

    public OnClickControllerApi(C controller) {
        super(controller);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onSafeNorm(@NonNull N norm, int position) {
        setEnableOnClickListener(norm.getOnClickListener())
                .setColorBg(getRootVg(), norm.isSelected() ? R.color.gray_light : R.color.white);
    }

    protected View getRootVg() {
        return null;
    }

    String checkFormat(String format, String key) {
        if (TextUtils.check(format, key)) {
            return String.format(format, key);
        }
        return null;
    }

    String checkFormat(String format, String key, String value) {
        if (TextUtils.check(format, key, value)) {
            return String.format(format, key, value);
        }
        return null;
    }

    protected String format(String format, String... args) {
        if (TextUtils.check(format, args)) {
            return String.format(format, (Object[]) args);
        }
        return null;
    }

}