package com.ylink.fullgoal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.leo.core.rect.Rec;

public class MaskView extends View {

    private final static float RATIO = 0.7f;
    private final static int WIDTH_BI = 7;
    private final static int HEIGHT_BI = 12;

    private int width;
    private int height;
    private Paint mAreaPaint;
    private Rect mCenterRect;

    public MaskView(Context context) {
        super(context);
        initPaint();
    }

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        //绘制四周阴影区域
        mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAreaPaint.setColor(Color.BLACK);
        mAreaPaint.setStyle(Paint.Style.FILL);
        mAreaPaint.setAlpha(127);
    }

    public void initCenterRect(int width, int height) {
        this.width = width;
        this.height = height;
        setCenterRect(newRect(width, height));
    }

    public Rect newRect(int width, int height) {
        if (((float) width / WIDTH_BI) > ((float) height / HEIGHT_BI)) {
            width = (int) ((float) height * WIDTH_BI / HEIGHT_BI);
        } else {
            height = (int) ((float) width * HEIGHT_BI / WIDTH_BI);
        }
        int x1 = (int) ((1 - RATIO) / 2 * width);
        int x2 = (int) ((1 + RATIO) / 2 * width);
        int y1 = (int) ((1 - RATIO) / 2 * height);
        int y2 = (int) ((1 + RATIO) / 2 * height);
        return new Rect(x1, y1, x2, y2);
    }

    public Rec newRec(int width, int height) {
        if (((float) width / WIDTH_BI) > ((float) height / HEIGHT_BI)) {
            width = (int) ((float) height * WIDTH_BI / HEIGHT_BI);
        } else {
            height = (int) ((float) width * HEIGHT_BI / WIDTH_BI);
        }
        int x = (int) ((1 - RATIO) / 2 * width);
        int y = (int) ((1 - RATIO) / 2 * height);
        int w = (int) (RATIO * width);
        int h = (int) (RATIO * height);
        return new Rec(x, y, w, h);
    }

    public void setCenterRect(Rect r) {
        this.mCenterRect = r;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCenterRect == null)
            return;
        //绘制四周阴影区域
        canvas.drawRect(0, 0, width, mCenterRect.top, mAreaPaint);
        canvas.drawRect(0, mCenterRect.bottom + 1, width, height, mAreaPaint);
        canvas.drawRect(0, mCenterRect.top, mCenterRect.left - 1, mCenterRect.bottom + 1, mAreaPaint);
        canvas.drawRect(mCenterRect.right + 1, mCenterRect.top, width, mCenterRect.bottom + 1, mAreaPaint);
        super.onDraw(canvas);
    }

}