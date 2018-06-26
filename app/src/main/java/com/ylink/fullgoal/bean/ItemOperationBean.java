package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.ItemOperationBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class ItemOperationBean extends SurfaceBiBean<ItemOperationBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ItemOperationBean> newDefApi() {
        return new ItemOperationBi();
    }

    private String name;
    private String detail;
    private transient View.OnClickListener nameOnClickListener;
    private transient View.OnClickListener detailOnClickListener;

    public ItemOperationBean(String name, String detail, OnBVClickListener<ItemOperationBean>
            nameListener, OnBVClickListener<ItemOperationBean> detailListener) {
        this.name = name;
        this.detail = detail;
        this.nameOnClickListener = getOnBVClickListener(nameListener);
        this.detailOnClickListener = getOnBVClickListener(detailListener);
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

    public View.OnClickListener getNameOnClickListener() {
        return nameOnClickListener;
    }

    public View.OnClickListener getDetailOnClickListener() {
        return detailOnClickListener;
    }

}
