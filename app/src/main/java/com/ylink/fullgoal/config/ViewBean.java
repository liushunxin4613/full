package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;
import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.OnClickBean;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewBean extends OnClickBean<ViewBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ViewBean> newDefApi() {
        return new ViewBi();
    }

    private List<TemplateVo> data;
    private Map<String, String> map;

    private transient String xml;

    ViewBean(String xml, XmlResourceParser parser, List<TemplateVo> data, Object obj,
             OnBVClickListener<ViewBean> listener) {
        super(listener);
        this.xml = xml;
        this.data = data;
        this.map = new LinkedHashMap<>();
        this.setApiXmlResourceParser(parser);
        if (obj instanceof Map) {
            execute((Map) obj, (key, value) -> {
                if (key instanceof String && value instanceof String) {
                    if(TextUtils.equals(key, "extension2")){//编号
                        setApiCode((String) value);
                    }
                    this.map.put((String) key, (String) value);
                }
            });
        }
    }

    public List<TemplateVo> getData() {
        return data;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

}