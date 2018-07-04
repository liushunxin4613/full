package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.DoubleController;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 金额控制器
 */
public class MoneyController<T extends MoneyController> extends DoubleController<T> {

    @Override
    public T initDB(Double s) {
        return super.initDB(s);
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
            case CC:
                return "totalAmountLower";
            case QR:
                return "shareAmount";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case QR:
                return getDBMoney();
        }
        return super.getOnUB(key);
    }

}
