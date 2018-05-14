package com.ylink.fullgoal.bean;

import com.ylink.fullgoal.R;

public class TvBean extends ApiBean<TvBean> {

    public TvBean(String name) {
        super(name);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv;
    }

}
