package com.example.androidproject.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 灰色虚线分割线(水平),用于"其他登录方式"两侧
 * 不使用shape line,部分设备上shape虚线不渲染
 */
public class YLZDashLineView extends View {

    //虚线颜色
    private int lineColor = Color.parseColor("#C4CBD8");
    //单段实线长度(px)
    private float dashWidth;
    //间隔长度(px)
    private float dashGap;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public YLZDashLineView(Context context) {
        super(context);
        init();
    }

    public YLZDashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YLZDashLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dashWidth = dp2px(3);
        dashGap = dp2px(2);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(lineColor);
        paint.setStrokeWidth(getHeight());
        float y = getHeight() / 2f;
        float x = 0;
        while (x < getWidth()) {
            canvas.drawLine(x, y, Math.min(x + dashWidth, getWidth()), y, paint);
            x += dashWidth + dashGap;
        }
    }

    private float dp2px(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
