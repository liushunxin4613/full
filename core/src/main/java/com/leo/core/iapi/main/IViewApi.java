package com.leo.core.iapi.main;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.core.iapi.IAction;
import com.leo.core.iapi.IVgRunApi;
import com.leo.core.iapi.core.IApi;

public interface IViewApi<T extends IViewApi> extends IApi {

    /**
     * 检查view
     *
     * @param view View
     * @return true为有, false为无
     */
    boolean checkView(View view);

    /**
     * 设置view是否的显示状态
     *
     * @param view       View
     * @param visibility 显示状态
     * @return 媒介
     */
    T setVisibility(View view, int visibility);

    /**
     * 设置view是否的显示状态
     *
     * @param visibility 显示状态
     * @return 媒介
     */
    T setVisibility(int visibility, View... args);

    /**
     * 设置view文本
     *
     * @return 媒介
     */
    T setText(View view, CharSequence text);

    /**
     * 设置TextView文本
     *
     * @param tv   TextView
     * @param text 文本
     * @param gone 无数据是否消除,否则invisibility
     * @return 媒介
     */
    T setViewText(TextView tv, CharSequence text, boolean gone);

    /**
     * 设置TextView文本,无数据时invisibility
     *
     * @param tv   TextView
     * @param text 文本
     * @return 媒介
     */
    T setViewText(TextView tv, CharSequence text);

    /**
     * 设置图片
     *
     * @param iv   ImageView
     * @param path 图片路径
     * @return 媒介
     */
    T setImage(ImageView iv, Object path);

    /**
     * 设置icon是否隐藏
     *
     * @param icon ImageView
     * @param show true:visibility;false:invisibility
     * @return 媒介
     */
    T setIcon(ImageView icon, boolean show);

    /**
     * 设置view的点击事件
     *
     * @param view     View
     * @param listener 点击监听
     * @return 媒介
     */
    T setOnClickListener(View view, View.OnClickListener listener);

    /**
     * 设置view的点击事件
     *
     * @param listener 点击监听
     * @return 媒介
     */
    T setOnClickListener(View.OnClickListener listener);

    /**
     * 设置view的长按点击事件
     *
     * @param view     View
     * @param listener 长按点击监听
     * @return 媒介
     */
    T setOnLongClickListener(View view, View.OnLongClickListener listener);

    /**
     * 设置view的长按点击事件
     *
     * @param listener 长按点击监听
     * @return 媒介
     */
    T setOnLongClickListener(View.OnLongClickListener listener);

    /**
     * 设置EditText hint
     *
     * @param et   EditText
     * @param hint hint
     * @return 本身
     */
    T setTextHint(EditText et, String hint);

    /**
     * 设置背景图片
     *
     * @param view  view
     * @param resId 资源id
     * @return 本身
     */
    T setResBg(View view, @DrawableRes int resId);

    /**
     * 设置背景颜色
     *
     * @param view  view
     * @param color 颜色id
     * @return 本身
     */
    T setColorBg(View view, @ColorInt int color);

    /**
     * 设置TextView颜色
     *
     * @param tv    tv
     * @param color 颜色
     * @return 本身
     */
    T setTextColor(TextView tv, @ColorInt int color);

    /**
     * 当ViewGroup时
     * @param vg vg
     * @param vgApi vgApi
     * @return 本身
     */
    T setViewGroupApi(ViewGroup vg, IVgRunApi vgApi);

    /**
     * 设置viewBean
     * @param action action
     * @return 本身
     */
    T setAction(IAction action);

}
