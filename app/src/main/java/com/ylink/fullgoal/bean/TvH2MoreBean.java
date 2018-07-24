package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvH2MoreBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2MoreBean extends ApiBean<TvH2MoreBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvH2MoreBean> newDefApi() {
        return new TvH2MoreBi();
    }

    private transient View.OnClickListener iconOnClickListener;

    public TvH2MoreBean(String name, String detail) {
        super(name, detail);
    }

    public TvH2MoreBean(String name, String detail, String hint,
                        OnBVClickListener<TvH2MoreBean> listener,
                        OnBVClickListener<TvH2MoreBean> iconListener) {
        super(name, detail, hint, listener);
        this.iconOnClickListener = getOnBVClickListener(iconListener == null ? null : (bean, view) -> {
            setDetail(null);
            executeNon(api(), IBindControllerApi::updateBind);
            iconListener.onBVClick(bean, view);
        });
    }

    public View.OnClickListener getIconOnClickListener() {
        return iconOnClickListener;
    }

}