package com.leo.core.core;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.api.main.HasCoreControllerApi;
import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IVgRunApi;
import com.leo.core.iapi.main.IViewApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;

public class ViewApi<T extends ViewApi> extends HasCoreControllerApi<T> implements IViewApi<T> {

    public ViewApi(CoreControllerApi controllerApi) {
        super(controllerApi);
    }

    @Override
    public boolean checkView(View view) {
        return view != null;
    }

    @Override
    public T setVisibility(View view, int visibility) {
        if (checkView(view)) {
            view.setVisibility(visibility);
        }
        return getThis();
    }

    @Override
    public T setVisibility(int visibility, View... args) {
        if (!TextUtils.isEmpty(args)) {
            for (View view : args) {
                setVisibility(view, visibility);
            }
        }
        return getThis();
    }

    @Override
    public T setText(View view, CharSequence text) {
        if (checkView(view)) {
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }
        return getThis();
    }

    @Override
    public T setViewText(TextView tv, CharSequence text, boolean gone) {
        if (checkView(tv)) {
            if (!TextUtils.isTrimEmpty(text)) {
                setVisibility(tv, View.VISIBLE);
                tv.setText(text);
            } else {
                setVisibility(tv, gone ? View.GONE : View.INVISIBLE);
            }
        }
        return getThis();
    }

    @Override
    public T setViewText(TextView tv, CharSequence text) {
        return setViewText(tv, text, false);
    }

    @Override
    public T setImage(ImageView iv, Object path) {
        if (checkView(iv)) {
            controllerApi().load(path, iv);
        }
        return getThis();
    }

    @Override
    public T setIcon(ImageView icon, boolean show) {
        return setVisibility(icon, show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public T setOnClickListener(View view, View.OnClickListener listener) {
        if (checkView(view)) {
            view.setOnClickListener(listener);
        }
        return getThis();
    }

    @Override
    public T setOnClickListener(View.OnClickListener listener) {
        setOnClickListener(controllerApi().getRootView(), listener);
        return getThis();
    }

    @Override
    public T setOnLongClickListener(View view, View.OnLongClickListener listener) {
        if (checkView(view)) {
            view.setOnLongClickListener(listener);
        }
        return getThis();
    }

    @Override
    public T setOnLongClickListener(View.OnLongClickListener listener) {
        setOnLongClickListener(controllerApi().getRootView(), listener);
        return getThis();
    }

    @Override
    public T setTextHint(EditText et, String hint) {
        if (checkView(et)) {
            et.setHint(hint);
        }
        return getThis();
    }

    @Override
    public T setResBg(View view, int resId) {
        if (checkView(view)) {
            view.setBackgroundResource(resId);
        }
        return getThis();
    }

    @Override
    public T setColorBg(View view, int color) {
        if (checkView(view)) {
            view.setBackgroundColor(ResUtil.getColor(color));
        }
        return getThis();
    }

    @Override
    public T setTextColor(TextView tv, int color) {
        if (checkView(tv)) {
            tv.setTextColor(ResUtil.getColor(color));
        }
        return getThis();
    }

    @Override
    public T setViewGroupApi(ViewGroup vg, IVgRunApi vgApi) {
        if (checkView(vg) && vgApi != null) {
            vgApi.execute(vg);
        }
        return getThis();
    }

    @Override
    public T setAction(IAction action) {
        if(action != null){
            action.action();
        }
        return getThis();
    }

}
