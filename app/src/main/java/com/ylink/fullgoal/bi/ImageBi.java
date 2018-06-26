package com.ylink.fullgoal.bi;

import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ImageBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class ImageBi extends SurfaceBi<ImageBi, ImageBean> {

    @Bind(R.id.image)
    ImageView image;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_ps_image;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ImageBean bean) {
        super.onBindApi(api, bean);
        api.setImage(image, bean.getPath());
    }

}