package com.leo.core.util;

import com.leo.core.api.api.ADisplayApi;
import com.leo.core.api.api.Dip2PxContextApi;
import com.leo.core.api.api.Px2DipContextApi;
import com.leo.core.api.api.Px2SpContextApi;
import com.leo.core.api.api.Sp2PxContextApi;
import com.leo.core.iapi.api.IDisplayApi;

/**
 * 屏幕分辨率工具类
 */
public class DisneyUtil {

    private static <T extends ADisplayApi> T getApi(Class<T> clz) {
        return ClassBindUtil.getApi(clz);
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(float pxValue) {
        return getApi(Px2DipContextApi.class).convert(pxValue);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(float dipValue) {
        return getApi(Dip2PxContextApi.class).convert(dipValue);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        return getApi(Px2SpContextApi.class).convert(pxValue);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue) {
        return getApi(Sp2PxContextApi.class).convert(spValue);
    }

    /**
     * 获取屏幕分辨率
     */
    public static IDisplayApi.ScreenDisplay getScreenDisplay() {
        return getApi(Px2DipContextApi.class).getWindowScreenDisplay();
    }

}
