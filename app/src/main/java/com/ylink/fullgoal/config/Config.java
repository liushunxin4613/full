package com.ylink.fullgoal.config;

public class Config {

    //固定参数
    public final static boolean DEBUG = false;//debug模式是否开启
    public final static boolean WIFI = true;//本地模式是否开启
    public final static String ROOT_IP = WIFI ? "http://192.168.43.79/" : "http://192.168.42.198/";
    public final static String ROOT_URL = ROOT_IP + "app/fullApp/";//根api地址
    public final static String UPLOAD_URL = ROOT_IP + "app/fullApp/upload";//根api地址
    public final static String UPLOAD_IMAGE_URL = UPLOAD_URL;//根api地址
    public final static int SOCKET_OUT_TIME = 10000;//网络超时时间
    public final static int BACK_PRESSED_INTERVAL = 2000;//退出间隔时间
    public final static int TIME_SPLASH = 1;//引导页时间

    //记录参数
    public static int MAIN_FRAGMENT_INDEX = 0;//主页面默认Fragment

    //固定键值
    public final static String TITLE = "title";
    public final static String REIMBURSE_TYPE = "reimburseType";
    public final static String STATE = "state";
    public final static String SEARCH = "search";

}
