package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ChuchaiBean;
import com.ylink.fullgoal.bean.DiaoyanBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class DiaoyanBi extends OnClickBi<DiaoyanBi, DiaoyanBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.vg)
    LinearLayout vg;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.tv5)
    TextView tv5;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_diaoyan;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull DiaoyanBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, bean.getCode())
                .setText(tv1, bean.getName())
                .setText(tv2, bean.getType())
                .setText(tv3, bean.getState())
                .setText(tv4, checkFormat("%s ~ %s", bean.getStartDate(), bean.getEndDate()))
                .setText(tv5, bean.getText());
    }

}
