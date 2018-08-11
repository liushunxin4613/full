package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.IconTvHNorm;

import butterknife.Bind;

public class IconTvHControllerApi<C> extends OnClickControllerApi<IconTvHControllerApi, C, IconTvHNorm>{

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;

    public IconTvHControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_icon_tv_h;
    }

    @Override
    protected void onSafeNorm(@NonNull IconTvHNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setImage(iconIv, norm.getIconResId())
                .setText(nameTv, norm.getName())
                .setOnClickListener(norm.getOnClickListener());
    }

}