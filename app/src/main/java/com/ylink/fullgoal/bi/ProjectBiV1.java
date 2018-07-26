package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ProjectBeanV1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class ProjectBiV1 extends OnClickBi<ProjectBiV1, ProjectBeanV1> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_project_v1;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ProjectBeanV1 bean) {
        super.updateBind(api, bean);
        api.setText(tv0, String.format("项目名称  %s", no(bean.getProjectName())))
                .setText(tv1, String.format("项目编号  %s", no(bean.getProjectCode())))
                .setText(tv2, bean.getState())
                .setText(tv3, bean.getUsername())
                .setText(tv4, bean.getDepartment());
    }

}
