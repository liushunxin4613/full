package com.ylink.fullgoal.cr.surface;

import android.support.annotation.NonNull;

import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.CostFg;

import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 费用指标控制器
 *
 * @param <T>
 */
public class CostIndexController<T extends CostIndexController> extends BaseController<T, CostFg, CostFg> {

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

    @NonNull
    @Override
    protected CostFg getNoneUB() {
        return null;
    }

    @Override
    protected Class<CostFg> getUBClz() {
        return CostFg.class;
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