package com.ylink.fullgoal.bean;

import com.ylink.fullgoal.R;

public class TvH2Bean extends ApiBean<TvH2Bean>{

    public TvH2Bean(String name, String detail) {
        super(name, detail);
    }

    @Override
    public Integer getApiType() {
        return getEnableLayoutResId(R.layout.l_h_tv2);
    }

}
