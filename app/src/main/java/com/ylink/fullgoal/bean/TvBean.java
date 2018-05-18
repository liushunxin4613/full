package com.ylink.fullgoal.bean;

import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvBean extends ApiBean<TvBean> {

    public TvBean(String name) {
        super(name);
    }

    public TvBean(String name, OnBVClickListener<TvBean> listener) {
        super(name, listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv;
    }

}
