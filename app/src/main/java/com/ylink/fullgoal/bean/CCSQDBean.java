package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

/**
 * 出差申请单
 */
public class CCSQDBean extends BaseApiBean {

    private transient View.OnClickListener onClickListener;

    @Override
    public Integer getApiType() {
        return R.layout.l_ccsqd;
    }

    private String name;
    private String detail;
    private String start;
    private String end;

    public CCSQDBean(String name, String detail, String start, String end) {
        this(name, detail, start, end, null);
    }

    public CCSQDBean(String name, String detail, String start, String end, OnBVClickListener<CCSQDBean> listener) {
        this.name = name;
        this.detail = detail;
        this.start = start;
        this.end = end;
        this.setOnBVClickListener(listener);
    }

    public void setOnBVClickListener(OnBVClickListener<CCSQDBean> listener) {
        this.onClickListener = listener == null ? null : v -> listener.onBVClick(this, v);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
