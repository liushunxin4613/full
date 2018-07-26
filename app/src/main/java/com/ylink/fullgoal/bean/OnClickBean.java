package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class OnClickBean<T extends OnClickBean> extends SurfaceBiBean<T> {

    private transient View.OnClickListener onClickListener;

    public OnClickBean(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

}
