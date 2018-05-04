package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.config.Config;
import com.leo.core.iapi.IRAction;
import com.leo.core.iapi.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.UserBean;

import static com.leo.core.config.Config.COOKIE;
import static com.leo.core.config.Config.IMAGE_URL;

public class UserApi<T extends UserApi> extends HasCoreControllerApi<T> implements IUserApi<T> {

    private final static String USER = Config.USER;

    public UserApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    protected String getDefTable() {
        return USER;
    }

    @Override
    public boolean isLogin() {
        return !TextUtils.isEmpty(getUserName());
    }

    @Override
    public T loginOut() {
        UserBean bean = getUser();
        if(bean != null){
            initUser(bean);
        }
        return getThis();
    }

    @Override
    public <R> T initUser(R user) {
        return getThis();
    }

    @Override
    public UserBean getUser() {
        return getBean(USER, UserBean.class);
    }

    @Override
    public String getUId() {
        return null;
    }

    @Override
    public String getUserName() {
//        return getFiled(UserBean::getUsername);
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUTime() {
        return null;
    }

    private String getFiled(IRAction<UserBean, String> action){
        return getExecute(getUser(), null, action);
    }

}
