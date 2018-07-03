package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.MoneyBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class MoneyBean extends ApiBean<MoneyBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, MoneyBean> newDefApi() {
        return new MoneyBi();
    }

    private transient IObjAction<String> action;

    public MoneyBean(String name, String detail) {
        super(name, detail);
        setEtEnable(true);
        setMoneyEnable(false);
    }

    public MoneyBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        setEtEnable(false);
        setMoneyEnable(true);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(getDetail());
        }
    }

}