package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.TvH2MoreControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2MoreNorm extends BaseTestNorm<TvH2MoreNorm>{

    @Override
    public Integer getApiType() {
        if(!isEnable()){
            return R.layout.l_h_tv2;
        }
        return super.getApiType();
    }

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvH2MoreControllerApi(controller);
    }
    
    private transient View.OnClickListener iconOnClickListener;

    public TvH2MoreNorm(String name, String detail) {
        super(name, detail);
    }

    public TvH2MoreNorm(String name, String detail, String hint,
                        OnBVClickListener<TvH2MoreNorm> listener,
                        OnBVClickListener<TvH2MoreNorm> iconListener) {
        super(name, detail, hint, listener);
        this.iconOnClickListener = getOnBVClickListener(iconListener == null ? null : (bean, view) -> {
            setDetail(null);
            controllerApi().onNorm(getThis(), getPosition());
            iconListener.onBVClick(bean, view);
        });
    }

    public View.OnClickListener getIconOnClickListener() {
        return iconOnClickListener;
    }

}
