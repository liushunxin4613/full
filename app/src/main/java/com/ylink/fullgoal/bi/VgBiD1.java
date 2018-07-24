package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.VgBeanD1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.core.BaseVgBi;

import java.util.List;

import butterknife.Bind;

public class VgBiD1 extends BaseVgBi<VgBiD1, VgBeanD1> {

    @Bind(R.id.vg)
    LinearLayout vg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_vg_d1;
    }

    @Override
    protected ViewGroup getVg() {
        return vg;
    }

    @Override
    protected List<BaseBiBean> getData(VgBeanD1 bean) {
        return bean.getLineData();
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull VgBeanD1 bean) {
        super.updateBind(api, bean);
        api.setOnClickListener(bean.getOnClickListener());
    }

}