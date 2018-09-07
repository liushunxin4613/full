package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.Config.XG1;
import static com.ylink.fullgoal.config.Config.XG2;
import static com.ylink.fullgoal.config.Config.XG3;
import static com.ylink.fullgoal.config.Config.XG4;

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
        return toUB(db -> {
            switch (db){
                case XG1:
                    return "4";
                case XG2:
                    return "1";
                case XG3:
                    return "3";
                case XG4:
                    return "2";
            }
            return null;
        });
    }

}
