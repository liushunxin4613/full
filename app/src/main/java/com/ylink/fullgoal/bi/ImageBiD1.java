package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class ImageBiD1 extends SurfaceBi {

    @Bind(R.id.image)
    ImageView image;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_image_d1;
    }

    /*@Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ImageBeanD1 bean) {
        super.updateBind(api, bean);
        api.setImage(image, bean.getRes());
    }*/

}