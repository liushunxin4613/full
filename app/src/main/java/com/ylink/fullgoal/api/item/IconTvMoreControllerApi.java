package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.IconTvMoreNorm;

import butterknife.Bind;

public class IconTvMoreControllerApi<T extends IconTvMoreControllerApi, C> extends NormControllerApi<T, C, IconTvMoreNorm> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;

    public IconTvMoreControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_icon_tv_more;
    }

    @Override
    protected void onSafeNorm(@NonNull IconTvMoreNorm norm, int position) {
        setImage(iconIv, norm.getIconResId())
                .setText(nameTv, norm.getName())
                .setOnClickListener(norm.getOnClickListener());
    }

}