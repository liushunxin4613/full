package com.ylink.fullgoal.bi;

import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconTvMoreBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class IconTvMoreBi extends BaseApiBi<IconTvMoreBi, IconTvMoreBean> {

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_icon_tv_more;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, IconTvMoreBean bean) {
        super.onBindApi(api, bean);
        ImageView iconIv = (ImageView) api.findViewById(R.id.icon_iv);
        TextView nameTv = (TextView) api.findViewById(R.id.name_tv);
        api.setImage(iconIv, bean.getIconResId())
                .setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener());
    }

}