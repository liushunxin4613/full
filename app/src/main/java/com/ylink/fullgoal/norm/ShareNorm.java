package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.ShareControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ShareNorm extends OnClickNorm<ShareNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ShareControllerApi.class;
    }

    private String share;

    public ShareNorm(String share, OnBVClickListener<ShareNorm> listener) {
        super(listener);
        this.share = share;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

}