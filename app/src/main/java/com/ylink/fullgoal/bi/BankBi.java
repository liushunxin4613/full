package com.ylink.fullgoal.bi;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.BankBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class BankBi extends SurfaceBi<BankBi, BankBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.root_vg)
    RelativeLayout rootVg;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_bank;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull BankBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, bean.getName())
                .setText(tv1, bean.getDetail())
                .setColorBg(rootVg, bean.isSelected()
                        ? R.color.gray_light : R.color.white)
                .setOnClickListener(bean.getOnClickListener());
    }

}