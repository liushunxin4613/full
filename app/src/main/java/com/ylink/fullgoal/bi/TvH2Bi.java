package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import butterknife.Bind;

public class TvH2Bi extends BaseApiBi<TvH2Bi, TvH2Bean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_tv2;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvH2Bean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailTv));
    }

}