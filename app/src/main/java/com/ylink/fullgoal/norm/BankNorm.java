package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.BankControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class BankNorm extends OnClickNorm<BankNorm, SurfaceControllerApi>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new BankControllerApi(controller);
    }

    private String name;
    private String detail;

    public BankNorm(String name, String detail, OnBVClickListener<BankNorm> listener) {
        super(listener);
        this.name = name;
        this.detail = detail;
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

}