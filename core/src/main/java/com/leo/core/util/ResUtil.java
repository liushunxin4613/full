package com.leo.core.util;

import android.graphics.drawable.Drawable;

import com.leo.core.api.api.ResApi;
import com.leo.core.iapi.api.IResApi;

public class ResUtil {

    private static IResApi getApi() {
        return ClassBindUtil.getApi(ResApi.class);
    }

    public static int getColor(int color) {
        return getApi().getColor(color);
    }

    public static int getDimenInt(int dimen) {
        return getApi().getDimenInt(dimen);
    }

    public static float getDimen(int dimen) {
        return getApi().getDimen(dimen);
    }

    public static String getString(int res) {
        return getApi().getString(res);
    }

    public static Drawable getDrawable(int res) {
        return getApi().getDrawable(res);
    }

}
