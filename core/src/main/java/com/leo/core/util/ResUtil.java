package com.leo.core.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.leo.core.api.api.ResApi;
import com.leo.core.iapi.api.IResApi;

public class ResUtil {

    private static IResApi getApi() {
        return ClassBindUtil.getApi(ResApi.class);
    }

    public static Resources getResources() {
        return getApi().getResources();
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

    public static int getIdentifier(String name, String defType){
        return getApi().getIdentifier(name, defType);
    }

    public static int getIdentifier(String name, String defType, String defPackage){
        return getApi().getIdentifier(name, defType, defPackage);
    }

}
