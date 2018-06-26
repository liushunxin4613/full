package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class TvBi extends SurfaceBi<TvBi, TvBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_tv;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener());
    }

}