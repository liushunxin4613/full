package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.ylink.fullgoal.R;

public class MoneyBean extends ApiBean<MoneyBean> {

    private transient IObjAction<String> action;

    public MoneyBean(String name, String detail) {
        super(name, detail);
        setEtEnable(true);
    }

    public MoneyBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        setEtEnable(false);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(getDetail());
        }
    }

    @Override
    public Integer getApiType() {
        return getEnableLayoutResId(isEtEnable() ? R.layout.l_h_tv_money : R.layout.l_h_money);
    }

}
