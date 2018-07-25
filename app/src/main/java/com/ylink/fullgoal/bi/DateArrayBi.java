package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.GridRecycleControllerApi;
import com.ylink.fullgoal.bean.DateArrayBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class DateArrayBi extends SurfaceBi<DateArrayBi, DateArrayBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.vg)
    LinearLayout vg;
    @Bind(R.id.icon_iv)
    ImageView iconIv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_date_array;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull DateArrayBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setViewGroupApi(vg, vg -> {
                    vg.removeAllViews();
                    GridRecycleControllerApi gridApi = (GridRecycleControllerApi)
                            api.getViewControllerApi(GridRecycleControllerApi.class);
                    vg.addView(gridApi.getRootView());
                    gridApi.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
                    gridApi.addAll(bean.getData()).notifyDataSetChanged();
                });
    }

}