package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.IconControllerApi;

public class IconNorm extends BaseTestNorm<IconNorm>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return IconControllerApi.class;
    }

    public IconNorm(OnBVClickListener<IconNorm> listener) {
        super(listener);
    }

}