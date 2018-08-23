package com.leo.core.net;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.leo.core.bean.NewFieldBean;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Field;

/**
 * 拦截器
 */
public class InterceptorBean extends NewFieldBean {

    private final static transient boolean OPEN = false;

    //HTTP
    private final static transient String FILTER[] = {
            "/uploadfile/",
    };
    private final static transient String PARAMS_FILTER[] = {
//            "Image_upload.action",
            "Task_submit.action",
    };
    private final static transient String RESPONSE_FILTER[] = {
            "Trans_file.action"
    };

    private boolean update;
    private String url;
    private String code;
    private String time;
    private String requestHead;
    private String responseHead;
    private String method;
    private String contentType;
    private String params;
    private String requestSize;
    private String responseSize;
    private Object response;

    private transient AuxiliaryFactory instance;

    public InterceptorBean() {
        instance = AuxiliaryFactory.getInstance();
    }

    public void completed() {
        if (check()) {
            instance.postUpLoadHttp(getFilterMap(true));
        }
    }

    public boolean check() {
        return OPEN && instance != null && !TextUtils.isEmpty(url) &&
                !url.startsWith(instance.getRootUrl());
    }

    private boolean isShowEnable(String[]... args) {
        if (!TextUtils.isEmpty(getUrl()) && !TextUtils.isEmpty(args)) {
            for (String[] filter : args) {
                for (String text : filter) {
                    if (!TextUtils.isEmpty(text) && getUrl().contains(text)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean isOther(@NonNull Field field) {
        return true;
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

    public String getRequestHead() {
        return requestHead;
    }

    public void setRequestHead(String requestHead) {
        this.requestHead = requestHead;
    }

    public String getResponseHead() {
        return responseHead;
    }

    public void setResponseHead(String responseHead) {
        this.responseHead = responseHead;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        if (!isShowEnable(FILTER, PARAMS_FILTER)) {
            return "params 禁止显示";
        }
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        if (!isShowEnable(FILTER, RESPONSE_FILTER)) {
            return "response 禁止显示";
        }
        if (response instanceof String) {
            return "\n" + response;
        }
        return LogUtil.getLog(response);
    }

    public void setResponse(String response) {
        try {
            this.response = JSON.parse(response);
        } catch (Exception ignored) {
            this.response = response;
        }
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

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}