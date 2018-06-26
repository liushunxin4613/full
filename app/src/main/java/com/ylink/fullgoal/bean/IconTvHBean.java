package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bi.IconTvHBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconTvHBean extends ApiBean<IconTvHBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, IconTvHBean> newDefApi() {
        return new IconTvHBi();
    }

    public IconTvHBean(String name, OnBVClickListener<IconTvHBean> listener) {
        super(R.mipmap.icon_add, name, listener);
    }

    public IconTvHBean(Integer iconResId, String name, OnBVClickListener<IconTvHBean> listener) {
        super(iconResId, name, listener);
    }

}