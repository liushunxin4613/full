package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.OnBVClickListener;
import com.leo.core.iapi.OnBVLongClickListener;
import com.ylink.fullgoal.R;

public class GridPhotoBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_grid_photo;
    }

    private transient int unit;
    private Object res;
    private Object obj;
    private transient View.OnClickListener onClickListener;
    private transient View.OnLongClickListener onLongClickListener;

    public GridPhotoBean(Object res, Object obj, OnBVClickListener<GridPhotoBean> clickListener
            , OnBVLongClickListener<GridPhotoBean> longClickListener) {
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

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<GridPhotoBean> listener) {
        this.onClickListener = listener == null ? null : v -> listener.onBVClick(this, v);
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return getEnable(onLongClickListener, null);
    }

    public void setOnLongClickListener(OnBVLongClickListener<GridPhotoBean> listener) {
        this.onLongClickListener = listener == null ? null : v -> listener.onBVLongClick(this, v);
    }

    //自有

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public GridPhotoBean setEnable(boolean enable) {
        super.setEnable(enable);
        return this;
    }
}
