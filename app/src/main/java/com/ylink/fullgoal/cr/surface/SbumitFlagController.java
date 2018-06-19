package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
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
    public String getUB(String... args) {
        if(!TextUtils.isEmpty(getDB())){
            return "3";
        }
        return super.getUB(args);
    }

    public void open(){
        initDB("1");
    }

}
