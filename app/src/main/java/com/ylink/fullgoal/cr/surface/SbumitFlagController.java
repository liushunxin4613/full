package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 提交标识控制器
 * 1.新影像上传;
 * 2.无金额修改;
 * 3.有金额修改;
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
        if (!TextUtils.isEmpty(getDB())) {
            return "3";
        }
        return super.getUB(args);
    }

    public void open() {
        initDB("1");
    }

    public boolean isOpen() {
        return TextUtils.equals("1", getDB());
    }

}
