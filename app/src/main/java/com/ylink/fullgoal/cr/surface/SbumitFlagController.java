package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.ComConfig.XQ;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 提交标识控制器
 */
public class SbumitFlagController<T extends SbumitFlagController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
            case CC:
                return "sbumitFlag";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case FQ:
                return "1";//新增影响修改
            case QR:
            case XG:
            case XQ:
                return "2";//正常修改提交
        }
        return super.getOnUB(key);
    }

}
