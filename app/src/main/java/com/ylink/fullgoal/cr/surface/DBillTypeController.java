package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.Config.D1;
import static com.ylink.fullgoal.config.Config.D2;
import static com.ylink.fullgoal.config.Config.D3;
import static com.ylink.fullgoal.config.Config.D4;
import static com.ylink.fullgoal.config.Config.D_BT1;
import static com.ylink.fullgoal.config.Config.D_BT2;

public class DBillTypeController<T extends DBillTypeController> extends StringController<T> {

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case D1:
            case D2:
            case D3:
            case D4:
                return "billType";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        return no(toUB(db -> {
            switch (db){
                case D_BT1:
                    return "427";
                case D_BT2:
                    return "428";
            }
            return null;
        }));
    }

}
