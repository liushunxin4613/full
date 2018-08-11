package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.InhibitionRuleNorm;

import butterknife.Bind;

public class InhibitionRuleControllerApi<C> extends OnClickControllerApi<InhibitionRuleControllerApi, C, InhibitionRuleNorm>{

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public InhibitionRuleControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_inhibition_rule;
    }

    @Override
    protected void onSafeNorm(@NonNull InhibitionRuleNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setImage(iconIv, norm.getIconResId())
                .setText(detailTv, norm.getDetail());
    }

}