package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class TvV2DialogBi extends SurfaceBi<TvV2DialogBi, TvV2DialogBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_dialog_photo;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvV2DialogBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .execute(() -> bean.setDialog(api.getDialog()))
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(nameTv, bean.getOnNameClickListener())
                .setOnClickListener(detailTv, bean.getOnDetailClickListener());
    }

}