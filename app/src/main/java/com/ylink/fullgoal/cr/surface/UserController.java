package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.UserFg;

/**
 * 用户控制器
 */
public class UserController<T extends UserController> extends BaseStringController<T, UserFg> {

    @Override
    public T initDB(UserFg userFg) {
        return super.initDB(userFg);
    }

    @Override
    public UserFg getDB() {
        return super.getDB();
    }

    @Override
    protected String getOnUBKey(String key) {
        return toField(field -> {
            switch (field) {
                case "agent":
                    return "agentList";
                case "reimbursement":
                    return "reimbursementList";
            }
            return super.getOnUBKey(key);
        });
    }

    @Override
    public String getViewBean() {
        return vor(UserFg::getUserName);
    }

    @Override
    public String getApiCode() {
        return vor(UserFg::getUserCode);
    }

}