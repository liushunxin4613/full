package com.ylink.fullgoal.api.item;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.BankNorm;

import butterknife.Bind;

public class BankControllerApi<C> extends OnClickControllerApi<BankControllerApi, C, BankNorm> {

    public BankControllerApi(C controller) {
        super(controller);
    }

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_bank;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onSafeNorm(@NonNull BankNorm norm, int position) {
        super.onSafeNorm(norm, position);
        setText(tv0, norm.getName())
                .setText(tv1, norm.getDetail())
                .setColorBg(rootVg, position > 0
                        ? R.color.white : R.color.gray_light)
                .setOnClickListener(norm.getOnClickListener());
    }

}