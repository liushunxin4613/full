package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.leo.core.util.TextUtils.count;
import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 事由控制器
 */
public class CauseController<T extends CauseController> extends StringController<T> {

    @Override
    public T initDB(String s) {
        if(count(s) <= 0){
            s = null;
        }
        return super.initDB(s);
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
            case CC:
                return "cause";
        }
        return super.getOnUBKey(key);
    }

}
