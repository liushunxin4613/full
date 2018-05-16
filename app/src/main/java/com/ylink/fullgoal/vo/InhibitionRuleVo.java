package com.ylink.fullgoal.vo;

/**
 * 禁止规则
 */
public class InhibitionRuleVo {

    public static final String STATE_RED = "退回";
    public static final String STATE_YELLOW = "需特批";

    //名称
    private String name;
    //状态 红灯(直接退回规则)、黄灯(需要特批规则)
    private String state;
    //信息
    private String detail;

    public InhibitionRuleVo(String name, String state, String detail) {
        this.name = name;
        this.state = state;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
