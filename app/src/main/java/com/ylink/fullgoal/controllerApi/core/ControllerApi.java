package com.ylink.fullgoal.controllerApi.core;

import android.view.View;

import com.leo.core.core.BaseControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.ylink.fullgoal.api.config.FgApi;
import com.ylink.fullgoal.api.config.ParseApi;
import com.ylink.fullgoal.api.config.UrlApi;
import com.ylink.fullgoal.api.func.UserApi;
import com.ylink.fullgoal.config.Api;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.UserFg;

import butterknife.ButterKnife;

import static com.ylink.fullgoal.config.UrlConfig.ROOT_URL;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T, C> {

    private String rootUrl = ROOT_URL;

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
    public Api getApi() {
        return getApi(rootUrl);
    }

    @Override
    public Api getApi(String url) {
        return create(url, Api.class);
    }

    @Override
    public UrlApi api() {
        return (UrlApi) super.api();
    }

    @Override
    public UrlApi newApi() {
        return new UrlApi(getThis());
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
    public FgApi uApi() {
        return new FgApi(getThis());
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
