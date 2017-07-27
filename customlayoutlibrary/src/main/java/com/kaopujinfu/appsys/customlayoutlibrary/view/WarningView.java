package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.DisplayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe: 动态打错
 * Created Author: Gina
 * Created Date: 2017/6/5.
 */

public class WarningView extends View {

    //打叉的起点
    int line1StartX;
    int line2StartX;
    int lineStartY;
    //线水平最大增量
    int maxLineIncrement;

    //线的宽度
    private int lineThick = 10;

    //获取圆心的x坐标
    int center;

    //圆弧半径
    int radius;

    //定义的圆弧的形状和大小的界限
    RectF rectF, rectF2, rectF3;

    Paint paint, paint2;

    //控件大小
    float totalWidth;

    public WarningView(Context context) {
        super(context);
        //init();
    }

    public WarningView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Pattern p = Pattern.compile("\\d*");

        Matcher m = p.matcher(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width"));

        if (m.find()) {
            totalWidth = Float.valueOf(m.group());
        }

        totalWidth = DisplayUtils.dp2px(context, totalWidth);

        maxLineIncrement = (int) (totalWidth * 2 / 5);

        init();
    }

    public WarningView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //init();
    }

    void init() {
        paint = new Paint();
        //设置画笔颜色
        paint.setColor(getResources().getColor(android.R.color.holo_orange_light));
        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        paint.setAntiAlias(true);

        paint2 = new Paint();
        //设置画笔颜色
        paint2.setColor(getResources().getColor(android.R.color.holo_orange_light));
        //设置圆弧的宽度
        paint2.setStrokeWidth(lineThick);
        //设置圆弧为空心
        paint2.setStyle(Paint.Style.FILL);
        //消除锯齿
        paint2.setAntiAlias(true);

        //获取圆心的x坐标
        center = (int) (totalWidth / 2);

        //圆弧半径
        radius = (int) (totalWidth / 2) - lineThick;

        //打叉的起点
        line1StartX = (int) (center + totalWidth / 5);
        lineStartY = (int) (center - totalWidth / 5);
        line2StartX = (int) (center - totalWidth / 5);

        rectF = new RectF(center - radius,
                center - radius,
                center + radius,
                center + radius);
        rectF2 = new RectF(center - radius / 4, center - radius + radius / 3, center + radius / 4, center + 2 * radius - radius / 3);

        rectF3 = new RectF(center - radius / 8, center + radius - radius / 2, center + radius / 8, center + radius - radius / 4);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置圆弧的宽度
        paint.setStrokeWidth(lineThick);
//        //根据进度画圆弧
        canvas.drawArc(rectF, 235, 360, false, paint);
        canvas.drawArc(rectF2, 240, 60, true, paint2);
        canvas.drawArc(rectF3, 235, 360, true, paint2);
    }
    /* 设置画笔的大小 */
    public void setLineThick(int lineThick) {
        this.lineThick = lineThick;
    }
}
