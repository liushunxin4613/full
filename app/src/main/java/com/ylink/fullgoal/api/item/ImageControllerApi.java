package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.ImageNorm;

import butterknife.Bind;

public class ImageControllerApi<T extends ImageControllerApi, C> extends NormControllerApi<T, C, ImageNorm> {

    @Bind(R.id.image)
    ImageView image;

    public ImageControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_image_d1;
    }

    @Override
    protected void onSafeNorm(@NonNull ImageNorm norm, int position) {
        setImage(image, norm.getRes());
    }

}