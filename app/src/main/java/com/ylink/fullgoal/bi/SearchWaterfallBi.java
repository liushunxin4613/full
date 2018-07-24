package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.StaggeredGridRecycleControllerApi;
import com.ylink.fullgoal.bean.SearchWaterfallBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class SearchWaterfallBi extends BaseApiBi<SearchWaterfallBi, SearchWaterfallBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.name_et)
    EditText nameEt;
    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.vg)
    LinearLayout vg;

    @Override
    protected Integer getEnableDefLayoutResId() {
        return R.layout.l_search_waterfall;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull SearchWaterfallBean bean) {
        super.updateBind(api, bean);
        api.setViewGroupApi(vg, vg -> {
            bean.setCloseIv(iconIv).setTextView(nameEt);
            api.setText(nameTv, bean.getName());
            StaggeredGridRecycleControllerApi staApi = (StaggeredGridRecycleControllerApi)
                    api.getViewControllerApi(StaggeredGridRecycleControllerApi.class);
            staApi.getRecyclerView().setBackgroundColor(ResUtil.getColor(R.color.white));
            bean.setApi(staApi);
            vg.removeAllViews();
            vg.addView(staApi.getRootView());
            execute(bean.getBeanData(), obj -> obj.setTextApi(text -> api.setText(detailTv, text)));
            staApi.addAll(bean.getBeanData()).notifyDataSetChanged();
        });
    }

}
