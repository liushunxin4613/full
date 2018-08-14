package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.DiaoyanNorm;

import butterknife.Bind;

public class DiaoyanControllerApi<C> extends LineControllerApi<DiaoyanControllerApi, C, DiaoyanNorm> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.vg)
    LinearLayout vg;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.tv5)
    TextView tv5;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    public DiaoyanControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_diaoyan;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull DiaoyanNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, norm.getCode())
                .setText(tv1, norm.getName())
                .setText(tv2, norm.getType())
                .setText(tv3, norm.getState())
                .setText(tv4, checkFormat("%s ~ %s", norm.getStartDate(), norm.getEndDate()))
                .setText(tv5, norm.getText());
    }
    
}
