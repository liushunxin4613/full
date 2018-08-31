package com.leo.core.viewParse;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class StyleFactory extends VsApi<StyleFactory> {

    private static StyleFactory instance;

    public static StyleFactory getInstance() {
        if (instance == null) {
            synchronized (StyleFactory.class) {
                instance = new StyleFactory();
            }
        }
        return instance;
    }

    private final static Map<String, Integer> MAP = TextUtils.map(m -> m
            .put("layout_marginTop", android.R.attr.layout_marginTop)
            .put("layout_marginBottom", android.R.attr.layout_marginBottom)
            .put("layout_marginLeft", android.R.attr.layout_marginLeft)
            .put("layout_marginRight", android.R.attr.layout_marginRight)
            .put("paddingLeft", android.R.attr.paddingLeft)
            .put("paddingTop", android.R.attr.paddingTop)
            .put("paddingRight", android.R.attr.paddingRight)
            .put("paddingBottom", android.R.attr.paddingBottom));

    public void onStyle(View v, int resId) {
        if (v != null && resId != 0) {
            execute(MAP, (key, value) -> otty(v, resId, key, (view, name, a) -> {
                AtomicBoolean success = new AtomicBoolean(true);
                initLayoutParams(view, layoutParams -> {
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) layoutParams;
                        switch (name) {
                            case "layout_marginTop":
                                lp.topMargin = a.getDimensionPixelSize(0, 0);
                                break;
                            case "layout_marginBottom":
                                lp.bottomMargin = a.getDimensionPixelSize(0, 0);
                                break;
                            case "layout_marginLeft":
                                lp.leftMargin = a.getDimensionPixelSize(0, 0);
                                break;
                            case "layout_marginRight":
                                lp.rightMargin = a.getDimensionPixelSize(0, 0);
                                break;
                            default:
                                success.set(false);
                                break;
                        }
                    }
                });
                if (success.get()) {
                    return;
                }
                success.set(true);
                switch (name) {
                    case "paddingLeft":
                        view.setPadding(a.getDimensionPixelSize(0, 0), view.getPaddingTop(),
                                view.getPaddingRight(), view.getPaddingBottom());
                        break;
                    case "paddingTop":
                        view.setPadding(view.getPaddingLeft(), a.getDimensionPixelSize(0, 0),
                                view.getPaddingRight(), view.getPaddingBottom());
                        break;
                    case "paddingRight":
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                                a.getDimensionPixelSize(0, 0), view.getPaddingBottom());
                        break;
                    case "paddingBottom":
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                                view.getPaddingRight(), a.getDimensionPixelSize(0, 0));
                        break;
                    default:
                        success.set(false);
                        break;
                }
                if (!success.get()) {
                    LogUtil.ee(this, String.format("%s %s 未解析", view.getClass().getName(), name));
                }
            }));
        }
    }

    private void otty(View view, int resId, String name, IStyleParseAction<View> action) {
        if (TextUtils.check(view, name, action) && resId != 0) {
            Integer attr = MAP.get(name);
            if (attr != null && attr != 0) {
                final TypedArray a = view.getContext().obtainStyledAttributes(resId,
                        new int[]{attr});
                action.onParse(view, name, a);
                a.recycle();
            }
        }
    }

    private void initLayoutParams(@NonNull View view, IObjAction<ViewGroup.LayoutParams> action) {
        if (action != null) {
            ViewGroup.LayoutParams layoutParams
                    = view.getLayoutParams();
            if(layoutParams != null){
                action.execute(layoutParams);
                view.setLayoutParams(layoutParams);
            }
        }
    }

}