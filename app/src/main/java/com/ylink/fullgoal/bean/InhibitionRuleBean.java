package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bi.InhibitionRuleBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class InhibitionRuleBean extends ApiBean<InhibitionRuleBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, InhibitionRuleBean> newDefApi() {
        return new InhibitionRuleBi();
    }

    private static final String STATE_RED = "红灯";
    private static final String STATE_YELLOW = "黄灯";

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

}