package com.ylink.fullgoal.bi;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.OnClickBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

public abstract class OnClickBi<T extends OnClickBi, B extends OnClickBean> extends SurfaceBi<T, B> {

    protected abstract View getRootVg();

    @SuppressLint("ResourceAsColor")
    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull B bean) {
        super.updateBind(api, bean);
        api.setOnClickListener(bean.getOnClickListener())
                .setColorBg(getRootVg(), bean.isSelected()
                        ? R.color.gray_light : R.color.white);
    }

    protected String checkFormat(String format, String key){
        if(TextUtils.check(format, key)){
            return String.format(format, key);
        }
        return null;
    }

    protected String checkFormat(String format, String key, String value){
        if(TextUtils.check(format, key, value)){
            return String.format(format, key, value);
        }
        return null;
    }

    protected String format(String format, String... args){
        if(TextUtils.check(format, args)){
            return String.format(format, (Object[]) args);
        }
        return null;
    }

}