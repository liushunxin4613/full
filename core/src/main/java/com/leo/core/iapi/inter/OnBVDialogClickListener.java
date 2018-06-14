package com.leo.core.iapi.inter;

import android.app.Dialog;
import android.view.View;

public interface OnBVDialogClickListener<T> {
    void onBVClick(T bean, View v, Dialog dialog);
}