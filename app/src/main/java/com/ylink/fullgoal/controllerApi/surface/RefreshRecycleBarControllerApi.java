package com.ylink.fullgoal.controllerApi.surface;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ylink.fullgoal.R;

import butterknife.Bind;

public class RefreshRecycleBarControllerApi<T extends RefreshRecycleBarControllerApi, C> extends RecycleBarControllerApi<T, C> {

    @Bind(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    public RefreshRecycleBarControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_bar_refresh_recycle;
    }

}
