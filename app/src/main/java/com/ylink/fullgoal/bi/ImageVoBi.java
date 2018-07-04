package com.ylink.fullgoal.bi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.core.util.HelperUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;
import com.ylink.fullgoal.vo.ImageVo;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class ImageVoBi extends SurfaceBi<ImageVoBi, ImageVo> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_et)
    EditText detailEt;
    @Bind(R.id.photo_iv)
    PhotoView photoIv;
    @Bind(R.id.vg)
    ViewGroup vg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_photo;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ImageVo bean) {
        super.onBindApi(api, bean);
        api.setVisibility(bean.isShow() ? View.VISIBLE : View.GONE, vg)
                .setVisibility(TextUtils.isEmpty(bean.getPhoto()) ?
                        View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailEt, bean.getAmount())
                .setImage(photoIv, bean.getPhoto())
                .execute(() -> HelperUtil.addMoneyTextChangedListener(detailEt, null, bean::setAmount));
    }

}