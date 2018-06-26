package com.ylink.fullgoal.bi;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;
import com.ylink.fullgoal.vo.ImageVo;

import butterknife.Bind;

public class GridPhotoBi extends SurfaceBi<GridPhotoBi, GridPhotoBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_grid_photo;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, GridPhotoBean bean) {
        super.onBindApi(api, bean);
        api.setImage(iconIv, bean.getRes())
                .execute(() -> {
                    if (bean.getObj() instanceof ImageVo) {
                        ImageVo vo = (ImageVo) bean.getObj();
                        api.setText(nameTv, String.format("金额: %s", TextUtils.isEmpty(vo.getAmount())
                                ? "0" : vo.getAmount()));
                    }
                })
                .setLayoutParams(iconIv, new LinearLayout.LayoutParams(-1, bean.getUnit()))
                .setVisibility(nameTv, bean.isVisible() ? View.VISIBLE : View.GONE)
                .setOnClickListener(bean.getOnClickListener())
                .setOnLongClickListener(bean.getOnLongClickListener());
    }

}