package com.leo.core.core;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

import static com.leo.core.iapi.IStartApi.CONTROLLER_API;
import static com.leo.core.iapi.IStartApi.ROOT_VIEW_CLZ_API;

public class BaseControllerApiFragment<T extends BaseControllerApiFragment, C extends IControllerApi> extends Fragment implements IAFVApi<T, C> {

    private IControllerApi controllerApi;

    @Override
    public IControllerApi<C, T> controllerApi() {
        if(controllerApi == null){
            controllerApi = newControllerApi();
            if(controllerApi == null){
                throw new NullPointerException("newControllerApi 不能为空");
            }
        }
        return controllerApi;
    }

    @Override
    public IControllerApi<C, T> newControllerApi(){
        return new BaseControllerApi(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onArguments(getArguments());
        execute(controllerApi(), api -> api.onAttach(context));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        execute(controllerApi(), api -> api.onCreate(savedInstanceState));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(controllerApi() != null){
            return controllerApi().onCreateView(inflater, container, savedInstanceState);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        execute(controllerApi(), api -> api.onViewCreated(view, savedInstanceState));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        execute(controllerApi(), api -> api.onActivityCreated(savedInstanceState));
    }

    @Override
    public void onStart() {
        super.onStart();
        execute(controllerApi(), IControllerApi::onStart);
    }

    @Override
    public void onResume() {
        super.onResume();
        execute(controllerApi(), IControllerApi::onResume);
    }

    @Override
    public void onPause() {
        super.onPause();
        execute(controllerApi(), IControllerApi::onPause);
    }

    @Override
    public void onStop() {
        super.onStop();
        execute(controllerApi(), IControllerApi::onStop);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        execute(controllerApi(), IControllerApi::onDestroyView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        execute(controllerApi(), IControllerApi::onDestroy);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        execute(controllerApi(), IControllerApi::onDetach);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        execute(controllerApi(), api -> api.setUserVisibleHint(isVisibleToUser));
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        execute(controllerApi(), api -> api.setMenuVisibility(menuVisible));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        execute(controllerApi(), api -> api.onConfigurationChanged(newConfig));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        execute(controllerApi(), api -> api.onActivityResult(requestCode, resultCode, data));
    }

    protected void onArguments(Bundle arguments){
        if(arguments != null){
            try {
                Class clz = (Class) getArguments().getSerializable(CONTROLLER_API);
                Class rootViewClz = (Class) getArguments().getSerializable(ROOT_VIEW_CLZ_API);
                if (CoreControllerApi.class.isAssignableFrom(clz)) {
                    controllerApi = (IControllerApi) ObjectUtil.getObject(clz, Object.class, this);
                }
                if (controllerApi != null && IControllerApi.class.isAssignableFrom(rootViewClz)) {
                    controllerApi.setRootViewClzApi(rootViewClz);
                }
            } catch (Exception e) {}
        }
    }

    protected <R> void execute(R obj, IRunApi<R> api){
        RunUtil.executeNon(obj, api);
    }

}
