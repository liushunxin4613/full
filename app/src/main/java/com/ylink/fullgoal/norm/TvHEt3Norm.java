package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.TvHEt3ControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHEt3Norm extends BaseTestNorm<TvHEt3Norm>{

    @Override
    public Integer getApiType() {
        if(isEnable()){
            return R.layout.l_tv_et3;
        }
        return R.layout.l_tv_tv3_s;
    }

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvHEt3ControllerApi(controller);
    }

    private transient IObjAction<String> action;

    public TvHEt3Norm(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint, null);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(getDetail());
        }
    }

}