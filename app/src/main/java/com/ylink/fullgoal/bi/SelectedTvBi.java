package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.SelectedTvBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class SelectedTvBi extends SurfaceBi<SelectedTvBi, SelectedTvBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_selected_tv;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull SelectedTvBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .execute(() -> bean.setTextView(nameTv))
                .setOnClickListener(v -> {
                    ViewGroup vg = (ViewGroup) v.getParent();
                    if (TextUtils.equals(vg.getTag(), v)) {
                        v.setSelected(!v.isSelected());
                    } else {
                        if (vg.getTag() instanceof View) {
                            ((View) vg.getTag()).setSelected(false);
                        }
                        v.setSelected(true);
                        vg.setTag(v);
                    }
                    bean.execute(v, v.isSelected() ? bean.getName() : null);
                });
    }

}