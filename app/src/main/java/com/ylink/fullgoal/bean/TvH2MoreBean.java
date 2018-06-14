package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvH2MoreBean extends ApiBean<TvH2MoreBean> {

    public TvH2MoreBean(String name, String detail) {
        super(name, detail);
    }

    public TvH2MoreBean(String name, String detail, String hint, OnBVClickListener<TvH2MoreBean> listener) {
        super(name, detail, hint, listener);
    }

    @Override
    public Integer getApiType() {
        return getEnableLayoutResId(R.layout.l_h_tv2_more);
    }

}
