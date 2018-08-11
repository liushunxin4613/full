package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvH2Norm;

import butterknife.Bind;

public class TvH2ControllerApi<C> extends OnClickControllerApi<TvH2ControllerApi, C, TvH2Norm>{

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public TvH2ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_h_tv2;
    }

    @Override
    protected void onSafeNorm(@NonNull TvH2Norm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .execute(() -> norm.setTextView(detailTv));
    }

}