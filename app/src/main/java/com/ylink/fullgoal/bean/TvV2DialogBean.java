package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvV2DialogBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_dialog_photo;
    }

    private String name;
    private String detail;
    private transient View.OnClickListener onNameClickListener;
    private transient View.OnClickListener onDetailClickListener;

    public TvV2DialogBean(String name, String detail, OnBVClickListener<TvV2DialogBean> onNameClickListener,
                          OnBVClickListener<TvV2DialogBean> onDetailClickListener) {
        this.name = name;
        this.detail = detail;
        setOnNameClickListener(onNameClickListener);
        setOnDetailClickListener(onDetailClickListener);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public View.OnClickListener getOnNameClickListener() {
        return onNameClickListener;
    }

    public void setOnNameClickListener(OnBVClickListener<TvV2DialogBean> listener) {
        this.onNameClickListener = listener == null ? null : v -> listener.onBVClick(this, v);
    }

    public View.OnClickListener getOnDetailClickListener() {
        return onDetailClickListener;
    }

    public void setOnDetailClickListener(OnBVClickListener<TvV2DialogBean> listener) {
        this.onDetailClickListener = listener == null ? null : v -> listener.onBVClick(this, v);
    }
}
