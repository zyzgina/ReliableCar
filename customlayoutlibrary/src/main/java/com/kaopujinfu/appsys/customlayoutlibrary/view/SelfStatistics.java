package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.kaopujinfu.appsys.customlayoutlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 圆形统计图
 * Created by 左丽姬 on 2017/5/24.
 */
public class SelfStatistics extends View {
    private Paint paint;
    //对外提供注入数据的变量
    private float[] datas;
    //自定义view内部存储数据信息
    private List<Infos> listDatas = new ArrayList<>();
    //默认统计图的颜色配置 如果数据大于4 则颜色轮询
    private String colorRes[];
    private int mPanelWidth;
    private String stringText = "车辆监管";

    public SelfStatistics(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    public SelfStatistics(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelfStatistics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelfStatistics(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public float[] getDatas() {
        return datas;
    }

    public String[] getColorRes() {
        return colorRes;
    }

    public void setColorRes(String[] colorRes) {
        this.colorRes = colorRes;
    }

    public void startDraw() {
        invalidate();
    }

    public void setDatas(float[] datas) {
        this.datas = datas;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (datas != null && datas.length > 0) {
            calculateDatas();
            //获取圆心的x坐标
            int center = mPanelWidth / 2;
            //圆环的半径
            int radius = center - 10;
            //消除锯齿
            paint.setAntiAlias(true);
            //给外圈设置样式
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            //设置StrokeWidth
            paint.setStrokeWidth(20);
            //给外圈设置颜色
            paint.setColor(Color.WHITE);
            //画最外层的圈
            canvas.drawCircle(center, center, radius, paint);
            //设置进度的颜色
            for (Infos infos : listDatas) {
                //定义一个RectF类
                if (infos.getCurrData() != 0)
                    paint.setColor(Color.parseColor(infos.getColor()));
                RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
                //绘制扇形
                canvas.drawArc(rectF, infos.getStartAngle(), infos.getEndAngle(), true, paint);
            }
            //画最外层的圈
            paint.setColor(Color.WHITE);
            canvas.drawCircle(center, center, radius - 20, paint);

            drawTexts(canvas, center);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = Math.min(w, h);
    }

    private void drawTexts(Canvas canvas, int center) {
        float total = 0;
        for (int i = 0; i < datas.length; i++) {
            total += datas[i];
        }
        paint.setStrokeWidth(2);
        //设置进度扇形的样式
        paint.setStyle(Paint.Style.FILL);
        //设置文字的大小
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp15));
        int widthStr1 = (int) paint.measureText(stringText);
        paint.setColor(Color.parseColor("#333333"));
        float baseX = center - widthStr1 / 2;
        float baseY = center + 20 / 4;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = baseY + offY;
        canvas.drawText(stringText, baseX, newY, paint);
    }

    //为绘画做基本的计算
    private void calculateDatas() {
        float total = 0;
        float tempAngle = 270;
        //计算出总数
        for (int i = 0; i < datas.length; i++) {
            total += datas[i];
        }
        //创建不同的Infos对象
        Infos infos;
        for (int i = 0; i < datas.length; i++) {
            infos = new Infos();
            float currData = datas[i];
            float startAngle = tempAngle;
            float endAngle = (float) (currData * 100 / total * 3.6);
            infos.setCurrData(currData);
            infos.setStartAngle(startAngle);
            infos.setEndAngle(endAngle);
            infos.setColor(colorRes[i % colorRes.length]);
            tempAngle = endAngle + tempAngle;
            listDatas.add(infos);
        }
    }

    public void setPaintText(String string) {
        this.stringText = string;
    }

    class Infos {
        private float currData;
        private float startAngle;
        private float endAngle;
        private String color;

        public float getCurrData() {
            return currData;
        }

        public void setCurrData(float currData) {
            this.currData = currData;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public float getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        public float getEndAngle() {
            return endAngle;
        }

        public void setEndAngle(float endAngle) {
            this.endAngle = endAngle;
        }
    }
}
