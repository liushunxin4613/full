package com.ylink.fullgoal.api.surface;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leo.core.util.DisneyUtil;
import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.other.GridSpacingItemDecoration;

public class GridRecycleControllerApi<T extends GridRecycleControllerApi, C> extends SetRecycleControllerApi<T, C> {

    private int space = 10;
    private int edgeSpace = space * 3;
    private int unit;

    public GridRecycleControllerApi(C controller) {
        super(controller);
    }

    private int getGridCount() {
        return 3;
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
        initDis();
        initCallback();
    }

    //监听相关对象
    private void initCallback() {
        //grid
        putBindBeanCallback(GridBean.class, (bean, position) -> {
            getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
            clear();
            execute(bean.getData(), obj -> {
                if(obj instanceof GridPhotoBean){
                    ((GridPhotoBean) obj).setUnit(unit);
                }
                add(obj);
            });
            notifyDataSetChanged();
        });
    }

    private void initDis(){
        int totalWidth = DisneyUtil.getScreenDisplay().getX();
        unit = (totalWidth - space * (getGridCount() - 1) - edgeSpace * 2) / getGridCount();
        getRecyclerView().addItemDecoration(new GridSpacingItemDecoration(space, edgeSpace));
    }

}