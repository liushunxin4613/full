package com.ylink.fullgoal.controllerApi.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.leo.core.core.BaseControllerApiApp;
import com.leo.core.core.MainManage;
import com.leo.core.util.LogUtil;
import com.leo.core.util.NetUtils;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.ylink.fullgoal.config.MVCFactory;
import com.ylink.fullgoal.db.core.AppDatabase;
import com.ylink.fullgoal.factory.BankFactory;
import com.ylink.fullgoal.factory.CacheFactory;
import com.ylink.fullgoal.factory.FilesFactory;
import com.ylink.fullgoal.util.CameraUtil;

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
        FlowManager.init(new FlowConfig.Builder(getContext()).build());
        MainManage.init(getApplication());//主
        LogUtil.openLog();//log
        AppDatabase.init();//初始化
        FilesFactory.getInstance().init(getThis());
        FilesFactory.getInstance().openCore();
        CacheFactory.getInstance().init();//初始化缓存
        Utils.init(getApplication());
        getApplication().registerActivityLifecycleCallbacks(activityLifecycleApi());
        CameraUtil.init(getContext());
        NetUtils.init(getApplication());
        BankFactory.getInstance().init(getThis());
        //友盟初始化
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout)
                -> new MaterialHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout)
                -> new ClassicsFooter(context));
        MVCFactory.getInstance().init(getThis()).start();
    }

}