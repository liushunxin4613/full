package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.ZLoadingView;

import butterknife.Bind;

public class LoadingDialogControllerApi<T extends LoadingDialogControllerApi, C> extends SurfaceControllerApi<T, C> {

    @Bind(R.id.loading)
    ZLoadingView loading;

    public LoadingDialogControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_loading_dialog;
    }

    @Override
    public void initView() {
        super.initView();
        executeNon(getDialog(), dialog -> dialog.setCancelable(false));
    }

}