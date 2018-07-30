package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.XiechengBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class XiechengBi extends OnClickBi<XiechengBi, XiechengBean> {

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
        return R.layout.l_xiecheng;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull XiechengBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, checkFormat("航班号  %s", bean.getCode()))
                .setText(tv1, checkFormat("%s - %s", bean.getStartPlace(), bean.getEndPlace()))
                .setText(tv2, checkFormat("乘机人  %s", bean.getUsername()))
                .setText(tv3, checkFormat("%s %s 开", bean.getStartDate(), bean.getStartTime()))
                .setText(tv4, checkFormat("金额      %s", bean.getAmount()))
                .setText(tv5, checkFormat("%s %s 到", bean.getEndDate(), bean.getEndTime()));
    }

}
