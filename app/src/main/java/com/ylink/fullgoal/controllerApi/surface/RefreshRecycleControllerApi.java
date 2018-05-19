package com.ylink.fullgoal.controllerApi.surface;

import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ylink.fullgoal.R;

import butterknife.Bind;

public class RefreshRecycleControllerApi<T extends RefreshRecycleControllerApi, C> extends RecycleControllerApi<T, C> {

    @Bind(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    public RefreshRecycleControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_refresh_recycle;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getActivity().finish();
    }

}