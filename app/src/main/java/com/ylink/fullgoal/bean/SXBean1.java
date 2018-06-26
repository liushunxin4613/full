package com.ylink.fullgoal.bean;

import android.widget.TextView;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class SXBean1 extends SurfaceBiBean<SXBean1> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, SXBean1> newDefApi() {
        return null;
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_sx_bottom1;
    }

    private transient TextView minTv;
    private transient TextView maxTv;
    private transient TextView textTv;

    public String getMinText() {
        return getText(minTv);
    }

    public void setMinTv(TextView minTv) {
        this.minTv = minTv;
    }

    public String getMaxText() {
        return getText(maxTv);
    }

    public void setMaxTv(TextView maxTv) {
        this.maxTv = maxTv;
    }

    public String getTextText() {
        return getText(textTv);
    }

    public void setTextTv(TextView textTv) {
        this.textTv = textTv;
    }

    private String getText(TextView tv) {
        return tv == null ? null : tv.getText().toString();
    }

}
