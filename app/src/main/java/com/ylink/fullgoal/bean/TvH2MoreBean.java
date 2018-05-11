package com.ylink.fullgoal.bean;

import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvH2MoreBean extends ApiBean<TvH2MoreBean> {

    public TvH2MoreBean(String name, String detail, OnBVClickListener<TvH2MoreBean> listener) {
        super(name, detail, listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_h_tv2_more;
    }

}
