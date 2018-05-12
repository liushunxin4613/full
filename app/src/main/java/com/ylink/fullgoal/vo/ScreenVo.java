package com.ylink.fullgoal.vo;

/**
 * 筛选
 */
public class ScreenVo {

    //经办人
    private String agent;
    //报销状态 待处理、审核中、已完成、已取消四类
    private String ReimbursementState;
    //时间区间 当天、七天、一个月、三个月、六个月、一年
    private String timeInterval;
    //金额区间最低值
    private String minMoney;
    //金额区间最高值
    private String maxMoney;
    //事由关键字
    private String causeKeyword;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getReimbursementState() {
        return ReimbursementState;
    }

    public void setReimbursementState(String reimbursementState) {
        ReimbursementState = reimbursementState;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getCauseKeyword() {
        return causeKeyword;
    }

    public void setCauseKeyword(String causeKeyword) {
        this.causeKeyword = causeKeyword;
    }

}
