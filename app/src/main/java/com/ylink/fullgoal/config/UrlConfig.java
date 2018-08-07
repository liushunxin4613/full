package com.ylink.fullgoal.config;

import static com.ylink.fullgoal.config.Config.LOCAL;

/**
 * 网络配置
 */
public class UrlConfig {

    private final static String FULL_TEST_HTTP = "http://192.168.41.123:7001/applications/";
    private final static String FULL_TEST1_HTTP = "http://192.168.8.114:8080/fuguo/";
    private final static String ROOT_IP = LOCAL ? "http://192.168.43.79/"
            : "http://111.231.231.226/";
    public final static String ROOT_URL = ROOT_IP + "app/fullApp/";//根api地址
    private final static String FG_ROOT_IP = LOCAL ? "http://192.168.8.102:8080/"
            : "http://192.168.8.108:8088/";
    //    public final static String FG_ROOT_URL = TEST ? FULL_TEST_HTTP + "/ssca/"
//            : FG_ROOT_IP + "ssca/";//fg调试地址
//    public final static String FG_ROOT_URL = TextUtils.equals(VERSION, VERSION_APP)
//        ? "http://192.168.8.109:8088/ssca/" : FULL_TEST_HTTP;
    public final static String FG_ROOT_URL = FULL_TEST_HTTP;
    private final static String UPLOAD_URL = ROOT_IP + "app/fullApp/upload";//根api地址
    public final static String UPLOAD_IMAGE_URL = UPLOAD_URL;//根api地址

    //请求参数
    //上传图片
    public final static String IMAGE_UPLOAD = "uploadImage";
    //报销提交
    public final static String REIMBURSE_SUBMIT = "FkSbumitCompensation";
    //报销获取
    public final static String REIMBURSE_QUERY = "QuerytCompensation";
    //报销列表获取
    public final static String REIMBURSE_QUERY_LIST = "QuerytReimbursement";

    //full

    //*** 图片提交备用地址(form表单限制) ***
    private final static String FULL_IMAGE_UPLOAD_V1 = "Image_upload.action";
    //*** 图片提交备用地址(form表单无限制) ***
    private final static String FULL_IMAGE_UPLOAD_V2 = "JFSHttpFileUploadServlet";
    //图片提交地址
    public final static String FULL_IMAGE_UPLOAD = FULL_IMAGE_UPLOAD_V1;
    //报销提交
    public final static String FULL_REIMBURSE_SUBMIT = "Task_submit.action";
    //报销获取
    public final static String FULL_REIMBURSE_QUERY = "Message_back.action";
    //报销列表获取
    public final static String FULL_REIMBURSE_QUERY_LIST = "Application_form.action";
    //分摊维度列表
    public final static String FULL_DIMENSION_LIST = "Dimension_list.action";
    //申诉
    public final static String FULL_APPEAL = "Complaint_task.action";

    //参数

    //报销列表请求状态参数(REIMBURSE_LIST_QUERY_STATUS)

    //待处理
    final static String REIMBURSE_LIST_QUERY_STATUS_DCL = "1";
    //审核中
    final static String REIMBURSE_LIST_QUERY_STATUS_SHZ = "2";
    //已完成
    final static String REIMBURSE_LIST_QUERY_STATUS_YWC = "3";
    //已取消
    final static String REIMBURSE_LIST_QUERY_STATUS_YQX = "4";

    //报销列表请求返回报销类型参数(REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE)

    //一般报销
    public final static String REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_YB = "一般报销";
    //出差报销
    public final static String REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_CC = "出差报销";

    //一般费用报销
    final static String REIMBURSE_QUERY_BILL_TYPE_YB = "427";
    //出差费用报销
    final static String REIMBURSE_QUERY_BILL_TYPE_CC = "428";

    //报销确认图片费用类型(MESSAGE_BACK_IMAGE_INVOICE_USE)

    //一般
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_YB = "一般";
    //交通
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_JT = "交通费";
    //住宿
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_ZS = "住宿费";
    //车船机票
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_CCJP = "车船机票费";

    //api

    public final static String PATH_QUERY_USER_DATA = "Reimburse_ment.action";//获取员工信息
    public final static String PATH_QUERY_DEPARTMENT_DATA = "Budget_department.action";//获取部门信息
    public final static String PATH_QUERY_PROJECT_DATA = "Project_list.action";//获取项目信息
    public final static String PATH_QUERY_CONTRACT_PAYMENT_DATA = "Contract_payment.action";//获取合同申请单
    public final static String PATH_QUERY_PROCESS_DATA = "Process_query.action";//获取招待申请单
    public final static String PATH_QUERY_COST_INDEX_DATA = "Cost_index.action";//获取费用指标
    public final static String PATH_QUERY_TRAVEL_FORM_DATA = "Travel_form.action";//获取出差申请单
    public final static String PATH_QUERY_RESEARCH_REPORT_DATA = "Research_report.action";//获取投研报告
    public final static String PATH_QUERY_CTRIP_TICKETS_DATA = "Ctrip_tickets.action";//获取携程机票
    public final static String PATH_QUERY_MESSAGE_BACK_DATA = FULL_REIMBURSE_QUERY;//获取报销确认信息
    public final static String PATH_QUERY_BANK_DATA = "Bank_mess.action";//获取银行卡报销信息
    public final static String PATH_QUERY_APPLICATION_FORM_DATA = "Application_form.action";//获取报销列表
    public final static String PATH_QUERY_DIMENSION_DATA = FULL_DIMENSION_LIST;//获取分摊维度列表
    public final static String PATH_QUERY_DIMENSION_INFORMATION_DATA = "Dimension_information.action";//获取分摊维度信息列表
    public final static String PATH_QUERY_APPLY = "Apply_compensation.action";//请求申请单信息
    public final static String PATH_QUERY_APPLY_CONTENT = "Apply_content.action";//请求申请单内容信息

    public final static String[] LOADING_DIALOGS = {
            PATH_QUERY_USER_DATA,//获取员工信息
            PATH_QUERY_DEPARTMENT_DATA,//获取部门信息
            PATH_QUERY_PROJECT_DATA,//获取项目信息
            PATH_QUERY_CONTRACT_PAYMENT_DATA,//获取合同申请单
            PATH_QUERY_PROCESS_DATA,//获取招待申请单
            PATH_QUERY_COST_INDEX_DATA,//获取费用指标
            PATH_QUERY_TRAVEL_FORM_DATA,//获取出差申请单
            PATH_QUERY_RESEARCH_REPORT_DATA,//获取投研报告
            PATH_QUERY_CTRIP_TICKETS_DATA,//获取携程机票
            PATH_QUERY_MESSAGE_BACK_DATA,//获取报销确认信息
            PATH_QUERY_BANK_DATA,//获取银行卡报销信息
            PATH_QUERY_APPLICATION_FORM_DATA,//获取报销列表
            PATH_QUERY_DIMENSION_DATA,//获取分摊维度列表
            PATH_QUERY_DIMENSION_INFORMATION_DATA,//获取分摊维度信息列表
            PATH_QUERY_APPLY,//请求申请单信息
            PATH_QUERY_APPLY_CONTENT,//请求申请单内容信息
    };

}
