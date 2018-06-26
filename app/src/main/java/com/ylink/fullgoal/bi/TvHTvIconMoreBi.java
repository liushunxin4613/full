package com.ylink.fullgoal.bi;

import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHTvIconMoreBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvHTvIconMoreBi extends BaseApiBi<TvHTvIconMoreBi, TvHTvIconMoreBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_tv_tv_more;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvHTvIconMoreBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setImage(iconIv, bean.getIconResId())
                .setIcon(iconIv, !TextUtils.isEmpty(bean.getIconResId()))
                .setOnClickListener(bean.getOnClickListener())
                .setText(detailTv, bean.getDetail());
    }

}