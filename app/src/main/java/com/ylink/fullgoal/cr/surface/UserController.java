package com.ylink.fullgoal.cr.surface;

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
    protected String getOnUBKey(String key) {
        return toField(field -> {
            switch (field){
                case "agent":
                    return "agentList";
                case "reimbursement":
                    return "reimbursementList";
            }
            return super.getOnUBKey(key);
        });
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
