package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHTv3Bean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvHTv3Bi extends BaseApiBi<TvHTv3Bi, TvHTv3Bean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_tv_tv3;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvHTv3Bean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail());
    }

}