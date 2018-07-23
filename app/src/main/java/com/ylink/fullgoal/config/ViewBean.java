package com.ylink.fullgoal.config;

import android.content.res.XmlResourceParser;
import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewBean extends SurfaceBiBean<ViewBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ViewBean> newDefApi() {
        return new ViewBi();
    }

    private List<TemplateVo> data;
    private Map<String, String> map;

    private transient View.OnClickListener onClickListener;

    ViewBean(XmlResourceParser parser, List<TemplateVo> data, Object obj) {
        this.data = data;
        this.map = new LinkedHashMap<>();
        this.setApiXmlResourceParser(parser);
        if (obj instanceof Map) {
            execute((Map) obj, (key, value) -> {
                if (key instanceof String && value instanceof String) {
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

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<ViewBean> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

}