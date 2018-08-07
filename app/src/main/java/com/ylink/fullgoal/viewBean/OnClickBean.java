package com.ylink.fullgoal.viewBean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class OnClickBean<T extends OnClickBean, P extends SurfaceControllerApi> {

    private transient boolean selected;
    private transient View.OnClickListener onClickListener;

    public OnClickBean(OnBVClickListener<T> listener) {
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}