package com.ylink.fullgoal.bean;

import android.app.Dialog;
import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvV2DialogBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class TvV2DialogBean extends SurfaceBiBean<TvV2DialogBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvV2DialogBean> newDefApi() {
        return new TvV2DialogBi();
    }

    private String name;
    private String detail;
    private transient Dialog dialog;
    private transient View.OnClickListener onNameClickListener;
    private transient View.OnClickListener onDetailClickListener;
    private transient OnBVDialogClickListener<TvV2DialogBean> onNameBVDialogClickListener;
    private transient OnBVDialogClickListener<TvV2DialogBean> onDetailBVDialogClickListener;

    public TvV2DialogBean(String name, String detail, OnBVDialogClickListener<TvV2DialogBean> onNameBVDialogClickListener,
                          OnBVDialogClickListener<TvV2DialogBean> onDetailBVDialogClickListener) {
        this.name = name;
        this.detail = detail;
        this.onNameBVDialogClickListener = onNameBVDialogClickListener;
        this.onDetailBVDialogClickListener = onDetailBVDialogClickListener;
        init();
    }

    private void init(){
        setOnNameBVDialogClickListener(onNameBVDialogClickListener);
        setOnDetailBVDialogClickListener(onDetailBVDialogClickListener);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
        init();
    }

    public View.OnClickListener getOnNameClickListener() {
        return onNameClickListener;
    }

    public void setOnNameBVDialogClickListener(OnBVDialogClickListener<TvV2DialogBean> listener) {
        this.onNameClickListener = (listener == null || getDialog() == null) ? null : v -> listener.onBVClick(this, v, getDialog());
    }

    public View.OnClickListener getOnDetailClickListener() {
        return onDetailClickListener;
    }

    public void setOnDetailBVDialogClickListener(OnBVDialogClickListener<TvV2DialogBean> listener) {
        this.onDetailClickListener = (listener == null || getDialog() == null) ? null : v -> listener.onBVClick(this, v, getDialog());
    }

}
