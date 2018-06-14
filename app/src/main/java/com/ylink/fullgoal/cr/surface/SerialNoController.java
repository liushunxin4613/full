package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.TP;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 报销批次号控制器
 */
public class SerialNoController<T extends SerialNoController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case TP:
            case YB:
            case CC:
                return "serialNo";
        }
        return super.getOnUBKey(key);
    }

}
