package com.leo.core.iapi.inter;

import android.widget.ImageView;

public interface ImageAction {
    void execute(Object path, ImageView iv, float rotate, boolean success);
}
