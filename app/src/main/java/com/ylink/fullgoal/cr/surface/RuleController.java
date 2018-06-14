package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IOnCom;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.RuleFg;

import java.util.List;

/**
 * 错误列表控制器
 */
public class RuleController<T extends RuleController> extends AddController<T, RuleFg> {

    @Override
    public T initDB(RuleFg fg) {
        return super.initDB(fg);
    }

    @Override
    public T remove(RuleFg ruleFg, IOnCom action) {
        return super.remove(ruleFg, action);
    }

    @Override
    public RuleFg getDB() {
        return super.getDB();
    }

    @Override
    protected RuleFg getFilterDB(String key, RuleFg fg) {
        return null;
    }

    @Override
    protected Class<RuleFg> getClz() {
        return RuleFg.class;
    }

    @Override
    public List<RuleFg> getViewBean() {
        return super.getViewBean();
    }

}
