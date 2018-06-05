package com.ylink.fullgoal.bean;

import com.leo.core.iapi.IObjAction;
import com.ylink.fullgoal.R;

public class TvHEt3Bean extends ApiBean<TvHEt3Bean> {

    private transient IObjAction<String> action;

    public TvHEt3Bean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint, null);
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
        return getEnable(R.layout.l_tv_et3, R.layout.l_tv_tv3_s);
    }

}
