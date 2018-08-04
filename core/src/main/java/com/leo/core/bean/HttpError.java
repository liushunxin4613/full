package com.leo.core.bean;

public final class HttpError {

    public final static int ERROR_CONNECT = 0x1190;//网络异常
    public final static int ERROR_HTTP = 0x1191;//服务器异常
    public final static int ERROR_SOCKET_TIME_OUT = 0x1192;//网络连接超时异常
    public final static int ERROR_JSON_PARSE = 0x1193;//json解析异常
    public final static int ERROR_DATA_NULL = 0x1194;//数据为空异常
    public final static int ERROR_DATA_METHOD = 0x1195;//数据解析方法异常
    public final static int ERROR_STRING_ENCODE = 0x1196;//string编码转换异常
    public final static int ERROR_DATA = 0x1197;//数据异常
    public final static int ERROR_NO_ROUTE_TO_HOST = 0x1198;//没有到主机的路由
    public final static int ERROR_JSON_ERROR = 0x1199;//不是json数据参数

    private int what;
    private String msg;
    private Throwable e;

    public HttpError(int what, String msg, Throwable e) {
        this.what = what;
        this.msg = msg;
        this.e = e;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }

}
