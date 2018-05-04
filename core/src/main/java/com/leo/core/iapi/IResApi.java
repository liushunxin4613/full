package com.leo.core.iapi;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface IResApi<T extends IResApi> extends IBindContextApi<T> {

    Resources getResources();

    int getColor(@ColorInt int color);

    float getDimen(@DimenRes int dimen);

    int getDimenInt(@DimenRes int dimen);

    String getString(@StringRes int res);

    Drawable getDrawable(@DrawableRes int res);

}
