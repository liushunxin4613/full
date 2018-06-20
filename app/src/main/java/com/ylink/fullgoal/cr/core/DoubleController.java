package com.ylink.fullgoal.cr.core;

import android.support.annotation.NonNull;

import com.leo.core.util.TextUtils;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

public abstract class DoubleController<T extends DoubleController> extends BaseController<T, Double, String> {

    @Override
    public T initDB(Double s) {
        return super.initDB(s);
    }

    @Override
    public Double getDB() {
        return super.getDB();
    }

    public double getdouble(){
        return getDB() == null ? 0 : getDB();
    }

    @Override
    public Class<Double> getClz() {
        return Double.class;
    }

    @Override
    public String getViewBean() {
        return TextUtils.getMoneyString(getDB());
    }

    @Override
    protected String getOnUBKey(String key) {
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        switch (key) {
            case YB:
            case CC:
                return TextUtils.getMoneyString(getDB());
        }
        return super.getOnUB(key);
    }

    @Override
    public String getUB(String... args) {
        return super.getUB(args);
    }

    @NonNull
    @Override
    public String getSafeUB(String... args) {
        return super.getSafeUB(args);
    }

    @NonNull
    @Override
    protected String getNoneUB() {
        return "";
    }

    @Override
    protected Class<String> getUBClz() {
        return null;
    }

}