package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class IconTvMoreBean extends ApiBean<IconTvMoreBean> {

    public IconTvMoreBean(Integer iconResId, String name, OnBVClickListener<IconTvMoreBean> listener) {
        super(iconResId, name, listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_icon_tv_more;
    }

    @Override
    public boolean isEnable() {
        return true;
    }

}
