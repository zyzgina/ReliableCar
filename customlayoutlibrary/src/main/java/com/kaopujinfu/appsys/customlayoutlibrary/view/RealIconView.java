package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DisplayUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/9.
 */

public class RealIconView extends View {
    private float totalWidth;
    private int center;//坐标圆心点
    private int radius;
    private boolean flag = false;

    public RealIconView(Context context) {
        super(context);
    }

    public RealIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Pattern p = Pattern.compile("\\d*");
        Matcher m = p.matcher(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width"));
        if (m.find()) {
            if (GeneralUtils.isEmpty(m.group()))
                totalWidth = 400;
            else
                totalWidth = Float.valueOf(m.group());
        }
        totalWidth = DisplayUtils.dp2px(context, totalWidth);
        init();
    }

    public RealIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        center = (int) (totalWidth / 2);
        radius = (int) (totalWidth / 2);
        left = center - radius + radian;
        top = center - radius + radian;
        rigth = center + radius - radian;
        bottom = center + radius - radian;
        crossSize = (int) (totalWidth / 10);
        separateSize = crossSize / 3;
        if (separateSize < 5) {
            separateSize = 5;
        }
        radian = (int) (totalWidth / 11);
        setCrossPosition();
        setCanvasArc();
    }

    private int left, top, rigth, bottom;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(left, top, rigth, bottom);
        canvas.drawArc(rectF, c1, 100, false, mPaint);
        canvas.drawArc(rectF, c2, 100, false, mPaint);
        canvas.drawArc(rectF, c3, 100, false, yPaint);
        Paint wPaint = new Paint();
        RectF rectF2 = new RectF(left + radian / 2, top + radian / 2, rigth - radian / 2, bottom - radian / 2);
        wPaint.setColor(getResources().getColor(R.color.white));
        wPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rectF2, c1, 360, false, wPaint);

        RectF rectF3 = new RectF(left + radian, top + radian, rigth - radian, bottom - radian);
        canvas.drawArc(rectF3, c4, 100, false, mPaint);
        canvas.drawArc(rectF3, c5, 100, false, mPaint);
        canvas.drawArc(rectF3, c6, 100, false, yPaint);

        RectF rectF4 = new RectF(left + radian / 2 - 10, top + radian / 2 - 10, rigth - radian / 2 + 10, bottom - radian / 2 + 10);
        canvas.drawArc(rectF4, c7, 20, true, mPaint);
        canvas.drawArc(rectF4, c8, 20, true, mPaint);
        canvas.drawArc(rectF4, c9, 20, true, yPaint);

        RectF rectF5 = new RectF(left + 2 * radian - radian / 3, top + 2 * radian - radian / 3, rigth - 2 * radian + radian / 3, bottom - 2 * radian + radian / 3);
        canvas.drawArc(rectF5, c1, 360, false, wPaint);

        //画一个十字架
        Path path = new Path();
        path.moveTo(sx, sy);//以0,20未起点
        path.lineTo(slx, sly);//画出左边的线
        path.lineTo(ltx, lty);
        path.lineTo(tbx, tby);
        path.lineTo(blx, bly);
        path.lineTo(brx, bry);
        path.lineTo(rbx, rby);
        path.lineTo(rtx, rty);
        path.lineTo(sx, sy);
        canvas.drawPath(path, crossPaint);

        isPosition();
        if (flag)
            postInvalidateDelayed(60);
    }

    /* 十字架位置变量 */
    private float sx, sy;//起点
    private float slx, sly;//起点的左边(第一条线)
    private float ltx, lty;//以第一条线为起点(第二条线)
    private float tbx, tby;//以二条线为起点(第三条线)
    private float blx, bly;//以第三条线为起点(第四条线)
    private float brx, bry;//以第四条线为起点(第五条线)
    private float rbx, rby;//以五条线为起点(第六条线)
    private float rtx, rty;//以第六条线为起点(第七条线)
    private int crossSize = 30;//设置每条线的高度
    private int separateSize = 10;//设置每条线最大分隔
    private Paint crossPaint;

    /* 设置十字架的位置 */
    private void setCrossPosition() {

        crossPaint = new Paint();
        crossPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        crossPaint.setStyle(Paint.Style.FILL);


        sx = center;
        sy = center - crossSize - separateSize;

        slx = center - separateSize;
        sly = center - separateSize;

        ltx = center - crossSize - separateSize;
        lty = center - separateSize + separateSize / 2;

        tbx = center - separateSize;
        tby = center + separateSize;

        blx = center;
        bly = center + crossSize + separateSize;

        brx = center + separateSize;
        bry = center + separateSize;

        rbx = center + crossSize + separateSize;
        rby = center - separateSize + separateSize / 2;

        rtx = center + separateSize;
        rty = center - separateSize;
    }

    private Paint mPaint;
    private Paint yPaint;

    private int c1, c2, c3, c4, c5, c6, c7, c8, c9;
    private int radian = 40;

    private void setCanvasArc() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);

        yPaint = new Paint();
        yPaint.setColor(getResources().getColor(android.R.color.holo_green_dark));
        yPaint.setStyle(Paint.Style.FILL);
        yPaint.setStrokeWidth(10);
        c1 = 240;
        c2 = 120;
        c3 = 0;
        c4 = c1 + 40;
        c5 = c2 + 40;
        c6 = c3 + 40;
        c7 = c1 + 80;
        c8 = c2 + 80;
        c9 = c3 + 80;

    }

    private void isPosition() {
        c1 += 10;
        if (c1 >= 360) {
            c1 = c1 - 360;
        }

        c2 += 10;
        if (c2 >= 360) {
            c2 = c2 - 360;
        }
        c3 += 10;
        if (c3 >= 360) {
            c3 = c3 - 360;
        }
        c4 += 10;
        if (c4 >= 360) {
            c4 = c4 - 360;
        }
        c5 += 10;
        if (c5 >= 360) {
            c5 = c5 - 360;
        }
        c6 += 10;
        if (c6 >= 360) {
            c6 = c6 - 360;
        }
        c7 += 10;
        if (c7 >= 360) {
            c7 = c7 - 360;
        }
        c8 += 10;
        if (c8 >= 360) {
            c8 = c8 - 360;
        }
        c9 += 10;
        if (c9 >= 360) {
            c9 = c9 - 360;
        }
    }


    /* 设置十字架每条线的高度 */
    public void setCrossSize(int crossSize) {
        this.crossSize = crossSize;
    }

    /* 设置十字架每条线最大分隔大小 */
    public void setCrossSeparateSize(int separateSize) {
        this.separateSize = separateSize;
    }

    /* 取消旋转 */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
