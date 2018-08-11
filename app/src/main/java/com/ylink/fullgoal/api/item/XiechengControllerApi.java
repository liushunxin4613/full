package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.XiechengNorm;

import butterknife.Bind;

public class XiechengControllerApi<C> extends OnClickControllerApi<XiechengControllerApi, C, XiechengNorm> {

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

    public XiechengControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_xiecheng;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull XiechengNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, checkFormat("航班号  %s", norm.getCode()))
                .setText(tv1, checkFormat("%s - %s", norm.getStartPlace(), norm.getEndPlace()))
                .setText(tv2, checkFormat("乘机人  %s", norm.getUsername()))
                .setText(tv3, checkFormat("%s %s 开", norm.getStartDate(), norm.getStartTime()))
                .setText(tv4, checkFormat("金额      %s", norm.getAmount()))
                .setText(tv5, checkFormat("%s %s 到", norm.getEndDate(), norm.getEndTime()));
    }

}