package com.leo.core.core;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

import static com.leo.core.iapi.api.IStartApi.CONTROLLER_API;
import static com.leo.core.iapi.api.IStartApi.ROOT_VIEW_CLZ_API;

public class BaseControllerApiActivity<T extends BaseControllerApiActivity, C extends IControllerApi> extends AppCompatActivity implements IAFVApi<T, C> {

    private IControllerApi controllerApi;

    @Override
    public IControllerApi<C, T> controllerApi() {
        if (controllerApi == null) {
            controllerApi = newControllerApi();
            if (controllerApi == null) {
                throw new NullPointerException("newControllerApi 不能为空");
            }
        }
        return controllerApi;
    }

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        execute(controllerApi(), api -> api.onCreate(savedInstanceState));
    }

    @Override
    protected void onStart() {
        super.onStart();
        execute(controllerApi(), IControllerApi::onStart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        execute(controllerApi(), IControllerApi::onResume);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        execute(controllerApi(), IControllerApi::onRestart);
    }

    @Override
    protected void onPause() {
        super.onPause();
        execute(controllerApi(), IControllerApi::onPause);
    }

    @Override
    protected void onStop() {
        super.onStop();
        execute(controllerApi(), IControllerApi::onStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        execute(controllerApi(), IControllerApi::onDestroy);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        execute(controllerApi(), api -> api.onConfigurationChanged(newConfig));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (controllerApi() != null && controllerApi().onKeyDown(keyCode, event)) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        execute(controllerApi(), api -> api.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        execute(controllerApi(), api -> api.onRequestPermissionsResult(requestCode, permissions, grantResults));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onIntent(intent);
        execute(controllerApi(), api -> api.onNewIntent(intent));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        execute(controllerApi(), IControllerApi::onBackPressed);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        execute(controllerApi(), obj -> obj.onStartActivity(intent));
    }

    @Override
    public void finish() {
        super.finish();
        execute(controllerApi(), IControllerApi::onFinish);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        execute(controllerApi(), obj -> obj.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    //自定义

    protected void onIntent(Intent intent) {
        try {
            Class clz = (Class) intent.getSerializableExtra(CONTROLLER_API);
            Class rootViewClz = (Class) intent.getSerializableExtra(ROOT_VIEW_CLZ_API);
            if (clz != null && CoreControllerApi.class.isAssignableFrom(clz)) {
                controllerApi = (IControllerApi) ObjectUtil.getObject(clz, Object.class, this);
                ((CoreControllerApi) controllerApi()).remove(clz);
            }
            if (controllerApi != null && rootViewClz != null &&
                    IControllerApi.class.isAssignableFrom(rootViewClz)) {
                controllerApi.setRootViewClzApi(rootViewClz);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    protected <R> void execute(R obj, IObjAction<R> api) {
        RunUtil.executeNon(obj, api);
    }

}
