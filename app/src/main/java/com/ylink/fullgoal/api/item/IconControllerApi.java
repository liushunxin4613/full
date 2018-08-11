package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.IconNorm;

import butterknife.Bind;

public class IconControllerApi<C> extends OnClickControllerApi<IconControllerApi, C, IconNorm> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;

    public IconControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_icon_bar;
    }

    @Override
    protected void onSafeNorm(@NonNull IconNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setOnClickListener(iconIv, norm.getOnClickListener());
    }

}