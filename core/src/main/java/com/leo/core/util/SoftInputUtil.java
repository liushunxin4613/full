package com.leo.core.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftInputUtil {

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) RunUtil.getExecute(context, obj -> obj.getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    /**
     * 关闭软键盘
     */
    public static void hidSoftInput(View v) {
        if (v != null) {
            InputMethodManager imm = getInputMethodManager(v.getContext());
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    /**
     * 输入法是否显示着
     */
    public static boolean getSoftInput(Context context) {
        return RunUtil.getExecute(getInputMethodManager(context), InputMethodManager::isActive);
    }

    /**
     * 打开输入法
     */
    public static boolean showSoftInput(View v) {
        if (v != null) {
            InputMethodManager imm = getInputMethodManager(v.getContext());
            if (imm != null) {
                imm.showSoftInput(v, 0);
            }
            return true;
        }
        return false;
    }

}
