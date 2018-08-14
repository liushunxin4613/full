package com.ylink.fullgoal.bean;

import android.widget.EditText;

import com.leo.core.iapi.inter.IABAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.MoneyBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import static com.leo.core.util.TextUtils.getMoneyString;

public class MoneyBean extends ApiBean<MoneyBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, MoneyBean> newDefApi() {
        return new MoneyBi();
    }

    private transient double max;
    private transient IABAction<MoneyBean, String> action;

    public MoneyBean(String name, String detail) {
        super(name, detail);
        setEtEnable(true);
        setMoneyEnable(false);
    }

    public MoneyBean(String name, String detail, double max, IABAction<MoneyBean, String> action) {
        super(name, detail);
        setMax(max);
        setEtEnable(false);
        setMoneyEnable(true);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(this, getDetail());
        }
    }

    @Override
    public MoneyBean setMax(Double max) {
        setHint(String.format("最多可分摊%s", getMoneyString(max)));
        if (getTextView() instanceof EditText) {
            getTextView().setHint(getHint());
        }
        return super.setMax(max);
    }

}