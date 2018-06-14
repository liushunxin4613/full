package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.StringController;

/**
 * 报销批次号控制器
 */
public class SerialNoController<T extends SerialNoController> extends StringController<T> {

    @Override
    protected String getDefUBKey() {
        return "serialNo";
    }

}
