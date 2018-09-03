package com.ylink.fullgoal.norm;

import android.widget.EditText;

import com.leo.core.iapi.inter.IABAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.MoneyControllerApi;

import static com.leo.core.util.TextUtils.getMoneyString;

public class MoneyNorm extends BaseTestNorm<MoneyNorm>{

    @Override
    public Integer getApiType() {
        if (isEtEnable()) {
            return R.layout.l_h_tv_money;
        }
        return R.layout.l_h_money;
    }

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return MoneyControllerApi.class;
    }

    private transient IABAction<MoneyNorm, String> action;

    public MoneyNorm(String name, String detail) {
        super(name, detail);
        setEtEnable(true);
        setMoneyEnable(false);
    }

    public MoneyNorm(String name, String detail, double max, IABAction<MoneyNorm, String> action) {
        super(name, detail);
        LogUtil.ee(this, "detail: " + detail);
        setMax(max);
        setEtEnable(false);
        setMoneyEnable(true);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(this, getDetail());
        }
    }

    @Override
    public MoneyNorm setMax(Double max) {
        setHint(String.format("最多可分摊%s", getMoneyString(max)));
        if (getTextView() instanceof EditText) {
            getTextView().setHint(getHint());
        }
        return super.setMax(max);
    }
    
}