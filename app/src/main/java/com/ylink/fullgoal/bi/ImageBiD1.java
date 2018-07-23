package com.ylink.fullgoal.bi;

import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ImageBeanD1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class ImageBiD1 extends SurfaceBi<ImageBiD1, ImageBeanD1> {

    @Bind(R.id.image)
    ImageView image;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_image_d1;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ImageBeanD1 bean) {
        super.onBindApi(api, bean);
        api.setImage(image, bean.getRes());
    }

}