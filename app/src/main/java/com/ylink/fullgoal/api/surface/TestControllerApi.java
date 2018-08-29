package com.ylink.fullgoal.api.surface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.api.FileApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.List;

public class TestControllerApi<T extends TestControllerApi, C> extends SurfaceControllerApi<T, C> {

    private final static List<String> DATA = TextUtils.getListData("");

    private String nextAssets() {
        List<String> data = FileApi.getAssetsData("test", "json");
        ee("data", data);
        if (TextUtils.check(data)) {
            for (String item : data) {
                if(!DATA.contains(item)){
                    return item;
                }
            }
        }
        return "view/l_apply_v1.json";
    }

    public TestControllerApi(C controller) {
        super(controller);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String assets = nextAssets();
        ee("assets", assets);
        return createAssetsJsonView(assets, container);
    }

}