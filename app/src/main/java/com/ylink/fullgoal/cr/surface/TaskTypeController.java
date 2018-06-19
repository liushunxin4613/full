package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 提交标识控制器
 */
public class TaskTypeController<T extends TaskTypeController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
            case CC:
                return "taskType";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case FQ:
                return "1";
            case QR:
                return "3";
            case XG:
                return "6";
        }
        return super.getOnUB(key);
    }

}
