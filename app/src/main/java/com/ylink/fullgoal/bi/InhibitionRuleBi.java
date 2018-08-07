package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class InhibitionRuleBi extends BaseApiBi<InhibitionRuleBi, InhibitionRuleBean> {


    @Bind(R.id.icon_iv)
    ImageView iconIv;
//    @Bind(R.id.name_tv)
//    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_inhibition_rule;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull InhibitionRuleBean bean) {
        super.updateBind(api, bean);
        api.setImage(iconIv, bean.getIconResId())
//                .setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail());
    }

}