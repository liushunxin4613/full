package com.ylink.fullgoal.bean;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.core.SurfaceBiBean;

public abstract class ApiBean<T extends ApiBean> extends SurfaceBiBean<T> {

    @Override
    protected String getDefaultKeyword() {
        return getName();
    }

    //不做引入处理
    private transient View.OnClickListener onClickListener;
    private transient TextView textView;

    private transient Integer iconResId;
    private String name;
    private String detail;
    private String hint;
    private transient boolean etEnable;
    private transient boolean isMoneyInputType;
    private transient Double max;
    private transient boolean moneyEnable;

    public ApiBean(String name) {
        this(null, name, null, null, null);
    }

    public ApiBean(String name, OnBVClickListener<T> listener) {
        this(null, name, null, null, listener);
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

    public ApiBean(Integer iconResId, String name, String detail) {
        this(iconResId, name, detail, null, null);
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

    public ApiBean(Integer iconResId, String name, String detail, String hint,
                   OnBVClickListener<T> listener) {
        this.iconResId = iconResId;
        this.name = name;
        this.detail = detail;
        this.hint = hint;
        setOnBVClickListener(listener);
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
        return RunUtil.getExecute(getTextView(), obj -> obj.getText().toString());
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
        if (this.textView != null && !isEtEnable() && isMoneyEnable()) {
            this.textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable text) {
                    onAfterTextChanged(text);
                }
            });
        }
    }

    private void onAfterTextChanged(Editable text) {
        String temp = text.toString();
        int posDot = temp.indexOf(".");
        int endPosDot = temp.lastIndexOf(".");
        if (posDot >= 0 && endPosDot != posDot) {
            text.delete(endPosDot, endPosDot + 1);
            return;
        }
        if (posDot > 0) {
            if (temp.length() - posDot - 1 > 2) {
                text.delete(posDot + 3, posDot + 4);
                return;
            }
        } else if (posDot == 0) {
            text.insert(0, "0");
        }
        if (max != null) {
            float f = JavaTypeUtil.getfloat(temp, 0);
            if (f > max) {
                text.delete(temp.length() - 1, temp.length());
                return;
            }
        }
        if (!TextUtils.equals(temp, getHint())) {
            setDetail(temp);
        }
    }

    public View.OnClickListener getOnClickListener() {
        return getEnable(onClickListener, null);
    }

}