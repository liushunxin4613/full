package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvHEtIconMoreBi extends BaseApiBi<TvHEtIconMoreBi, TvHEtIconMoreBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_et)
    EditText detailEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_tv_et_more;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvHEtIconMoreBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .execute(() -> bean.setTextView(detailEt))
                .setImage(iconIv, bean.getIconResId())
                .setIcon(iconIv, !TextUtils.isEmpty(bean.getIconResId()))
                .setOnClickListener(iconIv, bean.getOnClickListener())
                .setTextHint(detailEt, bean.getHint())
                .setText(detailEt, bean.getDetail());
    }

}