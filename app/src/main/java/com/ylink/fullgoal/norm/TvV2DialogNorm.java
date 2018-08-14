package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvV2DialogControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvV2DialogNorm extends BaseDialogNorm<TvV2DialogNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return TvV2DialogControllerApi.class;
    }

    private String name;
    private String detail;
    private boolean show;
    private transient View.OnClickListener onNameClickListener;
    private transient View.OnClickListener onDetailClickListener;
    private transient OnBVDialogClickListener<TvV2DialogNorm> onNameBVDialogClickListener;
    private transient OnBVDialogClickListener<TvV2DialogNorm> onDetailBVDialogClickListener;

    public TvV2DialogNorm(String name, String detail, boolean show,
                          OnBVDialogClickListener<TvV2DialogNorm> onNameBVDialogClickListener,
                          OnBVDialogClickListener<TvV2DialogNorm> onDetailBVDialogClickListener) {
        this.name = name;
        this.detail = detail;
        this.show = show;
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

    public boolean isShow() {
        return show;
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