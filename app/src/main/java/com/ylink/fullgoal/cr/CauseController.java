package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.StringController;

/**
 * 事由控制器
 */
public class CauseController<T extends CauseController> extends StringController<T> {

    @Override
    protected String getDefUBKey() {
        return null;
    }

}
