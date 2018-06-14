package com.ylink.fullgoal.vo1;

/**
 * 流程
 */
public class ProcessVo {

    //处理时间
    private String time;
    //操作人
    private String user;
    //节点
    private String node;
    //审批意见
    private String approvalOpinion;

    public ProcessVo(String time, String user, String node, String approvalOpinion) {
        this.time = time;
        this.user = user;
        this.node = node;
        this.approvalOpinion = approvalOpinion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

}
