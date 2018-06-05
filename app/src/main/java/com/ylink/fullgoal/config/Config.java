package com.ylink.fullgoal.config;

public class Config {

    //固定参数
    public final static boolean DEBUG = false;//debug模式是否开启
    public final static boolean LOCAL = false;//本地模式是否开启
    public final static int SOCKET_OUT_TIME = 10000;//网络超时时间
    public final static int BACK_PRESSED_INTERVAL = 2000;//退出间隔时间
    public final static int TIME_SPLASH = 1;//引导页时间
    public final static String HTTP_SUCCESS = "AAAAAA";//引导页时间
    public final static String[][] FIELDS = {
            {"agent", "经办人"},
            {"reimbursement", "报销人"},
            {"fillDate", "发起日期"},
            {"serialNo", "报销流水号"},
            {"approvalStatus", "审批状态"},
            {"fkApprovalNum", "费控审批批次号"},
            {"channel", "渠道"},
            {"budgetDepartment", "预算归属部门"},
            {"totalAmountLower", "金额"},
            {"project", "项目"},
            {"paymentRequest", "合同申请单"},
            {"serveBill", "招待申请单"},
            {"cause", "事由"},
            {"billType", "报销类型"},
            {"totalAmountLower", "金额"},
            {"isTickets", "是否专票"},
            {"sbumitFlag", "提交标志"},
            {"traveList", "出差申请单编号集合"},
            {"reportName", "投研报告编号集合"},
            {"ticketList", "携程机票编号集合"},
            {"imageList", "影像集合"},
    };
    public final static String[][] APPROVAL_STATUS = {
            {"待处理", "1"},
            {"审核中", "2"},
            {"已完成", "3"},
            {"已取消", "4"},
    };

    //记录参数
    public static int MAIN_FRAGMENT_INDEX = 0;//主页面默认Fragment

    //固定键值
    public final static String STATE = "state";
    public final static String SERIAL_NO = "serialNo";
    public final static String SEARCH = "search";

}
