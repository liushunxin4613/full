package com.ylink.fullgoal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.leo.core.iapi.api.IDisplayApi;
import com.leo.core.rect.Rec;
import com.leo.core.util.DisneyUtil;

public class MaskView extends View {

    private Paint mAreaPaint;
    private int widthScreen;
    private int heightScreen;
    private Rect mCenterRect;

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        IDisplayApi.ScreenDisplay p = DisneyUtil.getScreenDisplay();
        widthScreen = p.getX();
        heightScreen = p.getY();
        initCenterRect();
    }

    private void initPaint() {
        //绘制四周阴影区域
        mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAreaPaint.setColor(Color.BLACK);
        mAreaPaint.setStyle(Paint.Style.FILL);
        mAreaPaint.setAlpha(127);
    }

    private float xToy() {
        return (float) 12 / 7;
    }

    private float getRatio() {
        return 0.7f;
    }

    public void initCenterRect() {
        setCenterRect(newRect(widthScreen));
    }

    public Rect newRect(int width) {
        float ratio = getRatio();
        if (width > 0 && ratio >= 0 && ratio <= 1) {
            int x1 = (int) ((1 - ratio) / 2 * width);
            int y1 = (int) (x1 * xToy());
            int x2 = (int) ((1 + ratio) / 2 * width);
            int y2 = (int) (x2 * xToy());
            return new Rect(x1, y1, x2, y2);
        }
        return null;
    }

    public Rec newRec(int width) {
        float ratio = getRatio();
        if (width > 0 && ratio >= 0 && ratio <= 1) {
            int x = (int) ((1 - ratio) / 2 * width);
            int y = (int) (x * xToy());
            int w = (int) (ratio * width);
            int h = (int) (w * xToy());
            return new Rec(x, y, w, h);
        }
        return null;
    }

    public void setCenterRect(Rect r) {
        this.mCenterRect = r;
        postInvalidate();
    }

    public void clearCenterRect() {
        this.mCenterRect = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCenterRect == null)
            return;
        //绘制四周阴影区域
        canvas.drawRect(0, 0, widthScreen, mCenterRect.top, mAreaPaint);
        canvas.drawRect(0, mCenterRect.bottom + 1, widthScreen, heightScreen, mAreaPaint);
        canvas.drawRect(0, mCenterRect.top, mCenterRect.left - 1, mCenterRect.bottom + 1, mAreaPaint);
        canvas.drawRect(mCenterRect.right + 1, mCenterRect.top, widthScreen, mCenterRect.bottom + 1, mAreaPaint);
        super.onDraw(canvas);
    }

}