package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.PersonBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class PersonBi extends OnClickBi<PersonBi, PersonBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_person;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull PersonBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, bean.getName())
                .setText(tv1, bean.getPhone())
                .setText(tv2, bean.getDepartment());
    }

}