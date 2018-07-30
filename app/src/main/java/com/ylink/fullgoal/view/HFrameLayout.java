package com.ylink.fullgoal.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class HFrameLayout extends FrameLayout {

    private View.OnClickListener listenOnClickListener;

    public HFrameLayout(@NonNull Context context) {
        super(context);
    }

    public HFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OnClickListener getListenOnClickListener() {
        return listenOnClickListener;
    }

    public void setListenOnClickListener(OnClickListener listenOnClickListener) {
        this.listenOnClickListener = listenOnClickListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getListenOnClickListener() != null) {
            getListenOnClickListener().onClick(this);
        }
        return super.dispatchTouchEvent(ev);
    }

}