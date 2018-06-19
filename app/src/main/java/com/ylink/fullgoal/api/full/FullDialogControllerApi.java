package com.ylink.fullgoal.api.full;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.HintDialogBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class FullDialogControllerApi<T extends FullDialogControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.confirm_tv)
    TextView confirmTv;
    @Bind(R.id.cancel_tv)
    TextView cancelTv;

    public FullDialogControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_hint_dialog;
    }

    @Override
    public void initView() {
        super.initView();
        //dialog
        putBindBeanApi(HintDialogBean.class, (api, bean)
                -> api.setText(nameTv, bean.getTitle())
                .setText(detailTv, bean.getDetail())
                .setText(confirmTv, bean.getConfirm())
                .setText(cancelTv, bean.getCancel())
                .setOnClickListener(confirmTv, bean.getConfirmOnClickListener())
                .setOnClickListener(cancelTv, bean.getCancelOnClickListener()));
    }

}
