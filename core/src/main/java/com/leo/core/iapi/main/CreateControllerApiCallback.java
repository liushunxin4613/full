package com.leo.core.iapi.main;

import android.view.ViewGroup;

public interface CreateControllerApiCallback {
    IControllerApi createControllerApi(ViewGroup container, int resId);
}
