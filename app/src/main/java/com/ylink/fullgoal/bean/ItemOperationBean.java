package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class ItemOperationBean extends BaseApiBean<ItemOperationBean> {

    private String name;
    private String detail;
    private transient View.OnClickListener nameOnClickListener;
    private transient View.OnClickListener detailOnClickListener;

    public ItemOperationBean(String name, String detail, OnBVClickListener<ItemOperationBean> nameListener,
                             OnBVClickListener<ItemOperationBean> detailListener) {
        this.name = name;
        this.detail = detail;
        this.nameOnClickListener = getOnBVClickListener(nameListener);
        this.detailOnClickListener = getOnBVClickListener(detailListener);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_item_operation;
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

    public View.OnClickListener getNameOnClickListener() {
        return nameOnClickListener;
    }

    public View.OnClickListener getDetailOnClickListener() {
        return detailOnClickListener;
    }

}
