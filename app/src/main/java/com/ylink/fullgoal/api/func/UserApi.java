package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.config.Config;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.UserFg;

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
        return getThis();
    }

    @Override
    public <R> T initUser(R user) {
        return getThis();
    }

    @Override
    public String getDepartmentCode() {
        return getDepartment().getDepartmentCode();
    }

    /*@Override
    public UserBean getUser() {
        return getBean(USER, UserBean.class);
    }*/

    @Override
    public UserFg getUser() {
        return new UserFg(getUId(), getUserName());
    }

    @Override
    public String getUId() {
//        return "12001324";
        return "3";
    }

    @Override
    public String getUserName() {
        return "张3";
    }

    @Override
    public DepartmentFg getDepartment() {
        return new DepartmentFg("3", "3部门");
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUTime() {
        return null;
    }

}
