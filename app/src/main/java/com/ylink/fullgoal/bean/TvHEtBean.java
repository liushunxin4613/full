package com.ylink.fullgoal.bean;

import com.ylink.fullgoal.R;

public class TvHEtBean extends ApiBean<TvHEtBean> {

    public TvHEtBean(String name, String detail, String hint) {
        super(name, detail, hint);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv_h_et;
    }

}
