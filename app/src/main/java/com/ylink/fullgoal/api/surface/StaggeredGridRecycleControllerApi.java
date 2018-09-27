package com.ylink.fullgoal.api.surface;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.manager.HStaggeredGridLayoutManager;
import com.ylink.fullgoal.other.GridSpacing1ItemDecoration;

public class StaggeredGridRecycleControllerApi<T extends StaggeredGridRecycleControllerApi, C> extends RecycleControllerApi<T, C> {

    private int space = 8;

    public StaggeredGridRecycleControllerApi(C controller) {
        super(controller);
    }

    private int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        if (space > 0) {
            this.space = space;
        }
    }

    private int getGridCount() {
        return 3;
    }

    @Override
    public RecyclerView.LayoutManager newLayoutManager() {
        return new HStaggeredGridLayoutManager(getGridCount(), LinearLayout.HORIZONTAL);
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setFocusable(false);
        getRecyclerView().setFocusableInTouchMode(false);
        getRecyclerView().addItemDecoration(new GridSpacing1ItemDecoration(getSpace()));
    }

}
