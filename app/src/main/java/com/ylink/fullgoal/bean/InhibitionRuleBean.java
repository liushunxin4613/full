package com.ylink.fullgoal.bean;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

public class InhibitionRuleBean extends ApiBean<InhibitionRuleBean> {

    public static final String STATE_RED = "退回";
    public static final String STATE_YELLOW = "需特批";

    public InhibitionRuleBean(String state, String name, String detail) {
        super(name, detail);
        setIconResId(getResId(state));
    }

    private Integer getResId(String state){
        if(!TextUtils.isEmpty(state)){
            switch (state){
                case STATE_RED:
                    return R.mipmap.icon_red;
                case STATE_YELLOW:
                    return R.mipmap.icon_yellow;
            }
        }
        return null;
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_inhibition_rule;
    }

}
