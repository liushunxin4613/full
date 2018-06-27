package com.ylink.fullgoal.bean;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class BaseDialogBean<T extends BaseDialogBean> extends SurfaceBiBean<T> {

    private transient AlertDialog dialog;

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
        init();
    }

    protected void init(){
    }

    protected View.OnClickListener getOnClickListener(OnBVDialogClickListener<T> listener) {
        return (listener == null || getDialog() == null) ? null : v -> listener.onBVClick(getThis(), v, getDialog());
    }

}