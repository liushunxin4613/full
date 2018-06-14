package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.XG;

/**
 * 提交标识控制器
 */
public class LogoController<T extends LogoController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case XG:
                return "logo";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        return super.getOnUB(key);
    }

}
