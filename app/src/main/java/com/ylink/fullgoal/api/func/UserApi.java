package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.fg.DepartmentFg;

public class UserApi<T extends UserApi> extends HasCoreControllerApi<T> implements IUserApi<T> {

    public UserApi(CoreControllerApi controllerApi) {
        super(controllerApi);
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
        if (user instanceof UserBean) {
            saveData("user", user);
        }
        return getThis();
    }

    @Override
    public <A> T initDepartment(A department) {
        if(department instanceof DepartmentFg){
            saveData("department", department);
        }
        return getThis();
    }

    @Override
    public String getDepartmentCode() {
        return vr(getDepartment(), DepartmentFg::getDepartmentCode);
    }

    @Override
    public UserBean getUser() {
        return getBean("user", UserBean.class);
    }

    @Override
    public String getUId() {
//        return no(vr(getUser(), UserBean::getUserId), "xijiong");
        return vr(getUser(), UserBean::getUserId);
    }

    @Override
    public String getUserName() {
//        return no(vr(getUser(), UserBean::getUsername), "溪炯");
        return vr(getUser(), UserBean::getUsername);
    }

    @Override
    public String getCastgc() {
        return vr(getUser(), UserBean::getCastgc);
    }

    @Override
    public DepartmentFg getDepartment() {
//        return new DepartmentFg("f43368e1-8bd5-4ee3-aa51-2006c0489f1b", "固定收益投资部");
        return getBean("department", DepartmentFg.class);
    }

}