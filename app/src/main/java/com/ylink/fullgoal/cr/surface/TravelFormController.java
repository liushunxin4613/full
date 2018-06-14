package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IOnCom;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.TravelFormFg;

import static com.ylink.fullgoal.config.ComConfig.CC;

/**
 * 出差申请单控制器
 */
public class TravelFormController<T extends TravelFormController> extends AddController<T, TravelFormFg> {

    @Override
    public T initDB(TravelFormFg fg) {
        return super.initDB(fg);
    }

    @Override
    public T remove(TravelFormFg fg, IOnCom action) {
        return super.remove(fg, action);
    }

    @Override
    protected TravelFormFg getFilterDB(String key, TravelFormFg fg) {
        return null;
    }

    @Override
    public TravelFormFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<TravelFormFg> getClz() {
        return TravelFormFg.class;
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case CC:
                return "travel";
        }
        return super.getOnUBKey(key);
    }

}