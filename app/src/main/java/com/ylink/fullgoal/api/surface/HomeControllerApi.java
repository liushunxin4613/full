package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;

public class HomeControllerApi<T extends HomeControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public HomeControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("财务共享机器人")
                .hideBackIv()
                .setRightTv("拍照", v -> {

                });
    }

}
