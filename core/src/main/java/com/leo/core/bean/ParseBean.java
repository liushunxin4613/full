package com.leo.core.bean;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IPathMsgAction;

public class ParseBean<B> extends VsApi<ParseBean> {

    private IPathMsgAction<B> action;
    private B bean;
    private String name;
    private String path;
    private int what;
    private String msg;

    public ParseBean(IPathMsgAction<B> action, String name, String path, int what, String msg, B bean) {
        this.action = action;
        this.bean = bean;
        this.name = name;
        this.path = path;
        this.what = what;
        this.msg = msg;
    }

    public void execute() {
        executeNon(getAction(), action -> action.execute(getName(), no(getPath()), getWhat(), no(getMsg()),
                getBean()));
    }

    private IPathMsgAction<B> getAction() {
        return action;
    }

    private B getBean() {
        return bean;
    }

    private String getName() {
        return name;
    }

    private String getPath() {
        return path;
    }

    private int getWhat() {
        return what;
    }

    private String getMsg() {
        return msg;
    }

}