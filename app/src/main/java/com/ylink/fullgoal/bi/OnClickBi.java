package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;
import com.ylink.fullgoal.bean.OnClickBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

public abstract class OnClickBi<T extends OnClickBi, B extends OnClickBean> extends SurfaceBi<T, B> {

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull B bean) {
        super.updateBind(api, bean);
        api.setOnClickListener(bean.getOnClickListener());
    }

}
