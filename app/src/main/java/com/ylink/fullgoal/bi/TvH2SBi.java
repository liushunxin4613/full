package com.ylink.fullgoal.bi;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvH2SBi extends BaseApiBi<TvH2SBi, TvH2SBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_tv2_more_s;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindApi(SurfaceControllerApi api, TvH2SBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> {
                    int resId = bean.isSelected() ? R.color.EE4433 : R.color.tv;
                    api.setTextColor(nameTv, resId);
                    api.setTextColor(detailTv, resId);
                })
                .setOnClickListener(bean.getOnClickListener());
    }

}