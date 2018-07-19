package com.ylink.fullgoal.config;

public class Mvc {

    private String key;
    private String value;
    private String view;

    public Mvc(String key, String view) {
        this.key = key;
        this.view = view;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}