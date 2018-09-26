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

    @Bind(R.id.photo_iv)
    PhotoView photoIv;
    @Bind(R.id.to_left_tv)
    ImageView toLeftTv;
    @Bind(R.id.to_right_tv)
    ImageView toRightTv;

    private ViewGroup vg;
    private TextView nameTv;
    private HFrameLayout hVg;
    private TextView detailEt;

    public ImageVoControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        vg = findViewById(R.id.vg);
        hVg = findViewById(R.id.h_vg);
        nameTv = findViewById(R.id.name_tv);
        detailEt = findViewById(R.id.detail_et);
    }

    @Override
    protected void onSafeNorm(@NonNull ImageVoNorm norm, int position) {
        setVisibility(norm.isShow() ? View.VISIBLE : View.GONE, vg)
                .setVisibility(TextUtils.isEmpty(norm.getPhoto()) ?
                        View.INVISIBLE : View.VISIBLE, photoIv)
                .setText(nameTv, "金额")
                .setText(detailEt, TextUtils.isEmpty(norm.getAmount()) ? "0" : norm.getAmount())
                .setImage(photoIv, norm.getPhoto(), ImageFactory.getInstance().getRotate(norm.getPhoto()),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate))
                .execute(() -> {
                    if (detailEt instanceof EditText) {
                        HelperUtil.addMoneyTextChangedListener((EditText) detailEt,
                                null, norm::setAmount);
                    }
                })
                .setOnClickListener(toLeftTv, view -> setImage(photoIv, norm.getPhoto(),
                        ImageFactory.getInstance().getRotate(norm.getPhoto(), -90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .setOnClickListener(toRightTv, view -> setImage(photoIv, norm.getPhoto(),
                        ImageFactory.getInstance().getRotate(norm.getPhoto(), 90),
                        (path, iv, rotate, success) -> ImageFactory.getInstance().save(path, rotate)))
                .execute(() -> hVg.setListenOnClickListener(SoftInputUtil::hidSoftInput));
    }

}