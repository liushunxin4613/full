package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.DepartmentBean;
import com.ylink.fullgoal.bean.ShareBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import butterknife.Bind;

public class ShareBi extends OnClickBi<ShareBi, ShareBean> {

    @Bind(R.id.icon_iv)
    ImageView iconIv;
    @Bind(R.id.tv0)
    TextView tv0;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_share;
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ShareBean bean) {
        super.updateBind(api, bean);
        api.setText(tv0, bean.getShare());
    }

}
