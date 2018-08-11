package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.ReimburseTypeNorm;

import butterknife.Bind;

public class ReimburseTypeControllerApi<C> extends OnClickControllerApi<ReimburseTypeControllerApi, C, ReimburseTypeNorm> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    public ReimburseTypeControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_sx_bottom;
    }

    @Override
    protected void onSafeNorm(@NonNull ReimburseTypeNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .execute(() -> {
                    norm.setNameTv(nameTv);
                    norm.setDetailTv(detailTv);
                })
                .setOnClickListener(nameTv, v -> {
                    executeNon(nameTv, tv -> {
                        setSelected(tv, !tv.isSelected());
                        norm.execute(v, tv.isSelected() ? norm.getName() : null);
                    });
                    setSelected(detailTv, false);
                })
                .setOnClickListener(detailTv, v -> {
                    executeNon(detailTv, tv -> {
                        setSelected(tv, !tv.isSelected());
                        norm.execute(v, tv.isSelected() ? norm.getDetail() : null);
                    });
                    setSelected(nameTv, false);
                });
    }
    
}