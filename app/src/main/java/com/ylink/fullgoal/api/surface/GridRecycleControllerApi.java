package com.ylink.fullgoal.api.surface;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.other.GridSpacingItemDecoration;

import java.util.List;

public class GridRecycleControllerApi<T extends GridRecycleControllerApi, C> extends RecycleControllerApi<T, C> {

    private int space = 10;
    private int edgeSpace = space * 3;
    private int unit;

    public GridRecycleControllerApi(C controller) {
        super(controller);
    }

    private int getGridCount() {
        return 3;
    }

    public int getUnit() {
        return unit;
    }

    @Override
    public RecyclerView.LayoutManager newLayoutManager() {
        return new GridLayoutManager(getContext(), getGridCount());
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setFocusable(false);
        getRecyclerView().setFocusableInTouchMode(false);
        initDes();
    }

    private void initDes() {
        int totalWidth = DisneyUtil.getScreenDisplay().getX();
        unit = (totalWidth - space * (getGridCount() - 1) - edgeSpace * 2) / getGridCount();
        getRecyclerView().addItemDecoration(new GridSpacingItemDecoration(space, edgeSpace));
    }

    public void replaceAll(List<INorm> data, IObjAction<INorm> action) {
        getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
        clear();
        execute(data, item -> {
            if (action != null) {
                action.execute(item);
            }
            add(item);
        });
        notifyDataSetChanged();
    }

}