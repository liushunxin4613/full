package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.config.Config;
import com.leo.core.iapi.IReturnAction;
import com.leo.core.iapi.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.UserBean;

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
        return "张三";
    }

    @Override
    public String getDepartment() {
        return "信息技术部";
    }

    @Override
    public String getDepartmentCode() {
        return "20180507001";
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUTime() {
        return null;
    }

    private String getFiled(IReturnAction<UserBean, String> action){
        return getExecute(getUser(), null, action);
    }

}
