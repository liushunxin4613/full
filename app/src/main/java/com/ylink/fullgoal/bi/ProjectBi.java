package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ProjectBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class ProjectBi extends SurfaceBi<ProjectBi, ProjectBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.left_tv)
    TextView leftTv;
    @Bind(R.id.center_tv)
    TextView centerTv;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_project;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ProjectBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(timeTv, bean.getTime())
                .setText(leftTv, bean.getLeft())
                .setText(centerTv, bean.getCenter())
                .setText(rightTv, bean.getRight())
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(bean.getOnClickListener());
    }

}
