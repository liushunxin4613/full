package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
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
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvHTv3Bean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail());
    }

}