package com.ylink.fullgoal.bean;

import android.view.View;
import android.widget.TextView;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.OnBVClickListener;
import com.ylink.fullgoal.R;

public abstract class ApiBean<T extends ApiBean> extends BaseApiBean {

    //不做引入处理
    private transient View.OnClickListener onClickListener;
    private transient TextView textView;

    private transient Integer iconResId;
    private String name;
    private String detail;
    private String hint;

    public ApiBean(String name) {
        this(null, name, null, null, null);
    }

    public ApiBean(OnBVClickListener<T> listener) {
        this(null, null, null, null, listener);
    }

    public ApiBean(String name, String detail) {
        this(null, name, detail, null, null);
    }

    public ApiBean(String name, String detail, OnBVClickListener<T> listener) {
        this(null, name, detail, null, listener);
    }

    public ApiBean(Integer iconResId, String name, OnBVClickListener<T> listener) {
        this(iconResId, name, null, null, listener);
    }

    public ApiBean(String name, String detail, String hint, OnBVClickListener<T> listener) {
        this(null, name, detail, hint, listener);
    }

    public ApiBean(String name, String detail, String hint) {
        this(name, detail, hint, null);
    }

    public ApiBean(Integer iconResId, OnBVClickListener<T> listener) {
        this(iconResId, null, null, null, listener);
    }

    public ApiBean(Integer iconResId, String name, String detail, String hint, OnBVClickListener<T> listener) {
        this.iconResId = iconResId;
        this.name = name;
        this.detail = detail;
        this.hint = hint;
        setOnBVClickListener(listener);
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setOnBVClickListener(OnBVClickListener<T> listener) {
        this.onClickListener = listener == null ? null : v -> listener.onBVClick((T) this, v);
    }

    public Integer getIconResId() {
        return iconResId;
    }

    public void setIconResId(Integer iconResId) {
        this.iconResId = iconResId;
    }

    public String getText() {
        return textView == null ? null : textView.getText().toString();
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    protected Integer getEnableLayoutResId(Integer resId) {
        return isEnable() ? resId : R.layout.l_h_tv2_more_s;
    }

    public View.OnClickListener getOnClickListener() {
        return getEnable(onClickListener, null);
    }

}
