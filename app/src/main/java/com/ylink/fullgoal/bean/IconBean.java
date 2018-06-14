package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class IconBean extends ApiBean<IconBean> {

    public IconBean(OnBVClickListener<IconBean> listener) {
        super(listener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_icon_bar;
    }

}
