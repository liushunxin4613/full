package com.ylink.fullgoal.controllerApi.core;

import android.view.View;

import com.leo.core.core.BaseControllerApi;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.IUserApi;
import com.leo.core.net.RetrofitSubscriber;
import com.leo.core.other.ParamType;
import com.ylink.fullgoal.api.func.UserApi;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.config.Api;

import java.lang.reflect.Type;

import butterknife.ButterKnife;
import rx.Subscriber;

import static com.ylink.fullgoal.config.Config.ROOT_URL;

public class ControllerApi<T extends ControllerApi, C> extends BaseControllerApi<T,C> {

    private String rootUrl = ROOT_URL;

    public ControllerApi(C controller) {
        super(controller);
    }

    @Override
    public boolean isLogin() {
        boolean isLogin = super.isLogin();
        if(!isLogin){
//            startActivity(LoginActivity.class);
        }
        return isLogin;
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
    public <R> Subscriber<R> newSubscriber() {
        return new RetrofitSubscriber();
    }

    @Override
    public void initView() {
        if (getRootView() != null)
            ButterKnife.bind(this, getRootView());
    }

    //自定义
    protected ParamType get(Type rawType, Type... typeArguments){
        return ParamType.get(rawType, typeArguments);
    }

}
