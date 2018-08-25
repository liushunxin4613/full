package com.leo.core.config;

public class Config {

    //系统配置

    //app json模拟
    public final static String APP_JSON = "app://json/";
    public final static String APP_XML = "app://xml/";

    //超时时间(单位:秒)
    public final static int TIME_OUT_SECONDS = 15;
    private final static int FPS = 60;//FPS
    private final static int MAX_FPS = 80;//FPS
    public final static float FPS_TIME = (float) 1000 / FPS;//FPS时间
    public final static float MIN_FPS_TIME = (float) 1000 / MAX_FPS;//最小FPS时间

    //其他

    public final static String STATUS_BAR_KEY = "statusBarKey";
    public final static String STATUS_BAR_KEY_LIGHT = "statusBarKeyLight";
    public final static String STATUS_BAR_VALUE = "statusBarValue";
    public final static String COOKIE = "Set-Cookie";
    public final static String IMAGE_URL = "imageUrl";
    public final static String IMAGE_URL_START
            = "http://mall.sharecentury.cn:9000/static/uploaded/files/";
    public final static String IMAGE_PATH = "/Gallery/Pictures";
    public final static String FINISH = "finish";
    public final static String EMPTY = " ";
    public final static String RX = "\\\\/";
    public final static String RX_TO = "/";

}
