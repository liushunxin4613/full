package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.config.Config;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.fg.DepartmentFg;
import com.ylink.fullgoal.fg.UserList;

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
    public UserList getUser() {
        return new UserList(getUId(), getUserName());
    }

    @Override
    public String getUId() {
//        return "12001324";
        return "001";
    }

    @Override
    public String getUserName() {
        return "李四";
    }

    @Override
    public DepartmentFg getDepartment() {
        return new DepartmentFg("20180507001", "信息技术部");
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
