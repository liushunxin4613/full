package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.StringController;

/**
 * 状态控制器
 */
public class StateController<T extends StateController> extends StringController<T> {

    @Override
    protected String getDefUBKey() {
        return null;
    }

}
