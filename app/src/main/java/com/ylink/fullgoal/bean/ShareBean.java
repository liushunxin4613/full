package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.DepartmentBi;
import com.ylink.fullgoal.bi.ShareBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ShareBean extends OnClickBean<ShareBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ShareBean> newDefApi() {
        return new ShareBi();
    }

    private String share;

    public ShareBean(String share, OnBVClickListener<ShareBean> listener) {
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