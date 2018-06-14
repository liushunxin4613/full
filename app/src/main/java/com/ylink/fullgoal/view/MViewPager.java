package com.ylink.fullgoal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.RunUtil;
import com.leo.core.util.SoftInputUtil;

public class MViewPager extends ViewPager {

    private int miniSpeed = 0;
    private int miniWidth = 120;
    private GestureDetector detector;
    private IObjAction<Boolean> verticalApi;
    private IObjAction<Boolean> verticalBeyondApi;
    private IObjAction<Boolean> horizontalApi;

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
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                SoftInputUtil.hidSoftInput(getRootView());
                View v = getChildAt(position);
                if (v != null) {
                    v.setFocusableInTouchMode(true);
                    v.requestFocus();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setVerticalApi(IObjAction<Boolean> api) {
        this.verticalApi = api;
    }

    public void setVerticalBeyondApi(IObjAction<Boolean> api) {
        this.verticalBeyondApi = api;
    }

    public void setHorizontalApi(IObjAction<Boolean> api) {
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
