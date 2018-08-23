package com.ylink.fullgoal.api.item;

import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.config.MVCFactory;
import com.ylink.fullgoal.config.vo.TemplateVo;
import com.ylink.fullgoal.norm.ViewNorm;

import java.util.Map;

public class ViewControllerApi<C> extends LineControllerApi<ViewControllerApi, C, ViewNorm> {

    public ViewControllerApi(C controller) {
        super(controller);
    }

    @Override
    public XmlResourceParser getLayoutXmlPullParser(String xml) {
        if (TextUtils.check(xml)) {
            XmlResourceParser parser = openFileLayoutXmlPullParser(MVCFactory.getInstance().getConfigDir(), xml);
            if (parser == null) {
                parser = openAssetsLayoutXmlPullParser(xml);
            }
            return parser;
        }
        return null;
    }

    @Override
    public ViewControllerApi setRootViewXml(String xml) {
        if (!TextUtils.isEmpty(xml) && getLayoutXmlPullParser(xml) == null) {
            switch (xml) {
                case "l_apply_v1.xml":
                    setRootViewResId(R.layout.l_apply_v1);
                    break;
                case "l_apply_v2.xml":
                    setRootViewResId(R.layout.l_apply_v2);
                    break;
                case "l_apply_v3.xml":
                    setRootViewResId(R.layout.l_apply_v3);
                    break;
                case "l_apply_v4.xml":
                    setRootViewResId(R.layout.l_apply_v4);
                    break;
            }
            xml = null;
        }
        return super.setRootViewXml(xml);
    }

    @Override
    protected View getRootVg() {
        return findView("root_vg", "root_vg");
    }

    private View findView(String id, String tag) {
        View view = null;
        if (TextUtils.check(tag)) {
            view = findViewWithTag(tag);
        }
        if (view != null) {
            return view;
        } else if (TextUtils.check(id)) {
            return findViewById(ResUtil.getIdentifier(id, "id"));
        }
        return null;
    }

    private void onVo(TemplateVo vo, Map<String, String> map) {
        if (TextUtils.check(vo.getVId(), map)) {
            View view = findView(vo.getVId(), vo.getTag());
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (TextUtils.check(vo.getFormat())) {
                    String format = vo.getFormat();
                    if (TextUtils.check(map)) {
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            format = format.replace(String.format("<%s>", entry.getKey()),
                                    entry.getValue());
                        }
                    }
                    setText(tv, format);
                } else {
                    setText(tv, map.get(vo.getText()));
                }
            }
        }
    }

    @Override
    protected void onSafeNorm(@NonNull ViewNorm norm, int position) {
        super.onSafeNorm(norm, position);
        execute(norm.getData(), vo -> onVo(vo, norm.getMap()));
    }

}