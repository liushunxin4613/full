package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHEtBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvHEtBi extends BaseApiBi<TvHEtBi, TvHEtBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_et)
    EditText detailEt;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_tv_h_et;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvHEtBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .execute(() -> bean.setTextView(detailEt))
                .setText(detailEt, bean.getDetail())
                .setTextHint(detailEt, bean.getHint());
    }

}