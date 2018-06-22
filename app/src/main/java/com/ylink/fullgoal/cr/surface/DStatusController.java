package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.Config.D1;
import static com.ylink.fullgoal.config.Config.D2;
import static com.ylink.fullgoal.config.Config.D3;
import static com.ylink.fullgoal.config.Config.D4;

public class DStatusController<T extends DStatusController> extends StringController<T> {

    @Override
    public T initDB(String s) {
        return super.initDB(s);
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case D1:
            case D2:
            case D3:
            case D4:
                return "status";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case D1:
                return "1";
            case D2:
                return "2";
            case D3:
                return "3";
            case D4:
                return "4";
        }
        return super.getOnUB(key);
    }

}
