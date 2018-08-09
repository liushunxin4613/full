package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.factory.ImageFactory;
import com.leo.core.util.HelperUtil;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;
import com.ylink.fullgoal.view.HFrameLayout;
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
    @Bind(R.id.to_left_tv)
    ImageView toLeftTv;
    @Bind(R.id.to_right_tv)
    ImageView toRightTv;
    @Bind(R.id.vg)
    ViewGroup vg;
    @Bind(R.id.h_vg)
    HFrameLayout hVg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_photo;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ImageVo bean) {
        super.updateBind(api, bean);
        api.setVisibility(bean.isShow() ? View.VISIBLE : View.GONE, vg)
                .setVisibility(TextUtils.isEmpty(bean.getPhoto()) ?
                        View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailEt, bean.getAmount())
                .setImage(photoIv, bean.getPhoto(), ImageFactory.getInstance().getRotate(bean.getPhoto()),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate))
                .execute(() -> HelperUtil.addMoneyTextChangedListener(detailEt,
                        null, bean::setAmount))
                .setOnClickListener(toLeftTv, view -> api.setImage(photoIv, bean.getPhoto(),
                        ImageFactory.getInstance().getRotate(bean.getPhoto(), -90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .setOnClickListener(toRightTv, view -> api.setImage(photoIv, bean.getPhoto(),
                        ImageFactory.getInstance().getRotate(bean.getPhoto(), 90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .execute(() -> hVg.setListenOnClickListener(SoftInputUtil::hidSoftInput));
    }

}