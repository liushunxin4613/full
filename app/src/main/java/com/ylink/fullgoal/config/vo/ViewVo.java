package com.ylink.fullgoal.config.vo;

import java.util.List;

public class ViewVo {

    private String name;
    private String xml;
    private String path;
    private String params;
    private List<TemplateVo> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<TemplateVo> getList() {
        return list;
    }

    public void setList(List<TemplateVo> list) {
        this.list = list;
    }

}