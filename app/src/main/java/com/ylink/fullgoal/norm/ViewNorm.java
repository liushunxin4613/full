package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.item.ViewControllerApi;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewNorm extends OnClickNorm<ViewNorm, SurfaceControllerApi> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ViewControllerApi.class;
    }

    @Override
    protected String[] getSearchFields() {
        if (!TextUtils.isEmpty(map)) {
            int index = 0;
            String[] args = new String[map.size()];
            for (Map.Entry<String, String> entry : map.entrySet()) {
                args[index++] = entry.getValue();
            }
            return args;
        }
        return null;
    }

    private List<TemplateVo> data;
    private Map<String, String> map;

    public ViewNorm(String name, List<TemplateVo> data, Object obj,
                    OnBVClickListener<ViewNorm> listener) {
        super(listener);
        this.data = data;
        this.setRootViewJsonName(name);
        this.map = new LinkedHashMap<>();
        if (obj instanceof Map) {
            execute((Map) obj, (key, value) -> {
                if (key instanceof String && value instanceof String) {
                    if (TextUtils.equals(key, "extension2")) {//编号
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

}