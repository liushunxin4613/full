package com.ylink.fullgoal.cr.core;

public abstract class StringController<T extends StringController> extends BaseStringController<T, String> {

    @Override
    public T initDB(String s) {
        return super.initDB(s);
    }

    @Override
    public String getDB() {
        return super.getDB();
    }

    @Override
    public Class<String> getClz() {
        return String.class;
    }

    @Override
    public String getViewBean() {
        return getDB();
    }

    @Override
    protected String getOnUBKey(String key) {
        return super.getOnUBKey(key);
    }

    @Override
    protected String getOnUB(String key) {
        return super.getOnUB(key);
    }

    @Override
    public String getUB(String... args) {
        return super.getUB(args);
    }

}