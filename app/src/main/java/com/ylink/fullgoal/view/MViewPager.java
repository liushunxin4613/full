package com.ylink.fullgoal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.inter.IReturnoAction;
import com.leo.core.util.RunUtil;
import com.leo.core.util.SoftInputUtil;

public class MViewPager extends ViewPager {

    private int miniSpeed = 0;
    private int miniWidth = 120;
    private GestureDetector detector;
    private IReturnoAction<Boolean> horizontalAction;
    private IReturnAction<Boolean, Boolean> horizontalBeyondAction;

    public MViewPager(Context context) {
        super(context);
        init();
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void toLeft(){
        onToLeft();
    }

    public void toRight(){
        onToRight();
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
                    return true;
                } else if (-velocityX > miniWidth && Math.abs(velocityX) > miniSpeed) {
                    onToRight();
                    return true;
                } else if (velocityY > miniWidth && Math.abs(velocityY) > miniSpeed) {
                    onToUp();
                    return true;
                } else if (-velocityY > miniWidth && Math.abs(velocityY) > miniSpeed) {
                    onToDown();
                    return true;
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

    public MViewPager setHorizontalBeyondApi(IReturnAction<Boolean, Boolean> action) {
        this.horizontalBeyondAction = action;
        return this;
    }

    public void setHorizontalApi(IReturnoAction<Boolean> action) {
        this.horizontalAction = action;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !detector.onTouchEvent(ev) && super.dispatchTouchEvent(ev);
    }

    /**
     * 手势向左
     */
    protected void onToLeft() {
        if (getCurrentItem() == 0) {
            RunUtil.getExecute(horizontalBeyondAction, false, obj -> obj.execute(true));
        } else if (!RunUtil.getExecute(horizontalAction, true, IReturnoAction::execute)) {
            setCurrentItem(getCurrentItem() - 1);
        }
    }

    /**
     * 手势向右
     */
    protected void onToRight() {
        if (getCurrentItem() == RunUtil.getExecute(getAdapter(), 0, PagerAdapter::getCount) - 1) {
            if (!RunUtil.getExecute(horizontalAction, true, IReturnoAction::execute)
                    && RunUtil.getExecute(horizontalBeyondAction, false, obj -> obj.execute(false))) {
                setCurrentItem(getCurrentItem() + 1);
            }
        } else if (!RunUtil.getExecute(horizontalAction, true, IReturnoAction::execute)) {
            setCurrentItem(getCurrentItem() + 1);
        }
    }

    /**
     * 手势向上
     */
    protected void onToUp() {
    }

    /**
     * 手势向下
     */
    protected void onToDown() {
    }

}
