package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by zuoliji  on 2017/4/1.
 */

public class TextProgressBar extends ProgressBar {
    private String str;
    private boolean isX=false;//false 数字； true 文字;
    private Paint mPaint;
    private int size=28;//设置文字的大小

    public TextProgressBar(Context context){
        super(context);
        initText();
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initText();
    }

    public TextProgressBar(Context context, AttributeSet attrs){
        super(context, attrs);
        initText();
    }

    @Override
    public void setProgress(int progress){
        if(!isX)
        setText(progress);
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.str, 0, this.str.length(), rect);
        this.mPaint.setTextSize(size);
//        int x = ((getWidth()-(int) ((getProgress() * 1.0f / this.getMax()) * 100))/ 2) - rect.centerX();// 让现实的字体处于中心位置;
        int x = (((int) ((getProgress() * (getWidth()/100)/ this.getMax()) * 100))/ 2) + rect.centerX();
        if(isX) {
            int cx=rect.centerX();
            if(cx<50) {
                cx = 50;
                x = getWidth() / 3 - cx;
            }else{
                x = getWidth() /2 - cx;
            }
        }
        int y = (getHeight() / 2) - rect.centerY();// 让显示的字体处于中心位置;;
        canvas.drawText(this.str, x, y, this.mPaint);
    }

    // 初始化，画笔
    private void initText(){
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);// 设置抗锯齿;;;;
        this.mPaint.setColor(Color.WHITE);
    }

    // 设置文字内容
    private void setText(int progress){
        int i = (int) ((progress * 1.0f / this.getMax()) * 100);
        this.str = String.valueOf(i) + "%";
    }
    /**设置文字*/
    public void setText(String progress){
        this.str = progress;
    }
    /**判断是文字还是数字*/
    public void isText(boolean isX){
        this.isX=isX;
    }
    /**设置文字的大小*/
    public void setSize(int size){
        this.size=size;
    }
}
