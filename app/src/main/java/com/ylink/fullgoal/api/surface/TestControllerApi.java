package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.view.ChooseDialog;

import java.util.Arrays;

public class TestControllerApi extends SurfaceControllerApi {

    public TestControllerApi(Object controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_test;
    }

    @Override
    public void initView() {
        super.initView();
        ChooseDialog dialog = new ChooseDialog(getContext())
                .setTitle("请选择部门")
                .setData(Arrays.asList("A", "B", "C", "D"))
                .setOnWheelViewListener((selectedIndex, item) -> {
                    ee("selectedIndex", selectedIndex);
                    ee("item", item);
                });
//        bt.setOnClickListener(v -> dialog.show());
    }

}
