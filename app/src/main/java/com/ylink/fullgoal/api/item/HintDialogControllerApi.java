package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.HintDialogNorm;

import butterknife.Bind;

public class HintDialogControllerApi<C> extends NormControllerApi<HintDialogControllerApi, C, HintDialogNorm> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.confirm_tv)
    TextView confirmTv;
    @Bind(R.id.cancel_tv)
    TextView cancelTv;

    public HintDialogControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_hint_dialog;
    }

    @Override
    protected void onSafeNorm(@NonNull HintDialogNorm norm, int position) {
        setText(nameTv, norm.getTitle())
                .setText(detailTv, norm.getDetail())
                .setText(confirmTv, norm.getConfirm())
                .setText(cancelTv, norm.getCancel())
                .execute(() -> norm.setDialog(getDialog()))
                .setOnClickListener(confirmTv, norm.getConfirmOnClickListener())
                .setOnClickListener(cancelTv, norm.getCancelOnClickListener())
                .setVisibility(cancelTv, TextUtils.isEmpty(norm.getCancel())
                        ? View.GONE : View.VISIBLE);
    }

}