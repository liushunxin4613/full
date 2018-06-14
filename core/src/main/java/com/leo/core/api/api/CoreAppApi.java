package com.leo.core.api.api;

import android.app.Application;

import com.leo.core.core.ApiFactory;
import com.leo.core.core.MainManage;
import com.leo.core.util.NetUtils;

import static com.leo.core.util.ClassBindUtil.close;

public class CoreAppApi<T extends CoreAppApi, A extends Application> extends ApplicationApi<T, A> {

    public CoreAppApi(A context) {
        super(context);
    }

    @Override
    public T init() {
        MainManage.init(getAttach());//主
        NetUtils.init(getAttach());
        return super.init();
    }

    @Override
    public T clear() {
        ApiFactory.getInstance().close();
        close();
        return super.clear();
    }

}
