package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.IconTvMoreControllerApi;

public class IconTvMoreNorm extends BaseTestNorm<IconTvMoreNorm> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return IconTvMoreControllerApi.class;
    }

    public IconTvMoreNorm(Integer iconResId, String name, OnBVClickListener<IconTvMoreNorm> listener) {
        super(iconResId, name, listener);
    }

}