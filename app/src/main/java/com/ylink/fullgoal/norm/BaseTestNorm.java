package com.ylink.fullgoal.norm;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.util.HelperUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public abstract class BaseTestNorm<T extends BaseTestNorm> extends SurfaceNorm<T, SurfaceControllerApi> {

    private String name;
    private String detail;
    private String hint;
    private transient Double max;
    private transient boolean etEnable;
    private transient TextView textView;
    private transient Integer iconResId;
    private transient boolean moneyEnable;
    private transient boolean isMoneyInputType;

    public BaseTestNorm(String name) {
        this(null, name, null, null, null);
    }

    public BaseTestNorm(String name, OnBVClickListener<T> listener) {
        this(null, name, null, null, listener);
    }

    public BaseTestNorm(OnBVClickListener<T> listener) {
        this(null, null, null, null, listener);
    }

    public BaseTestNorm(String name, String detail) {
        this(null, name, detail, null, null);
    }

    public BaseTestNorm(String name, String detail, OnBVClickListener<T> listener) {
        this(null, name, detail, null, listener);
    }

    public BaseTestNorm(Integer iconResId, String name, String detail) {
        this(iconResId, name, detail, null, null);
    }

    public BaseTestNorm(Integer iconResId, String name, OnBVClickListener<T> listener) {
        this(iconResId, name, null, null, listener);
    }

    public BaseTestNorm(String name, String detail, String hint, OnBVClickListener<T> listener) {
        this(null, name, detail, hint, listener);
    }

    public BaseTestNorm(String name, String detail, String hint) {
        this(name, detail, hint, null);
    }

    public BaseTestNorm(Integer iconResId, OnBVClickListener<T> listener) {
        this(iconResId, null, null, null, listener);
    }

    public BaseTestNorm(Integer iconResId, String name, String detail, String hint,
                        OnBVClickListener<T> listener) {
        this.iconResId = iconResId;
        this.name = name;
        this.detail = detail;
        this.hint = hint;
        this.setOnClickListener(listener);
    }

    public boolean isEtEnable() {
        return etEnable;
    }

    public void setEtEnable(boolean etEnable) {
        this.etEnable = etEnable;
    }

    public boolean isMoneyEnable() {
        return moneyEnable;
    }

    public void setMoneyEnable(boolean moneyEnable) {
        this.moneyEnable = moneyEnable;
    }

    public void setB5(boolean b5) {
        this.max = b5 ? 100d : null;
    }

    public T setMax(Double max) {
        this.max = max;
        return (T) this;
    }

    public Double getMax() {
        return max;
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

    public Integer getIconResId() {
        return iconResId;
    }

    public void setIconResId(Integer iconResId) {
        this.iconResId = iconResId;
    }

    public String getText() {
        return RunUtil.getExecute(getTextView(), obj -> obj.getText().toString());
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
        if (textView instanceof EditText) {
            if(!isEtEnable() && isMoneyEnable()){
                HelperUtil.addMoneyTextChangedListener((EditText) textView, this::getMax, text -> {
                    if (!TextUtils.equals(text, getHint())) {
                        setDetail(text);
                    }
                });
            } else {
                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        String text = s.toString();
                        if (!TextUtils.equals(text, getHint())) {
                            setDetail(text);
                        }
                    }
                });
            }
        }
    }

}