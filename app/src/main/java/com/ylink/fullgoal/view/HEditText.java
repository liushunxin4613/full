package com.ylink.fullgoal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class HEditText extends EditText {

    private float touchY;

    public HEditText(Context context) {
        super(context);
    }

    public HEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float offsetY = touchY - event.getY();
                if (offsetY > 10) {
                    //请求所有父控件及祖宗控件不要拦截事件
                    getParent().requestDisallowInterceptTouchEvent(getScrollY()
                            <= (getLineCount() - getMaxLines()) * getHeight());
                } else if (-offsetY > 10) {
                    getParent().requestDisallowInterceptTouchEvent(getScrollY() > 0);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        touchY = event.getY();
        return super.dispatchTouchEvent(event);
    }

}