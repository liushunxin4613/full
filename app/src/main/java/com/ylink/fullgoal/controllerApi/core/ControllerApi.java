package com.ylink.fullgoal.controllerApi.core;

import android.view.View;

import com.leo.core.api.main.HttpApi;
import com.leo.core.core.BaseControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.ylink.fullgoal.api.config.FgApi;
import com.ylink.fullgoal.api.config.ParseApi;
import com.ylink.fullgoal.api.func.UserApi;
import com.leo.core.net.Api;
import com.ylink.fullgoal.api.surface.MHttpApi;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.UserFg;

import butterknife.ButterKnife;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T, C> {

    public ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public UserApi userApi() {
        return (UserApi) super.userApi();
    }

    @Override
    public UserApi newUserApi() {
        return new UserApi(getThis());
    }

    @Override
    public Api getApi(String url) {
        return create(url, Api.class);
    }

    @Override
    public FgApi api() {
        return (FgApi) super.api();
    }

    @Override
    public FgApi newApi() {
        return new FgApi(getThis());
    }

    @Override
    public ParseApi parseApi() {
        return (ParseApi) super.parseApi();
    }

    @Override
    public ParseApi newParseApi() {
        return new ParseApi();
    }

    @Override
    public HttpApi newHttpApi() {
        return new MHttpApi(getThis(), newTransformer());
    }

    @Override
    public boolean isLogin() {
        return super.isLogin();
    }

    @Override
    public UserFg getUser() {
        return super.getUser();
    }

    @Override
    public DepartmentFg getDepartment() {
        return super.getDepartment();
    }

    @Override
    public Class<? extends View> getRootViewClz() {
        return BaseControllerApiView.class;
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
    }

}
