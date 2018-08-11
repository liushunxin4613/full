package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.CCSQDNorm;

import butterknife.Bind;

public class CCSQDControllerApi<C> extends OnClickControllerApi<CCSQDControllerApi, C, CCSQDNorm> {

    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.end_tv)
    TextView endTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public CCSQDControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_ccsqd;
    }

    @Override
    protected void onSafeNorm(@NonNull CCSQDNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .setText(startTv, norm.getStart())
                .setText(endTv, norm.getEnd())
                .setEnableOnClickListener(norm.getOnClickListener());
    }

}