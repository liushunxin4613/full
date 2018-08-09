package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.TvV2DialogNorm;

import butterknife.Bind;

public class TvV2DialogControllerApi<C> extends NormControllerApi<TvV2DialogControllerApi, C, TvV2DialogNorm> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.line_view)
    View lineView;

    public TvV2DialogControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_dialog_photo;
    }

    @Override
    protected void onSafeNorm(@NonNull TvV2DialogNorm norm, int position) {
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .execute(() -> norm.setDialog(getDialog()))
                .setVisibility(nameTv, norm.isShow() ? View.VISIBLE : View.GONE)
                .setVisibility(lineView, norm.isShow() ? View.VISIBLE : View.GONE)
                .setOnClickListener(nameTv, norm.getOnNameClickListener())
                .setOnClickListener(detailTv, norm.getOnDetailClickListener());
    }

}