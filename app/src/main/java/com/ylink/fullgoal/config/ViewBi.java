package com.ylink.fullgoal.config;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bi.OnClickBi;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.Map;

public class ViewBi extends OnClickBi<ViewBi, ViewBean> {

    @Override
    public Integer getDefLayoutResId() {
        ViewBean bean = bean();
        if (bean != null) {
            if (TextUtils.isEmpty(bean.getApiXmlResourceParser())
                    && TextUtils.check(bean.getXml())) {
                switch (bean.getXml()) {
                    case "l_apply_v1.xml":
                        return R.layout.l_apply_v1;
                    case "l_apply_v2.xml":
                        return R.layout.l_apply_v2;
                    case "l_apply_v3.xml":
                        return R.layout.l_apply_v3;
                    case "l_apply_v4.xml":
                        return R.layout.l_apply_v4;
                }
            }
        }
        return null;
    }

    @Override
    protected View getRootVg() {
        return findView("root_vg", "root_vg");
    }

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull ViewBean bean) {
        super.updateBind(api, bean);
        execute(bean.getData(), this::onVo);
    }

    private Map<String, String> getMap() {
        return vr(bean(), ViewBean::getMap);
    }

    private String get(String key) {
        return vr(getMap(), obj -> obj.get(key));
    }

    private View findView(String id, String tag) {
        View view = null;
        if (TextUtils.check(id)) {
            view = api().findViewById(ResUtil.getIdentifier(id, "id"));
        }
        if (view != null) {
            return view;
        } else if (TextUtils.check(tag)) {
            return api().findViewWithTag(tag);
        }
        return null;
    }

    private void onVo(TemplateVo vo) {
        if (TextUtils.check(vo.getVId())) {
            View view = findView(vo.getVId(), vo.getTag());
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
//                api().ii(String.format("tag: %s, id: %s", vo.getTag(), Integer.toHexString(tv.getId())));
//                api().ii(String.format("text: %s", tv.getText().toString()));
                if (TextUtils.check(vo.getFormat())) {
                    String format = vo.getFormat();
                    if (TextUtils.check(getMap())) {
                        for (Map.Entry<String, String> entry : getMap().entrySet()) {
                            format = format.replace(String.format("<%s>", entry.getKey()),
                                    entry.getValue());
                        }
                    }
                    api().setText(tv, format);
//                    api().ee(String.format("id: %s, format: %s", vo.getVId(), format));
                } else {
                    api().setText(tv, get(vo.getText()));
//                    api().ee(String.format("id: %s, text: %s, value: %s", vo.getVId(), vo.getText(), get(vo.getText())));
                }
            }
        }
    }

}