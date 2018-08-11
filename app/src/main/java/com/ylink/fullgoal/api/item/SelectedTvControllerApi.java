package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.SelectedTvNorm;

import butterknife.Bind;

public class SelectedTvControllerApi<C> extends NormControllerApi<SelectedTvControllerApi, C, SelectedTvNorm>{

    @Bind(R.id.name_tv)
    TextView nameTv;

    public SelectedTvControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_selected_tv;
    }

    @Override
    protected void onSafeNorm(@NonNull SelectedTvNorm norm, int position) {
        setText(nameTv, norm.getName())
                .execute(() -> norm.setTextView(nameTv))
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
                    norm.execute(v, v.isSelected() ? norm.getName() : null);
                });
    }

}