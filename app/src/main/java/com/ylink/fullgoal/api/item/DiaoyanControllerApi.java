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

    TextView tv1;
    TextView tv0;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    LinearLayout vg;
    ImageView iconIv;
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
    public void initView() {
        super.initView();
        vg = findViewById(R.id.vg);
        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        iconIv = findViewById(R.id.icon_iv);
        rootVg = findViewById(R.id.root_vg);
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
