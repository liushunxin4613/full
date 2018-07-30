package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class TvH2MoreBi extends BaseApiBi<TvH2MoreBi, TvH2MoreBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_h_tv2_more;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull TvH2MoreBean bean) {
        super.updateBind(api, bean);
        ImageView iconIv = (ImageView) api.findViewById(R.id.icon_iv);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .execute(() -> bean.setTextView(detailTv))
                .setText(detailTv, TextUtils.isEmpty(bean.getDetail()) ? bean.getHint()
                        : bean.getDetail())
                .setTextColor(detailTv, api.getResTvColor(bean.getDetail()))
                .setImage(iconIv, TextUtils.isEmpty(bean.getDetail()) ? R.mipmap.more
                        : R.mipmap.cha)
                .setOnClickListener(iconIv, TextUtils.isEmpty(bean.getDetail())
                        ? bean.getOnClickListener() : bean.getIconOnClickListener())
                .setOnClickListener(bean.getOnClickListener());
    }

}