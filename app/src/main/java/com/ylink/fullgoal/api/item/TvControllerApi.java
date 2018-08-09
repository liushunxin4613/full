package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvNorm;

import butterknife.Bind;

public class TvControllerApi<C> extends OnClickControllerApi<TvControllerApi, C, TvNorm>{

    @Bind(R.id.name_tv)
    TextView nameTv;

    public TvControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_tv;
    }

    @Override
    protected void onSafeNorm(@NonNull TvNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName());
    }

}