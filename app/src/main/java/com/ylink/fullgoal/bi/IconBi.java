package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IconBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class IconBi extends SurfaceBi<IconBi, IconBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_icon_bar;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull IconBean bean) {
        super.updateBind(api, bean);
        api.setOnClickListener(iconIv, bean.getOnClickListener());
    }

}