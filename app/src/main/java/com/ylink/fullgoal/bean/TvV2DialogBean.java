package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvV2DialogBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvV2DialogBean extends BaseDialogBean<TvV2DialogBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvV2DialogBean> newDefApi() {
        return new TvV2DialogBi();
    }

    private String name;
    private String detail;
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

    @Override
    protected void init() {
        super.init();
        setOnNameClickListener(getOnClickListener(onNameBVDialogClickListener));
        setOnDetailClickListener(getOnClickListener(onDetailBVDialogClickListener));
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

    public View.OnClickListener getOnNameClickListener() {
        return onNameClickListener;
    }

    public void setOnNameClickListener(View.OnClickListener onNameClickListener) {
        this.onNameClickListener = onNameClickListener;
    }

    public View.OnClickListener getOnDetailClickListener() {
        return onDetailClickListener;
    }

    public void setOnDetailClickListener(View.OnClickListener onDetailClickListener) {
        this.onDetailClickListener = onDetailClickListener;
    }

}