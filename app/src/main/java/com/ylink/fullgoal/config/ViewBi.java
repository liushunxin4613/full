package com.ylink.fullgoal.config;

import android.view.View;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBi;

import java.util.Map;

public class ViewBi extends SurfaceBi<ViewBi, ViewBean> {

    @Override
    public Integer getDefLayoutResId() {
        return null;
    }

    @Override
    public void onBindApi(SurfaceControllerApi api, ViewBean bean) {
        super.onBindApi(api, bean);
        execute(bean.getData(), this::onVo);
        api.setOnClickListener(bean.getOnClickListener());
    }

    private Map<String, String> getMap() {
        return vr(bean(), ViewBean::getMap);
    }

    private String get(String key) {
        return vr(getMap(), obj -> obj.get(key));
    }

    private void onVo(TemplateVo vo) {
        if (TextUtils.check(vo.getVId())) {
            int id = ResUtil.getIdentifier(vo.getVId(), "id");
            View view = api().findViewById(id);
            if (view == null && TextUtils.check(vo.getTag())) {
                view = api().findViewWithTag(vo.getTag());
            }
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (TextUtils.check(vo.getFormat())) {
                    String format = vo.getFormat();
                    if (TextUtils.check(getMap())) {
                        for (Map.Entry<String, String> entry : getMap().entrySet()) {
                            format = format.replace(String.format("<%s>", entry.getKey()),
                                    entry.getValue());
                        }
                    }
                    api().setText(tv, format);
                } else {
                    api().setText(tv, get(vo.getText()));
                }
            }
        }
    }

}