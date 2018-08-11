package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.inter.OnBVLongClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.GridPhotoControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public class GridPhotoNorm extends SurfaceNorm<GridPhotoNorm, SurfaceControllerApi>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new GridPhotoControllerApi(controller);
    }

    private Object res;
    private Object obj;
    private transient int unit;
    private transient boolean visible;
    private transient View.OnLongClickListener onLongClickListener;

    public GridPhotoNorm(Object res, Object obj, OnBVClickListener<GridPhotoNorm> clickListener
            , OnBVLongClickListener<GridPhotoNorm> longClickListener) {
        this.res = res;
        this.obj = obj;
        setOnClickListener(clickListener);
        setOnLongClickListener(longClickListener);
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return getEnable(onLongClickListener);
    }

    private void setOnLongClickListener(OnBVLongClickListener<GridPhotoNorm> listener) {
        this.onLongClickListener = listener == null ? null : v -> listener.onBVLongClick(this, v);
    }

    //自有

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public boolean isVisible() {
        return visible;
    }

    public GridPhotoNorm setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

}