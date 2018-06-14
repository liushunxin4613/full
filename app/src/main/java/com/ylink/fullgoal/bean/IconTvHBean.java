package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class IconTvHBean extends ApiBean<IconTvHBean> {

    public IconTvHBean(String name, OnBVClickListener<IconTvHBean> listener) {
        super(R.mipmap.icon_add, name, listener);
    }

    public IconTvHBean(Integer iconResId, String name, OnBVClickListener<IconTvHBean> listener) {
        super(iconResId, name, listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_icon_tv_h;
    }

}
