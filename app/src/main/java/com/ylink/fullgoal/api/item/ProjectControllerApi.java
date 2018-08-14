package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.ProjectNorm;

import butterknife.Bind;

public class ProjectControllerApi<C> extends LineControllerApi<ProjectControllerApi, C, ProjectNorm>{

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
    @Bind(R.id.vg0)
    LinearLayout vg0;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    public ProjectControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_project_v1;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull ProjectNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, String.format("项目名称  %s", no(norm.getProjectName())))
                .setText(tv1, String.format("项目编号  %s", no(norm.getProjectCode())))
                .setText(tv2, norm.getState())
                .setText(tv3, norm.getUsername())
                .setText(tv4, norm.getDepartment());
    }

}