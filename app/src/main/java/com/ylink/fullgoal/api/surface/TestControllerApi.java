package com.ylink.fullgoal.api.surface;

import android.widget.Button;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.view.ChooseDialog;

import java.util.Arrays;

import butterknife.Bind;

public class TestControllerApi extends SurfaceControllerApi {

    @Bind(R.id.bt)
    Button bt;

    public TestControllerApi(Object controller) {
        super(controller);
    }

    @Override
    public Integer getRootViewResId() {
        return R.layout.l_test;
    }

    @Override
    public void initView() {
        super.initView();
        ChooseDialog dialog = new ChooseDialog(getContext())
                .setTitle("请选择部门")
                .setItems(Arrays.asList("A", "B", "C", "D"))
                .setOnWheelViewListener((selectedIndex, item) -> {
                    e("selectedIndex", selectedIndex);
                    e("item", item);
                });
        bt.setOnClickListener(v -> dialog.show());
    }

}
