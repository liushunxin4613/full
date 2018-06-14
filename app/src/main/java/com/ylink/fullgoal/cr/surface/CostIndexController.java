package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.CostFg;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 费用指标控制器
 *
 * @param <T>
 */
public class CostIndexController<T extends CostIndexController> extends BaseController<T, CostFg> {

    @Override
    public T initDB(CostFg costIndexFg) {
        return super.initDB(costIndexFg);
    }

    @Override
    public CostFg getDB() {
        return super.getDB();
    }

    @Override
    public Class<CostFg> getClz() {
        return CostFg.class;
    }

    @Override
    public String getViewBean() {
        return no(CostFg::getCostIndex);
    }

    @Override
    public CostFg getUB(String... args) {
        return super.getUB(args);
    }

    @Override
    protected CostFg getDefUB() {
        return super.getDefUB();
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
                return "cost";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected CostFg getOnUB(String key) {
        return toUB(db -> {
            switch (key) {
                case YB:
                    return getDB();
            }
            return super.getOnUB(key);
        });
    }

}