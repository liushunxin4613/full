package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.InhibitionRuleControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class InhibitionRuleNorm extends BaseTestNorm<InhibitionRuleNorm> {

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new InhibitionRuleControllerApi(controller);
    }

    private static final String STATE_RED = "红灯";
    private static final String STATE_YELLOW = "黄灯";

    public InhibitionRuleNorm(String state, String name, String detail) {
        super(name, detail);
        setIconResId(getResId(state));
    }

    private Integer getResId(String state) {
        if (!TextUtils.isEmpty(state)) {
            switch (state) {
                case STATE_RED:
                    return R.mipmap.jin;
                case STATE_YELLOW:
                    return R.mipmap.jing;
            }
        }
        return null;
    }

}
