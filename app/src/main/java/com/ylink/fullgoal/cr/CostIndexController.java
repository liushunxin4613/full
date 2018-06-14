package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.CostIndexFg;

/**
 * 费用指标控制器
 * @param <T>
 */
public class CostIndexController<T extends CostIndexController> extends BaseStringController<T, CostIndexFg> {

    @Override
    public T initDB(CostIndexFg costIndexFg) {
        return super.initDB(costIndexFg);
    }

    @Override
    public CostIndexFg getDB() {
        return super.getDB();
    }

    @Override
    public Class<CostIndexFg> getClz() {
        return CostIndexFg.class;
    }

    @Override
    public String getViewBean() {
        return no(CostIndexFg::getName);
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected <UB> UB getDefUB() {
        return null;
    }

}