package com.ylink.fullgoal.api.surface;

import com.google.gson.reflect.TypeToken;
import com.leo.core.bean.BaseBean;
import com.ylink.fullgoal.controllerApi.surface.RecycleBarControllerApi;
import com.ylink.fullgoal.view.ChooseDialog;
import com.ylink.fullgoal.view.WheelView;

import java.util.List;

public class OtherControllerApi<T extends OtherControllerApi, C> extends RecycleBarControllerApi<T, C> {

    public OtherControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        BaseBean root = decode(getAssetsString("jsonApi.json"), new TypeToken<BaseBean>() {
        }.getType());
        ee("root", root);
    }

    private void showChooseDialog(String title, List<String> data, WheelView.OnWheelViewListener listener) {
        new ChooseDialog(getContext())
                .setTitle(title)
                .setData(data)
                .setOnWheelViewListener(listener)
                .show();
    }

}
