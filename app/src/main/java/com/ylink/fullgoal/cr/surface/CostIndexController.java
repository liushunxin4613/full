package com.ylink.fullgoal.cr.surface;

import android.support.annotation.NonNull;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.CostFg;
import com.ylink.fullgoal.fg.DimenFg;

import java.util.List;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 费用指标控制器
 *
 * @param <T>
 */
public class CostIndexController<T extends CostIndexController> extends BaseController<T, CostFg, CostFg> {

    public T update(String money) {
        if (getDB() != null) {
            if (TextUtils.isEmpty(getDB().getAmount())) {
                getDB().setAmount(money);
            }
            if (TextUtils.isEmpty(getDB().getTaxAmount())) {
                getDB().setTaxAmount("0");
            }
            if (TextUtils.isEmpty(getDB().getExTaxAmount())) {
                getDB().setExTaxAmount("0");
            }
        }
        return getThis();
    }

    @Override
    public T initDB(CostFg costIndexFg) {
        return super.initDB(costIndexFg);
    }

    @Override
    public CostFg getDB() {
        return super.getDB();
    }

    @Override
    public String getViewBean() {
        return getCostName();
    }

    @Override
    public String getApiCode() {
        return vor(CostFg::getCostCode);
    }

    public String getCostName(){
        return vor(CostFg::getCostIndex);
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

    public T update(List<DimenFg> data) {
        if (check(getDB())) {
            getDB().setShare(TextUtils.isEmpty(data) || data.get(0) == null || data.get(0).isEmpty()
                    ? "无需分摊" : "需要分摊");
        }
        return getThis();
    }

    public T update(CostFg fg) {
        if (check(fg)) {
            if (check(getDB())) {
                getDB().setCostIndex(fg.getCostIndex());
                getDB().setCostCode(fg.getCostCode());
                getDB().setExplain(fg.getExplain());
            } else {
                initDB(fg);
            }
        }
        return getThis();
    }

    public T updateAmount(String amount) {
        if (getDB() != null) {
            getDB().setAmount(amount);
        }
        return getThis();
    }

    public T updateTaxAmount(String amount) {
        if (getDB() != null) {
            getDB().setTaxAmount(amount);
        }
        return getThis();
    }

    public T updateExTaxAmount(String amount) {
        if (getDB() != null) {
            getDB().setExTaxAmount(amount);
        }
        return getThis();
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
            case CC:
            case QR:
                return "cost";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected CostFg getOnUB(String key) {
        return getExecute(getDB(), new CostFg(), db -> {
            switch (key) {
                case YB:
                case CC:
                case QR:
                    return getDB();
            }
            return super.getOnUB(key);
        });
    }

}