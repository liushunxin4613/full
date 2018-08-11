package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.IconTvHControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconTvHNorm extends BaseTestNorm<IconTvHNorm>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new IconTvHControllerApi(controller);
    }

    public IconTvHNorm(String name, OnBVClickListener<IconTvHNorm> listener) {
        super(R.mipmap.icon_add, name, listener);
    }

    public IconTvHNorm(Integer iconResId, String name, OnBVClickListener<IconTvHNorm> listener) {
        super(iconResId, name, listener);
    }

}