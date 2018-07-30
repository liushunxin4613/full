package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.HintDialogBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class HintDialogBi extends SurfaceBi<HintDialogBi, HintDialogBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.confirm_tv)
    TextView confirmTv;
    @Bind(R.id.cancel_tv)
    TextView cancelTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_hint_dialog;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull HintDialogBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getTitle())
                .setText(detailTv, bean.getDetail())
                .setText(confirmTv, bean.getConfirm())
                .setText(cancelTv, bean.getCancel())
                .execute(() -> bean.setDialog(api.getDialog()))
                .setOnClickListener(confirmTv, bean.getConfirmOnClickListener())
                .setOnClickListener(cancelTv, bean.getCancelOnClickListener())
                .setVisibility(confirmTv, TextUtils.isEmpty(bean.getCancel())
                        ? View.GONE : View.VISIBLE);
    }

}