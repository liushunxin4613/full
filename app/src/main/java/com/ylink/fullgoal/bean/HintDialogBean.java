package com.ylink.fullgoal.bean;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.HintDialogBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class HintDialogBean extends BaseDialogBean<HintDialogBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, HintDialogBean> newDefApi() {
        return new HintDialogBi();
    }

    private String title;
    private String detail;
    private String confirm;
    private String cancel;
    private transient View.OnClickListener confirmOnClickListener;
    private transient View.OnClickListener cancelOnClickListener;
    private transient OnBVDialogClickListener<HintDialogBean> confirmListener;
    private transient OnBVDialogClickListener<HintDialogBean> cancelListener;

    public HintDialogBean(String title, String detail, String confirm, String cancel,
                          OnBVDialogClickListener<HintDialogBean> confirmListener,
                          OnBVDialogClickListener<HintDialogBean> cancelListener) {
        this.title = title;
        this.detail = detail;
        this.confirm = confirm;
        this.cancel = cancel;
        this.confirmListener = confirmListener;
        this.cancelListener = cancelListener;
        init();
    }

    @Override
    protected void init() {
        super.init();
        setConfirmOnClickListener(getOnClickListener(confirmListener));
        setCancelOnClickListener(getOnClickListener(cancelListener));
    }

    @Override
    public void setDialog(AlertDialog dialog) {
        super.setDialog(dialog);
        executeNon(getDialog(), obj -> obj.setCancelable(false));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public void setConfirmOnClickListener(View.OnClickListener confirmOnClickListener) {
        this.confirmOnClickListener = confirmOnClickListener;
    }

    public void setCancelOnClickListener(View.OnClickListener cancelOnClickListener) {
        this.cancelOnClickListener = cancelOnClickListener;
    }

    public View.OnClickListener getConfirmOnClickListener() {
        return confirmOnClickListener;
    }

    public View.OnClickListener getCancelOnClickListener() {
        return cancelOnClickListener;
    }

}