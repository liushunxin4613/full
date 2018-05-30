package com.ylink.fullgoal.controllerApi.core;

import android.view.View;

import com.leo.core.core.BaseControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IObjAction;
import com.leo.core.iapi.IParseApi;
import com.leo.core.iapi.IUserApi;
import com.ylink.fullgoal.api.config.ParseApi;
import com.ylink.fullgoal.api.config.UrlApi;
import com.ylink.fullgoal.api.func.UserApi;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.config.Api;

import butterknife.ButterKnife;

import static com.ylink.fullgoal.config.Config.ROOT_URL;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T, C> {

    private String rootUrl = ROOT_URL;

    public ControllerApi(C controller) {
        super(controller);
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
    public IUserApi newUserApi() {
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
    public Class<? extends View> getRootViewClz() {
        return BaseControllerApiView.class;
    }

    @Override
    public UrlApi api() {
        return super.api();
    }

    public T api(IObjAction<UrlApi> action){
        if(action != null){
            action.execute(api());
        }
        return getThis();
    }

    @Override
    public UrlApi newApi() {
        return new UrlApi(getThis());
    }

    @Override
    public IParseApi newParseApi() {
        return new ParseApi();
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
    }

}
