package com.ylink.fullgoal.hb;

import java.util.ArrayList;
import java.util.List;

public class TestHb {

    private int i;
    private float f;
    private double d;
    private boolean b;
    private char c;
    private String name;
    private String title;
    private String detail;
    private List<String> data;
    private ChildHb child;

    public TestHb() {
        this.name = "name";
        this.title = "";
        this.detail = null;
        this.data = new ArrayList<>();
        this.child = new ChildHb();
    }

    public static class ChildHb{

    }

}
