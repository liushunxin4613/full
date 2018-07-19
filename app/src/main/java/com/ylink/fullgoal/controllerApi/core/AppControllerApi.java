package com.ylink.fullgoal.controllerApi.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.leo.core.core.BaseControllerApiApp;
import com.leo.core.core.MainManage;
import com.leo.core.net.RetrofitFactory;
import com.leo.core.util.LogUtil;
import com.leo.core.util.NetUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.ylink.fullgoal.config.MVCFactory;

public class AppControllerApi extends ControllerApi<AppControllerApi, BaseControllerApiApp> {

    public AppControllerApi(BaseControllerApiApp application) {
        super(application);
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplication());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainManage.init(getApplication());//主
        getApplication().registerActivityLifecycleCallbacks(activityLifecycleApi());
        LogUtil.openLog();//log
        NetUtils.init(getApplication());
        RetrofitFactory.show(false);
        //友盟初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout)
                -> new MaterialHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout)
                -> new ClassicsFooter(context));
//        ConfigFactory.getInstance().init(getThis()).start();
        MVCFactory.getInstance().init(getThis()).start();
    }

}