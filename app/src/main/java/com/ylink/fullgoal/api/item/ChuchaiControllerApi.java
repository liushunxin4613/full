package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.ChuchaiNorm;

import butterknife.Bind;

public class ChuchaiControllerApi<C> extends LineControllerApi<ChuchaiControllerApi, C, ChuchaiNorm>{

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
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    public ChuchaiControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_chuchai;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull ChuchaiNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, norm.getCode())
                .setText(tv1, checkFormat("预支金额  %s", norm.getAmount()))
                .setText(tv2, format("%s(%s天)", norm.getPlace(), norm.getDay()))
                .setText(tv3, format("%s  ~  %s", norm.getStartDate(), norm.getEndDate()))
                .setText(tv4, String.format("工作内容  %s", no(norm.getText())));
    }
}
