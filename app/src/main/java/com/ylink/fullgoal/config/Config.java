package com.ylink.fullgoal.config;

import com.ylink.fullgoal.api.api.RouteApi;

import static com.ylink.fullgoal.config.ComConfig.MQZ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.QZ;
import static com.ylink.fullgoal.config.ComConfig.XG;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_STATUS_DCL;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_STATUS_SHZ;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_STATUS_YQX;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_LIST_QUERY_STATUS_YWC;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_QUERY_BILL_TYPE_CC;
import static com.ylink.fullgoal.config.UrlConfig.REIMBURSE_QUERY_BILL_TYPE_YB;

public class Config {

    //版本
    private final static String VERSION_APP = RouteApi.MODULE_APP;

    //配置
    public final static String VERSION = VERSION_APP;//版本
    public final static boolean TEMPLATE_FULL = true;//template下载方式
    public final static boolean DEBUG = false;//debug模式是否开启5
    final static boolean LOCAL = false;//本地模式是否开启
    public final static boolean TEST = true;//测试模式是否开启
    public final static boolean SIMULATE_HTTP = false;//模拟模式是否开启
    public final static int SOCKET_OUT_TIME = 10000;//网络超时时间
    public final static int BACK_PRESSED_INTERVAL = 2000;//退出间隔时间
    public final static int TIME_SPLASH = 1;//引导页时间
    public final static String HTTP_SUCCESS = "AAAAAA";//接口成功标志

    public final static String[][] FIELDS = {
            {"agentList", "经办人"},
            {"reimbursementList", "报销人"},
            {"fillDate", "发起日期"},
            {"serialNo", "报销流水号"},
            {"approvalStatus", "审批状态"},
            {"fkApprovalNum", "费控审批批次号"},
            {"channel", "渠道"},
            {"budgetDepartment", "预算归属部门"},
            {"totalAmountLower", "金额"},
            {"project", "项目"},
            {"paymentRequest", "合同申请单"},
            {"entertainrequest", "招待申请单"},
            {"cause", "事由"},
            {"billType", "报销类型"},
            {"totalAmountLower", "金额"},
            {"isTickets", "是否专票"},
            {"sbumitFlag", "提交标志"},
            {"traveList", "出差申请单编号集合"},
            {"reportName", "投研报告编号集合"},
            {"ticketList", "携程机票编号集合"},
            {"imageList", "影像集合"},
            {"cost", "费用指标"},
            {"first", "是否是第一次提交"},
            {"taskType", "任务类型"},
            {"costList", "费用指标集合"},
            {"shareList", "分摊列表"},
            {"ruleList", "评审结果集"},
    };
    public final static String[][] APPROVAL_STATUS = {
            {"待处理", REIMBURSE_LIST_QUERY_STATUS_DCL},
            {"审核中", REIMBURSE_LIST_QUERY_STATUS_SHZ},
            {"已完成", REIMBURSE_LIST_QUERY_STATUS_YWC},
            {"已取消", REIMBURSE_LIST_QUERY_STATUS_YQX},
    };
    public final static String[][] BILL_TYPES = {
            {"一般费用报销", REIMBURSE_QUERY_BILL_TYPE_YB},
            {"出差费用报销", REIMBURSE_QUERY_BILL_TYPE_CC},
    };
    public final static String[][] BILL_TYPE_TITLES = {
            {"报销确认", QR},
            {"报销修改", XG},
    };
    public final static String[][] DATES = {
            {"当天", "1"},
            {"七天", "2"},
            {"一个月", "3"},
            {"三个月", "4"},
            {"六个月", "5"},
            {"一年", "6"},
    };
    public final static String[][] FULL_STATUS = {
            //前置(无金额)
            {"初始化任务", QZ},
            //活动
            {"金额确认", QR},
            {"修改任务", XG},
            //金额前置
            {"自动审核", MQZ},
            {"申诉任务", MQZ},
            //后置(有费控批次,和流程节点)
    };

    public final static String XG1 = "申请特批";
    public final static String XG2 = "我不要了";
    public final static String XG3 = "修改提交";
    public final static String XG4 = "取消报销";

    public final static String D1 = "待处理";
    public final static String D2 = "审核中";
    public final static String D3 = "已完成";
    public final static String D4 = "已取消";

    public final static String D_DATE1 = "当天";
    public final static String D_DATE2 = "七天";
    public final static String D_DATE3 = "一个月";
    public final static String D_DATE4 = "三个月";
    public final static String D_DATE5 = "六个月";
    public final static String D_DATE6 = "一年";

    public final static String D_BT1 = "一般费用报销";
    public final static String D_BT2 = "出差费用报销";

    //记录参数
    public static int MAIN_FRAGMENT_INDEX = 0;//主页面默认Fragment

    //固定键值
    public final static String TAG = "tag";
    public final static String STATE = "state";
    public final static String TITLE = "title";
    public final static String SERIAL_NO = "serialNo";
    public final static String JSON = "json";
    public final static String MAIN_APP = "mainApp";
    public final static String COST = "cost";
    public final static String COST_LIST = "cost";
    public final static String SEARCH = "search";
    public final static String SEARCH_EVECTION = "searchEvection";
    public final static String SEARCH_TITLE = "searchTitle";
    public final static String FILTERS = "filters";
    public final static String KEY = "key";
    public final static String VALUE = "value";
    public final static String DATA_QR = "dataQR";
    public final static String MONEY = "totalAmountLower";
    public final static String CASTGC = "CASTGC";

}