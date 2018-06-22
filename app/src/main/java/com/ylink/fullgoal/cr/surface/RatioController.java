package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.StringController;

import static com.ylink.fullgoal.config.ComConfig.QR;

/**
 * 比例控制器
 */
public class RatioController<T extends RatioController> extends StringController<T> {

    @Override
    public T initDB(String s) {
        return super.initDB(s);
    }

    @Override
    public String getDB() {
        return super.getDB();
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case QR:
                return "shareRatio";
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case QR:
                return getDB();
        }
        return super.getOnUB(key);
    }

}
