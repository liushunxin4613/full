package com.leo.core.bean;

public class Bol {

    private String name;
    private boolean bol;

    public Bol(boolean bol) {
        this.bol = bol;
    }

    public Bol(String name, boolean bol) {
        this.name = name;
        this.bol = bol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }

}
