package com.leo.core.net;

import com.alibaba.fastjson.JSON;
import com.leo.core.bean.NewFieldBean;
import com.leo.core.util.TextUtils;

/**
 * 拦截器
 */
public class InterceptorBean extends NewFieldBean {

    private final static transient boolean OPEN = true;
    private final static transient String ROOT_URL = "http://192.168.8.102/app/api/";
    private final static transient String PATH = "uploadHttp";

    private String url;
    private String code;
    private String time;
    private String head;
    private String method;
    private String params;
    private String requestSize;
    private String responseSize;
    private Object response;

    private transient HttpHelper helper;

    public InterceptorBean() {
        try {
            helper = new HttpHelper(ROOT_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completed() {
        if (check()) {
            helper.post(PATH, getFilterMap("url", "params", "response"));
        }
    }

    public boolean check() {
        return OPEN && helper != null && !TextUtils.isEmpty(url) &&
                !url.startsWith(helper.getRootUrl());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        return JSON.toJSONString(response);
    }

    public void setResponse(String response) {
        this.response = JSON.parse(response);
    }

    public String getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(String requestSize) {
        this.requestSize = requestSize;
    }

    public String getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(String responseSize) {
        this.responseSize = responseSize;
    }

}
