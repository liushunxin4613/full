package com.ylink.fullgoal.bi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvSBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class TvSBi extends SurfaceBi<TvSBi, TvSBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_tv_s;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvSBean bean) {
        super.onBindApi(api, bean);
        api.setText(nameTv, bean.getName())
                .setOnClickListener(v -> {
                    executeNon(bean.getTextApi(), textApi -> textApi.execute(bean.getName()));
                    ViewGroup vg = (ViewGroup) v.getParent();
                    if (!TextUtils.equals(vg.getTag(), v) || !v.isSelected()) {
                        if (vg.getTag() instanceof View) {
                            ((View) vg.getTag()).setSelected(false);
                        }
                        v.setSelected(true);
                        vg.setTag(v);
                    }
                });
    }

}