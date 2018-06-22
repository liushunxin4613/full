package com.ylink.fullgoal.cr.surface;

import android.support.annotation.NonNull;

import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.CostFg;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.ComConfig.QR;
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
        /*if(!check(getDB())){
            initDB(new CostFg("009", "差旅费"));
        }*/
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

    public T update(boolean share) {
        if (check(getDB())) {
            getDB().setShare(share ? "需要分摊" : "无需分摊");
        }
        return getThis();
    }

    public T update(CostFg fg) {
        if (check(fg)) {
            if(check(getDB())){
                getDB().setCostIndex(fg.getCostIndex());
                getDB().setCostCode(fg.getCostCode());
                getDB().setExplain(fg.getExplain());
            } else {
                initDB(fg);
            }
        }
        return getThis();
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
            case QR:
                return "cost";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected CostFg getOnUB(String key) {
        return toUB(db -> {
            switch (key) {
                case YB:
                case QR:
                    return getDB();
            }
            return super.getOnUB(key);
        });
    }

}