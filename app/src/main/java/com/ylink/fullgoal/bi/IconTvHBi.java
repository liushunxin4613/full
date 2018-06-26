package com.ylink.fullgoal.bi;

import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class IconTvHBi extends BaseApiBi<IconTvHBi, IconTvHBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_icon_tv_h;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, IconTvHBean bean) {
        super.onBindApi(api, bean);
        api.setImage(iconIv, bean.getIconResId())
                .setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener());
    }

}