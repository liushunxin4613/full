package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ReimburseTypeBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class ReimburseTypeBi extends BaseApiBi<ReimburseTypeBi, ReimburseTypeBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_sx_bottom;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ReimburseTypeBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> {
                    bean.setNameTv(nameTv);
                    bean.setDetailTv(detailTv);
                })
                .setOnClickListener(nameTv, v -> {
                    executeNon(nameTv, tv -> {
                        api.setSelected(tv, !tv.isSelected());
                        bean.execute(v, tv.isSelected() ? bean.getName() : null);
                    });
                    api.setSelected(detailTv, false);
                })
                .setOnClickListener(detailTv, v -> {
                    executeNon(detailTv, tv -> {
                        api.setSelected(tv, !tv.isSelected());
                        bean.execute(v, tv.isSelected() ? bean.getDetail() : null);
                    });
                    api.setSelected(nameTv, false);
                });
    }

}