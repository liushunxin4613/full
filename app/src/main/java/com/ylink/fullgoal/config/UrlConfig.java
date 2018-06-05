package com.ylink.fullgoal.config;

import static com.ylink.fullgoal.config.Config.LOCAL;

/**
 * 网络配置
 */
public class UrlConfig {

    public final static String ROOT_IP = LOCAL ? "http://192.168.43.79/" : "http://111.231.231.226/";
    public final static String ROOT_URL = ROOT_IP + "app/fullApp/";//根api地址
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

}
