package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.TvHEt3Norm;

public class TvHEt3ControllerApi<C> extends OnClickControllerApi<TvHEt3ControllerApi, C, TvHEt3Norm> {

    private TextView nameTv;
    private TextView detailTv;
    private EditText detailEt;

    public TvHEt3ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void initView() {
        super.initView();
        nameTv = findViewById(R.id.name_tv);
        detailTv = findViewById(R.id.detail_tv);
        detailEt = findViewById(R.id.detail_et);
    }

    @Override
    protected void onSafeNorm(@NonNull TvHEt3Norm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .setText(detailTv, norm.getDetail())
                .execute(() -> norm.setTextView(detailEt))
                .setText(detailEt, norm.getDetail())
                .setTextHint(detailEt, norm.getHint());
    }

}