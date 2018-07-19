package com.ylink.fullgoal.config;

import android.view.View;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

public class ViewBi extends SurfaceBi<ViewBi, ViewBean> {

    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;

    @Override
    public Integer getDefLayoutResId() {
        return null;
    }

    private <V extends View> V findViewById(int resId) {
        return (V) api().findViewById(resId);
    }

    private void onFindViewById() {
        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ViewBean bean) {
        super.onBindApi(api, bean);
        api.execute(this::onFindViewById)
                .setOnClickListener(bean.getOnClickListener())
                .setText(tv0, bean.getTv0())
                .setText(tv1, bean.getTv1())
                .setText(tv2, bean.getTv2())
                .setText(tv3, bean.getTv3())
                .setText(tv4, bean.getTv4())
                .setText(tv5, bean.getTv5())
                .setText(tv6, bean.getTv6())
                .setText(tv7, bean.getTv7())
                .setText(tv8, bean.getTv8())
                .setText(tv9, bean.getTv9());
    }

}