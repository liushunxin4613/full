package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.PersonNorm;

import butterknife.Bind;

public class PersonControllerApi<C> extends LineControllerApi<PersonControllerApi, C, PersonNorm>{

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

    public PersonControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_person;
    }

    @Override
    protected View getRootVg() {
        return rootVg;
    }

    @Override
    protected void onSafeNorm(@NonNull PersonNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, norm.getName())
                .setText(tv1, norm.getPhone())
                .setText(tv2, norm.getDepartment());
    }

}