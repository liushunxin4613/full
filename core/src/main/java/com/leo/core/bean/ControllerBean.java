package com.leo.core.bean;

public class ControllerBean {

    private String type;
    private Object db;
    private Object viewBean;
    private Entry<String, Object> up;

    public ControllerBean(String type, Object db, Object viewBean, Entry<String, Object> up) {
        this.type = type;
        this.db = db;
        this.viewBean = viewBean;
        this.up = up;
    }

    public Object getDb() {
        return db;
    }

    public void setDb(Object db) {
        this.db = db;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getViewBean() {
        return viewBean;
    }

    public void setViewBean(Object viewBean) {
        this.viewBean = viewBean;
    }

    public Entry<String, Object> getUp() {
        return up;
    }

    public void setUp(Entry<String, Object> up) {
        this.up = up;
    }

}