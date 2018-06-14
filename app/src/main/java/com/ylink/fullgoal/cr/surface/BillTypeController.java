package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.TP;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 报销类型控制器
 */
public class BillTypeController<T extends BillTypeController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
            case CC:
                return "billType";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key){
            case YB:
                return "1";
            case CC:
                return "2";
        }
        return super.getOnUB(key);
    }

}
