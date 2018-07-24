package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvV2DialogBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class TvV2DialogBi extends SurfaceBi<TvV2DialogBi, TvV2DialogBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.line_view)
    View lineView;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_dialog_photo;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvV2DialogBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setDialog(api.getDialog()))
                .setVisibility(nameTv, bean.isShow() ? View.VISIBLE : View.GONE)
                .setVisibility(lineView, bean.isShow() ? View.VISIBLE : View.GONE)
                .setOnClickListener(nameTv, bean.getOnNameClickListener())
                .setOnClickListener(detailTv, bean.getOnDetailClickListener());
    }

}