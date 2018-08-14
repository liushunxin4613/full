package com.ylink.fullgoal.norm;

import android.support.v7.app.AlertDialog;
import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.HintDialogControllerApi;
import com.ylink.fullgoal.api.item.VgControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class HintDialogNorm extends BaseDialogNorm<HintDialogNorm, SurfaceControllerApi> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return HintDialogControllerApi.class;
    }

    private String title;
    private String detail;
    private String confirm;
    private String cancel;
    private transient View.OnClickListener confirmOnClickListener;
    private transient View.OnClickListener cancelOnClickListener;
    private transient OnBVDialogClickListener<HintDialogNorm> confirmListener;
    private transient OnBVDialogClickListener<HintDialogNorm> cancelListener;

    public HintDialogNorm(String title, String detail, String confirm, String cancel,
                          OnBVDialogClickListener<HintDialogNorm> confirmListener,
                          OnBVDialogClickListener<HintDialogNorm> cancelListener) {
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

    private void setConfirmOnClickListener(View.OnClickListener confirmOnClickListener) {
        this.confirmOnClickListener = confirmOnClickListener;
    }

    private void setCancelOnClickListener(View.OnClickListener cancelOnClickListener) {
        this.cancelOnClickListener = cancelOnClickListener;
    }

    public View.OnClickListener getConfirmOnClickListener() {
        return confirmOnClickListener;
    }

    public View.OnClickListener getCancelOnClickListener() {
        return cancelOnClickListener;
    }

}