package com.ylink.fullgoal.api.item;

import android.annotation.SuppressLint;

import com.leo.core.iapi.core.INorm;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.GridRecycleControllerApi;
import com.ylink.fullgoal.norm.GridNorm;
import com.ylink.fullgoal.norm.GridPhotoNorm;

public class GridControllerApi<T extends GridControllerApi, C> extends GridRecycleControllerApi<T, C> {

    public GridControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_grid;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onNorm(INorm norm, int position) {
        super.onNorm(norm, position);
        if(norm instanceof GridNorm){
            GridNorm no = (GridNorm) norm;
            replaceAll(no.getData(), obj -> {
                if (obj instanceof GridPhotoNorm) {
                    ((GridPhotoNorm) obj).setUnit(getUnit());
                }
            });
        }
    }

}