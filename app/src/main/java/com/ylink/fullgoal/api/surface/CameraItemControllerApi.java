package com.ylink.fullgoal.api.surface;

import android.widget.ImageView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ImageBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class CameraItemControllerApi<T extends CameraItemControllerApi, C> extends SurfaceControllerApi<T, C>{

    private ImageView image;

    public CameraItemControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        image = findViewById(R.id.image);
        //图片
        putBindBeanCallback(ImageBean.class, (bean, position) -> {
            ee("path", bean.getPath());
            setImage(image, bean.getPath());
        });
    }

}
