package com.ylink.fullgoal.bean;

import com.ylink.fullgoal.R;

public class TvHEt3Bean extends ApiBean<TvHEt3Bean> {

    public TvHEt3Bean(String name, String detail, String hint) {
        super(name, detail, hint, null);
    }

    @Override
    public Integer getApiType() {
        return getEnable(R.layout.l_tv_et3, R.layout.l_tv_tv3_s);
    }

}
