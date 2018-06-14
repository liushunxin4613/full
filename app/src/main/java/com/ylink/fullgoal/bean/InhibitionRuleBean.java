package com.ylink.fullgoal.bean;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.vo1.InhibitionRuleVo;

public class InhibitionRuleBean extends ApiBean<InhibitionRuleBean> {

    public InhibitionRuleBean(String state, String name, String detail) {
        super(name, detail);
        setIconResId(getResId(state));
    }

    private Integer getResId(String state){
        if(!TextUtils.isEmpty(state)){
            switch (state){
                case InhibitionRuleVo.STATE_RED:
                    return R.mipmap.icon_red;
                case InhibitionRuleVo.STATE_YELLOW:
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
