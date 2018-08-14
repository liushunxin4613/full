package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.DepartmentNorm;

import butterknife.Bind;

public class DepartmentControllerApi<C> extends LineControllerApi<DepartmentControllerApi, C, DepartmentNorm>{

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    public DepartmentControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_department;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull DepartmentNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, norm.getDepartment());
    }

}