package com.ylink.fullgoal.controllerApi.core;

import com.leo.core.api.main.HttpApi;
import com.leo.core.core.BaseControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.api.RouteApi;
import com.ylink.fullgoal.api.config.FgApi;
import com.ylink.fullgoal.api.config.ParseApi;
import com.leo.core.net.Api;
import com.ylink.fullgoal.api.func.UserApiV1;
import com.ylink.fullgoal.api.surface.MHttpApi;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.db.table.Text;
import com.ylink.fullgoal.fg.DepartmentFg;

import java.util.List;

import butterknife.ButterKnife;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T, C> {

    public ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public UserApiV1 userApi() {
        return (UserApiV1) super.userApi();
    }

    @Override
    public UserApiV1 newUserApi() {
        return new UserApiV1(getThis());
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
        return new MHttpApi(getThis(), transformer());
    }

    @Override
    public RouteApi routeApi() {
        return (RouteApi) super.routeApi();
    }

    @Override
    public RouteApi newRouteApi() {
        return new RouteApi(getThis());
    }

    @Override
    public boolean isLogin() {
        return super.isLogin();
    }

    @Override
    public UserBean getUser() {
        return super.getUser();
    }

    @Override
    public DepartmentFg getDepartment() {
        return super.getDepartment();
    }

    @Override
    public List<String> getJsonViewAssetsDefDir() {
        return TextUtils.getListData("view");
    }

    @Override
    public String queryViewJson(String name) {
        return Text.queryViewJson(name);
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
    }

}
