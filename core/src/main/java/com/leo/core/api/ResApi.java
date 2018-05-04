package com.leo.core.api;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.leo.core.iapi.IResApi;

@SuppressWarnings("ResourceType")
public class ResApi<T extends ResApi> extends BindContextApi<T> implements IResApi<T> {

    @Override
    public Resources getResources() {
        return getContext().getResources();
    }

    @Override
    public int getColor(@ColorInt int color) {
        return getResources().getColor(color);
    }

    @Override
    public float getDimen(@DimenRes int dimen) {
        return getResources().getDimension(dimen);
    }

    @Override
    public int getDimenInt(@DimenRes int dimen) {
        return getResources().getDimensionPixelSize(dimen);
    }

    @Override
    public String getString(@StringRes int res) {
        return getResources().getString(res);
    }

    @Override
    public Drawable getDrawable(@DrawableRes int res) {
        return getResources().getDrawable(res);
    }

}
