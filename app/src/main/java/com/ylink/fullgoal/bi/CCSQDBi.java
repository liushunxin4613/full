package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class CCSQDBi extends SurfaceBi<CCSQDBi, CCSQDBean> {

    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.end_tv)
    TextView endTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_ccsqd;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, CCSQDBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd())
                .setEnableOnClickListener(bean.getOnClickListener());
    }

}