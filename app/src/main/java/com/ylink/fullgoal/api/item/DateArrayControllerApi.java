package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.GridRecycleControllerApi;
import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.DateArrayNorm;

import butterknife.Bind;

public class DateArrayControllerApi<C> extends NormControllerApi<DateArrayControllerApi, C, DateArrayNorm> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.vg)
    LinearLayout vg;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    public DateArrayControllerApi(C controller) {
        super(controller);
    }

    @Override
    public Integer getDefRootViewResId() {
        return R.layout.l_date_array;
    }

    @Override
    protected void onSafeNorm(@NonNull DateArrayNorm norm, int position) {
        setText(nameTv, norm.getName());
        setViewGroupApi(vg, vg -> {
            vg.removeAllViews();
            GridRecycleControllerApi gridApi = getViewControllerApi(GridRecycleControllerApi.class);
            vg.addView(gridApi.getRootView());
            gridApi.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
            gridApi.addAll(norm.getData()).notifyDataSetChanged();
        });
    }

}