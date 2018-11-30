package com.ylink.fullgoal.cr.core;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

public class BaseStringController<T extends BaseStringController, DB> extends BaseController<T, DB, DB>{

    @Override
    public String getViewBean(){
        return null;
    }

    @Override
    protected DB getOnUB(String key) {
        switch (key){
            case YB:
            case CC:
                return getDB();
        }
        return super.getOnUB(key);
    }

}
