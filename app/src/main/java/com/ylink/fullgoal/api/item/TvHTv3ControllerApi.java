package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvHTv3Norm;

import butterknife.Bind;

public class TvHTv3ControllerApi<C> extends OnClickControllerApi<TvHTv3ControllerApi, C, TvHTv3Norm>{

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public TvHTv3ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_tv_tv3;
    }

    @Override
    protected void onSafeNorm(@NonNull TvHTv3Norm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail());
    }

}