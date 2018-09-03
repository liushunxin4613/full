package com.ylink.fullgoal.api.surface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return createAssetsJsonView("test_apply.json", container);
    }

}