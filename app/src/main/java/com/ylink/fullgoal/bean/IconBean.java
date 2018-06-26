package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.IconBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconBean extends ApiBean<IconBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, IconBean> newDefApi() {
        return new IconBi();
    }

    public IconBean(OnBVClickListener<IconBean> listener) {
        super(listener);
    }

}
