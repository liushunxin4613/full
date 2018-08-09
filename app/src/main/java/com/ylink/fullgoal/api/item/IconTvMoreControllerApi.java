package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.IconTvMoreNorm;

import butterknife.Bind;

public class IconTvMoreControllerApi<C> extends OnClickControllerApi<IconTvMoreControllerApi, C, IconTvMoreNorm> {

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
    public ViewGroup onCreateViewGroup(ViewGroup group, @NonNull View rootView) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(rootView);
        LayoutInflater.from(getContext()).inflate(
                R.layout.v_line2, layout);
        return layout;
    }

    @Override
    protected void onSafeNorm(@NonNull IconTvMoreNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setImage(iconIv, norm.getIconResId())
                .setText(nameTv, norm.getName());
    }

}