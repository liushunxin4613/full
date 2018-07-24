package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class XCJPBi extends SurfaceBi<XCJPBi, XCJPBean> {

    @Bind(R.id.place_tv)
    TextView placeTv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.start_tv)
    TextView startTv;
    @Bind(R.id.type_tv)
    TextView typeTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.end_tv)
    TextView endTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_xcjp;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull XCJPBean bean) {
        super.updateBind(api, bean);
        api.setText(nameTv, bean.getName())
                .setText(detailTv, bean.getDetail())
                .setText(typeTv, bean.getType())
                .setText(placeTv, bean.getPlace())
                .setText(startTv, bean.getStart())
                .setText(endTv, bean.getEnd())
                .setOnClickListener(bean.getOnClickListener());
    }

}