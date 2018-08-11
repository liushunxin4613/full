package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.TvH4Norm;

public class TvH4ControllerApi<C> extends NormControllerApi<TvH4ControllerApi, C, TvH4Norm> {

    private TextView timeTv;
    private TextView nameTv;
    private TextView detailTv;
    private TextView typeTv;

    public TvH4ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        timeTv = findViewById(R.id.time_tv);
        nameTv = findViewById(R.id.name_tv);
        detailTv = findViewById(R.id.detail_tv);
        typeTv = findViewById(R.id.type_tv);
    }

    @Override
    protected void onSafeNorm(@NonNull TvH4Norm norm, int position) {
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .setText(typeTv, norm.getType())
                .setText(timeTv, norm.getTime());
    }

}