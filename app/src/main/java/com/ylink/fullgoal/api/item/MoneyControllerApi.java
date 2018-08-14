package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.MoneyNorm;

public class MoneyControllerApi<C> extends LineControllerApi<MoneyControllerApi, C, MoneyNorm>{

    private TextView nameTv;
    private TextView detailTv;
    private EditText detailEt;

    public MoneyControllerApi(C controller) {
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
    protected void onSafeNorm(@NonNull MoneyNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(nameTv, norm.getName())
                .execute(() -> {
                    if (detailEt != null) {
                        norm.setTextView(detailEt);
                    } else {
                        norm.setTextView(detailTv);
                    }
                })
                .setTextHint(detailEt, norm.getHint())
                .setText(detailEt, norm.getDetail())
                .setText(detailTv, norm.getDetail());
    }
    
}