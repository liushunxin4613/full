package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import static com.leo.core.util.TextUtils.check;

public class TvH4Bi extends SurfaceBi<TvH4Bi, TvH4Bean> {

    @Override
    public Integer getDefLayoutResId() {
        if (check(bean()) && check(bean().isTitle())) {
            return R.layout.l_process_title;
        }
        return R.layout.l_process;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, TvH4Bean bean) {
        super.onBindApi(api, bean);
        TextView timeTv = (TextView) api.findViewById(R.id.time_tv);
        TextView nameTv = (TextView) api.findViewById(R.id.name_tv);
        TextView detailTv = (TextView) api.findViewById(R.id.detail_tv);
        TextView typeTv = (TextView) api.findViewById(R.id.type_tv);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(typeTv, bean.getType())
                .setText(timeTv, bean.getTime());
    }

}