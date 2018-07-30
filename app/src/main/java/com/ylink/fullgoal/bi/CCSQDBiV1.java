package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBeanV1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class CCSQDBiV1 extends SurfaceBi<CCSQDBiV1, CCSQDBeanV1> {

    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_ccsqd_v1;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull CCSQDBeanV1 bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(startTv, bean.getStart())
                .execute(() -> {
                    if (bean.getColorResId() != null) {
                        api.setTextColor(detailTv, bean.getColorResId());
                    }
                })
                .setEnableOnClickListener(bean.getOnClickListener());
    }

}