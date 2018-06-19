package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class HintDialogBean extends BaseApiBean<HintDialogBean> {

    @Override
    public Integer getApiType() {
        return R.layout.l_hint_dialog;
    }

    private String title;
    private String detail;
    private String confirm;
    private String cancel;
    private transient View.OnClickListener confirmOnClickListener;
    private transient View.OnClickListener cancelOnClickListener;

    public HintDialogBean(String title, String detail, String confirm, String cancel,
                          OnBVClickListener<HintDialogBean> confirmListener,
                          OnBVClickListener<HintDialogBean> cancelListener) {
        this.title = title;
        this.detail = detail;
        this.confirm = confirm;
        this.cancel = cancel;
        setConfirmOnClickListener(confirmListener);
        setCancelOnClickListener(cancelListener);
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

    public View.OnClickListener getConfirmOnClickListener() {
        return confirmOnClickListener;
    }

    public void setConfirmOnClickListener(OnBVClickListener<HintDialogBean> listener) {
        this.confirmOnClickListener = getOnBVClickListener(listener);
    }

    public View.OnClickListener getCancelOnClickListener() {
        return cancelOnClickListener;
    }

    public void setCancelOnClickListener(OnBVClickListener<HintDialogBean> listener) {
        this.cancelOnClickListener = getOnBVClickListener(listener);
    }

}
