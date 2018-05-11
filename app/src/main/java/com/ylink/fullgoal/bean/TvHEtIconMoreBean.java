package com.ylink.fullgoal.bean;

import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvHEtIconMoreBean extends ApiBean<TvHEtIconMoreBean> {

    public TvHEtIconMoreBean(Integer iconResId, String name, String detail, String hint, OnBVClickListener<TvHEtIconMoreBean> listener) {
        super(iconResId, name, detail, hint, listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_h_tv_et_more;
    }

}
