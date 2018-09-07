package com.ylink.fullgoal.api.func;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.api.IUserApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.UserBean;
import com.ylink.fullgoal.factory.CacheFactory;
import com.ylink.fullgoal.fg.DepartmentFg;

public class UserApiV1<T extends UserApiV1> extends HasCoreControllerApi<T> implements IUserApi<T> {

    public UserApiV1(CoreControllerApi controllerApi) {
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
            CacheFactory.getInstance().setUserBean((UserBean) user);
        }
        return getThis();
    }

    @Override
    public <A> T initDepartment(A department) {
        if (department instanceof DepartmentFg) {
            CacheFactory.getInstance().setDepartmentFg((DepartmentFg) department);
        }
        return getThis();
    }

    @Override
    public String getDepartmentCode() {
        return vr(getDepartment(), DepartmentFg::getDepartmentCode);
    }

    @Override
    public UserBean getUser() {
        return CacheFactory.getInstance().getUserBean();
    }

    @Override
    public DepartmentFg getDepartment() {
        return CacheFactory.getInstance().getDepartmentFg();
    }

    @Override
    public String getUId() {
        return vr(getUser(), UserBean::getUserId);
    }

    @Override
    public String getUserName() {
        return vr(getUser(), UserBean::getUsername);
    }

    @Override
    public String getCastgc() {
        return vr(getUser(), UserBean::getCastgc);
    }

}