package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ChuchaiBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class ChuchaiBi extends OnClickBi<ChuchaiBi, ChuchaiBean> {

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

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_chuchai;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ChuchaiBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, bean.getCode())
                .setText(tv1, checkFormat("预支金额  %s", bean.getAmount()))
                .setText(tv2, format("%s(%s天)", bean.getPlace(), bean.getDay()))
                .setText(tv3, format("%s  ~  %s", bean.getStartDate(), bean.getEndDate()))
                .setText(tv4, String.format("工作内容  %s", no(bean.getText())));
    }

}
