package com.ylink.fullgoal.bi;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.VgBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.core.BaseVgBi;

import java.util.List;

import butterknife.Bind;

public class VgBi extends BaseVgBi<VgBi, VgBean> {

    @Bind(R.id.vg)
    LinearLayout vg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_vg;
    }

    @Override
    protected ViewGroup getVg() {
        return vg;
    }

    @Override
    protected List<BaseBiBean> getData(VgBean bean) {
        return bean.getLineData();
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, VgBean bean) {
        super.onBindApi(api, bean);
        api.setOnClickListener(bean.getOnClickListener());
    }

}