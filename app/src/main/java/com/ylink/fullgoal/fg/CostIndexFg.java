package com.ylink.fullgoal.fg;

/**
 * 费用指标
 */
public class CostIndexFg {

    /**
     * code : 0000
     * name : 指标1
     */

    private String code;
    private String name;

    public CostIndexFg(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
