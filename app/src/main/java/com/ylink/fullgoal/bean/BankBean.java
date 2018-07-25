package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.FormatUtil;
import com.ylink.fullgoal.bi.BankBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;
import com.ylink.fullgoal.factory.BankFactory;

public class BankBean extends SurfaceBiBean<BankBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, BankBean> newDefApi() {
        return new BankBi();
    }

    private String name;
    private String detail;
    private String bankNo;
    private transient boolean selected;
    private transient View.OnClickListener onClickListener;

    public BankBean(String bankNo, boolean selected, OnBVClickListener<BankBean> listener) {
        this.bankNo = bankNo;
        this.selected = selected;
        this.name = BankFactory.getInstance().findBank(bankNo);
        this.detail = FormatUtil.toBankNo(bankNo);
        this.onClickListener = getOnBVClickListener(listener);
    }

    public boolean isSelected() {
        return selected;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

}