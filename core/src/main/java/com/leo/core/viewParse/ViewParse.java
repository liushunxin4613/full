package com.leo.core.viewParse;

import android.view.View;

import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.Map;

public class ViewParse<A extends View, B> {

    private Class<A> viewClz;
    private Class<B> valueClz;
    private IViewParseAction<A, B> action;

    ViewParse(Class<A> viewClz, Class valueClz, IViewParseAction<A, B> action) {
        if (!TextUtils.check(viewClz, valueClz, action)) {
            throw new NullPointerException("加入项不能有空值");
        }
        this.viewClz = viewClz;
        this.valueClz = valueClz;
        this.action = action;
    }

    public boolean execute(View view, Object value, final Map<String, Object> map) {
        if (viewClz.isInstance(view) && TextUtils.check(map)) {
            if (valueClz.isInstance(value)) {
                action.onParse((A) view, (B) value, map);
                return true;
            } else if (TextUtils.equals(valueClz, int.class)) {
                Integer i = JavaTypeUtil.getInt(value, 0);
                action.onParse((A) view, (B) i, map);
                return true;
            } else if (TextUtils.equals(valueClz, boolean.class)) {
                Boolean b = JavaTypeUtil.getboolean(value, false);
                action.onParse((A) view, (B) b, map);
                return true;
            } else if (TextUtils.equals(valueClz, float.class)) {
                Float f = JavaTypeUtil.getfloat(value, -1.0f);
                action.onParse((A) view, (B) f, map);
                return true;
            }
        }
        return false;
    }

}
