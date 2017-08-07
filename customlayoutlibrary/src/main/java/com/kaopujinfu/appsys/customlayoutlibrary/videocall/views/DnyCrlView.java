package com.kaopujinfu.appsys.customlayoutlibrary.videocall.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Describe:动态加载圆点
 * Created Author: Gina
 * Created Date: 2017/7/20.
 */

public class DnyCrlView extends View {
    public DnyCrlView(Context context) {
        super(context);
    }

    public DnyCrlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DnyCrlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    int num = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        if(num>=1) {
            RectF rect = new RectF(0, 0, size, size);
            canvas.drawArc(rect, 0, 360, true, paint);
        }
        if (num >1 && num <=3) {
            RectF rect2 = new RectF(size + 10, 0, 2 * size + 10, size);
            canvas.drawArc(rect2, 0, 360, true, paint);
        }
        if (num == 3) {
            RectF rect3 = new RectF(2 * size + 20, 0, 3 * size + 20, size);
            canvas.drawArc(rect3, 0, 360, true, paint);
        }
        num++;
        if (num == 4) {
            num = 0;
        }
        if (flag) {
            postInvalidateDelayed(1000);
        }
    }

    boolean flag = true;
    int color = getResources().getColor(android.R.color.white);

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setCrlColor(int color) {
        this.color = color;
    }

    private int size = 20;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = getHeight();
    }
}
