package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHintBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class TvHintBi extends SurfaceBi<TvHintBi, TvHintBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_tv_hint;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvHintBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setOnClickListener(bean.getOnClickListener());
    }

}