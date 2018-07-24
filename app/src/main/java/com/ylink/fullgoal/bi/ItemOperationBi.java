package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ItemOperationBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class ItemOperationBi extends SurfaceBi<ItemOperationBi, ItemOperationBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_item_operation;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ItemOperationBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setOnClickListener(nameTv, bean.getNameOnClickListener())
                .setOnClickListener(detailTv, bean.getDetailOnClickListener());
    }

}