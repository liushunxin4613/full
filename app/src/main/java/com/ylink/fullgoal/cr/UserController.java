package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.UserList;

/**
 * 用户控制器
 */
public class UserController<T extends UserController> extends BaseStringController<T, UserList> {

    @Override
    public T initDB(UserList userList) {
        return super.initDB(userList);
    }

    @Override
    public UserList getDB() {
        return super.getDB();
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected <UB> UB getDefUB() {
        return null;
    }

    @Override
    public Class<UserList> getClz() {
        return UserList.class;
    }

    @Override
    public String getViewBean() {
        return no(UserList::getName);
    }

}
