package com.leo.core.bean;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IPathMsgAction;

import java.util.Map;

public class ParseBean<B> extends VsApi<ParseBean> {

    private IPathMsgAction<B> action;
    private String type;
    private String baseUrl;
    private String path;
    private Map<String, String> map;
    private int what;
    private String msg;
    private String field;
    private B bean;

    public ParseBean(IPathMsgAction<B> action, String type, String baseUrl, String path,
                     Map<String, String> map, int what, String msg, String field, B bean) {
        this.action = action;
        this.type = type;
        this.baseUrl = baseUrl;
        this.path = path;
        this.map = map;
        this.what = what;
        this.msg = msg;
        this.field = field;
        this.bean = bean;
    }

    public void execute() {
        executeNon(action, action -> action.execute(type, baseUrl, path, map, what, msg, field, bean));
    }

}