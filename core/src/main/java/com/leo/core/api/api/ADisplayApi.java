package com.leo.core.api.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.leo.core.iapi.api.IDisplayApi;

/**
 * 分辨率参数转换抽象类
 *
 * @param <T> 本身泛型
 */
public abstract class ADisplayApi<T extends ADisplayApi> extends BindContextApi<T> implements IDisplayApi<T, Float, String, Integer> {

    private Context context;
    private Display display;
    protected float scale;

    @Override
    public T bind(@NonNull Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            display = manager.getDefaultDisplay();
        }
        return (T) this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ScreenDisplay getWindowScreenDisplay() {
        return new ScreenDisplay(display.getWidth(), display.getHeight());
    }

    /**
     * 获取显示指标
     *
     * @return 显示指标
     */
    public DisplayMetrics getDisplayMetrics() {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 转换
     *
     * @param in 输入数据
     * @return 返回数据
     */
    public abstract int convert(Float in);

}
