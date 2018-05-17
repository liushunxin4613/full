package com.ylink.fullgoal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.leo.core.iapi.IRunApi;
import com.leo.core.util.RunUtil;

public class MViewPager extends ViewPager {

    private int miniSpeed = 0;
    private int miniWidth = 120;
    private GestureDetector detector;
    private IRunApi<Boolean> verticalApi;
    private IRunApi<Boolean> verticalBeyondApi;
    private IRunApi<Boolean> horizontalApi;

    public MViewPager(Context context) {
        super(context);
        init();
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        detector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityX > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    onToLeft();
                } else if (-velocityX > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    onToRight();
                } else if (velocityY > miniWidth && Math.abs(velocityY) > miniSpeed) {
                    onToUp();
                } else if (-velocityY > miniWidth && Math.abs(velocityY) > miniSpeed) {
                    onToDown();
                }
                return false;
            }
        });
    }

    public void setVerticalApi(IRunApi<Boolean> api) {
        this.verticalApi = api;
    }

    public void setVerticalBeyondApi(IRunApi<Boolean> api) {
        this.verticalBeyondApi = api;
    }

    public void setHorizontalApi(IRunApi<Boolean> api) {
        this.horizontalApi = api;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    protected void onToLeft() {
        if (getCurrentItem() == 0) {
            RunUtil.executeNon(verticalBeyondApi, obj -> obj.execute(true));
        }
        RunUtil.executeNon(verticalApi, obj -> obj.execute(true));
    }

    protected void onToRight() {
        if (getCurrentItem() == RunUtil.getExecute(getAdapter(), 0, PagerAdapter::getCount) - 1) {
            RunUtil.executeNon(verticalBeyondApi, obj -> obj.execute(false));
        }
        RunUtil.executeNon(verticalApi, obj -> obj.execute(false));
    }

    protected void onToUp() {
        RunUtil.executeNon(horizontalApi, obj -> obj.execute(true));
    }

    protected void onToDown() {
        RunUtil.executeNon(horizontalApi, obj -> obj.execute(false));
    }

}
