package com.ylink.fullgoal.bean;

import com.ylink.fullgoal.R;

public class TvHTv3Bean extends ApiBean<TvHTv3Bean> {

    public TvHTv3Bean(String name, String detail) {
        super(name, detail);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv_tv3;
    }

}
