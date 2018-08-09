package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvHintNorm;

import butterknife.Bind;

public class TvHintControllerApi<C> extends OnClickControllerApi<TvHintControllerApi, C, TvHintNorm>{

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_tv_hint;
    }

    public TvHintControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected void onSafeNorm(@NonNull TvHintNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setVisibility(detailTv, norm.isShow() ? View.VISIBLE : View.INVISIBLE)
                .setOnClickListener(norm.getOnClickListener());
    }

}