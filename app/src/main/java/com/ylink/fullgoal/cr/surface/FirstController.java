package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.FQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.TP;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 报销批次号控制器
 */
public class FirstController<T extends FirstController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case TP:
            case YB:
            case CC:
                return "first";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        String first = getFirst();
        switch (key){
            case TP:
            case YB:
            case CC:
                return first;
        }
        return super.getOnUB(key);
    }

    private String getFirst() {
        if (!TextUtils.isEmpty(getDB())) {
            switch (getDB()) {
                case FQ://发起
                    return "1";
                case QR://确认
                    return "2";
                case XG://修改
                    return "3";
            }
        }
        return null;
    }

}
