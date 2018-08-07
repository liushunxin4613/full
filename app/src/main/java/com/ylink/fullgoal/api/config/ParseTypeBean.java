package com.ylink.fullgoal.api.config;

import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseTypeBean {

    public static String getName(Type type) {
        return type == null ? null : (type instanceof Class
                ? ((Class) type).getName() : type.toString());
    }

    private Type type;
    private String name;
    private String path;
    private Map<String, List<IPathMsgAction>> map;

    private ParseTypeBean() {
    }

    ParseTypeBean(Type root, Type type, IPathMsgAction action) {
        this.type = root;
        this.name = getName(root);
        this.map = new HashMap<>();
        this.map.put(getName(type), TextUtils.getListData(action));
    }

    public ParseTypeBean copy() {
        ParseTypeBean bean =
                new ParseTypeBean();
        bean.setType(getType());
        bean.setName(getName());
        bean.setPath(getPath());
        bean.setMap(getMap());
        return bean;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, List<IPathMsgAction>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<IPathMsgAction>> map) {
        this.map = map;
    }

}