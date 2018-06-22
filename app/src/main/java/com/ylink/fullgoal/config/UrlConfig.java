package com.ylink.fullgoal.config;

import static com.ylink.fullgoal.config.Config.LOCAL;

/**
 * 网络配置
 */
public class UrlConfig {

    private final static String ROOT_IP = LOCAL ? "http://192.168.43.79/"
            : "http://111.231.231.226/";
    public final static String ROOT_URL = ROOT_IP + "app/fullApp/";//根api地址
    private final static String FG_ROOT_IP = LOCAL ? "http://192.168.8.102:8080/"
            : "http://192.168.8.108:8088/";
    public final static String FG_ROOT_URL = FG_ROOT_IP + "ssca/";//fg调试地址
    public final static String UPLOAD_URL = ROOT_IP + "app/fullApp/upload";//根api地址
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

    //参数

    //报销列表请求状态参数(REIMBURSE_LIST_QUERY_STATUS)

    //待处理
    public final static String REIMBURSE_LIST_QUERY_STATUS_DCL = "1";
    //审核中
    public final static String REIMBURSE_LIST_QUERY_STATUS_SHZ = "2";
    //已完成
    public final static String REIMBURSE_LIST_QUERY_STATUS_YWC = "3";
    //已取消
    public final static String REIMBURSE_LIST_QUERY_STATUS_YQX = "4";

    //报销列表请求返回报销类型参数(REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE)

    //一般报销
    public final static String REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_YB = "一般报销";
    //出差报销
    public final static String REIMBURSE_LIST_QUERY_RETURN_BILL_TYPE_CC = "出差报销";

    //报销列表请求返回状态参数(REIMBURSE_LIST_QUERY_RETURN_STATUS)

    //金额确认
    public final static String REIMBURSE_LIST_QUERY_RETURN_STATUS_QR = "金额确认";
    //修改任务
    public final static String REIMBURSE_LIST_QUERY_RETURN_STATUS_XG = "修改任务";

    //报销请求类型参数(REIMBURSE_QUERY_BILL_TYPE)

    //一般费用报销
    public final static String REIMBURSE_QUERY_BILL_TYPE_YB = "427";
    //出差费用报销
    public final static String REIMBURSE_QUERY_BILL_TYPE_CC = "428";

    //报销确认图片费用类型(MESSAGE_BACK_IMAGE_INVOICE_USE)

    //一般
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_YB = "一般";
    //交通
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_JT = "交通费";
    //住宿
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_ZS = "住宿费";
    //车船机票
    public final static String MESSAGE_BACK_IMAGE_INVOICE_USE_CCJP = "车船机票费";

}
