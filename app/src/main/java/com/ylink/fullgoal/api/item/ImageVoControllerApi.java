package com.ylink.fullgoal.api.item;

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
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.ImageVoNorm;
import com.ylink.fullgoal.view.HFrameLayout;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;

public class ImageVoControllerApi<C> extends NormControllerApi<ImageVoControllerApi, C, ImageVoNorm> {

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

    public ImageVoControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_photo;
    }

    @Override
    protected void onSafeNorm(@NonNull ImageVoNorm norm, int position) {
        setVisibility(norm.isShow() ? View.VISIBLE : View.GONE, vg)
                .setVisibility(TextUtils.isEmpty(norm.getPhoto()) ?
                        View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailEt, norm.getAmount())
                .setImage(photoIv, norm.getPhoto(), ImageFactory.getInstance().getRotate(norm.getPhoto()),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate))
                .execute(() -> HelperUtil.addMoneyTextChangedListener(detailEt,
                        null, norm::setAmount))
                .setOnClickListener(toLeftTv, view -> setImage(photoIv, norm.getPhoto(),
                        ImageFactory.getInstance().getRotate(norm.getPhoto(), -90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .setOnClickListener(toRightTv, view -> setImage(photoIv, norm.getPhoto(),
                        ImageFactory.getInstance().getRotate(norm.getPhoto(), 90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .execute(() -> hVg.setListenOnClickListener(SoftInputUtil::hidSoftInput));
    }

}