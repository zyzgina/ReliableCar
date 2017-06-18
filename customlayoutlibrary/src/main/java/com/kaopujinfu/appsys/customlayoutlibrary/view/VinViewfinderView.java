package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.kaopujinfu.appsys.customlayoutlibrary.R;


public final class VinViewfinderView extends View {

    /**
     * 刷新界面的时�?
     */
    private static final long ANIMATION_DELAY = 10L;
    // private static final int OPAQUE = 0xFF;
    private final Paint paint;
    private final Paint paintLine;
    // private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int frameColor;
    private final int laserColor;
    private int scannerAlpha;
    private int leftLine = 0;
    private int topLine = 0;
    private int rightLine = 0;
    private int bottomLine = 0;

    private Rect frame;

    int w, h;
    boolean boo = false;
    private Paint mTextPaint;
    private String mText;

    public VinViewfinderView(Context context, int w, int h) {
        super(context);
        this.w = w;
        this.h = h;
        paint = new Paint();
        paintLine = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        frameColor = resources.getColor(R.color.green);// 绿色
        laserColor = resources.getColor(R.color.red);// 红色
        scannerAlpha = 0;
    }

    public VinViewfinderView(Context context, int w, int h, boolean boo) {
        super(context);
        this.w = w;
        this.h = h;
        this.boo = boo;
        paint = new Paint();
        paintLine = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        frameColor = resources.getColor(R.color.green);// 绿色
        laserColor = resources.getColor(R.color.red);// 红色
        scannerAlpha = 0;
    }

    public void setLeftLine(int leftLine) {
        this.leftLine = leftLine;
    }

    public void setTopLine(int topLine) {
        this.topLine = topLine;
    }

    public void setRightLine(int rightLine) {
        this.rightLine = rightLine;
    }

    public void setBottomLine(int bottomLine) {
        this.bottomLine = bottomLine;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int t;
        int b;
        int l;
        int r;

        //	这个矩形就是中间显示的那个框�?
        int $t = h / 10;
        int ntmp = h * 3 / 10;
        t = ntmp;
        b = h - ntmp;
        int $l = (int) ((h - $t - $t) * 1.585);
        l = (w - $l) / 2;
        r = w - l;
        frame = new Rect(l, t, r, b);
        // 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        // 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paintLine.setColor(frameColor);
        paintLine.setStrokeWidth(10);
        paintLine.setAntiAlias(true);
        int num = (b - t) / 6;
        canvas.drawLine(l - 5, t, l + num, t, paintLine);
        canvas.drawLine(l, t, l, t + num, paintLine);

        canvas.drawLine(r, t, r - num, t, paintLine);
        canvas.drawLine(r, t - 5, r, t + num, paintLine);

        canvas.drawLine(l - 5, b, l + num, b, paintLine);
        canvas.drawLine(l, b, l, b - num, paintLine);

        canvas.drawLine(r, b, r - num, b, paintLine);
        canvas.drawLine(r, b + 5, r, b - num, paintLine);

        if (frame == null) {
            return;
        }
//        mText = "如果无法识别，请点击拍照按钮保存图像";
        mText = "";
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize((h - b) / 7);
        mTextPaint.setColor(frameColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mText, w / 2, b + (h - b) * 2 / 5, mTextPaint);
        /**
         * 当我们获得结果的时�?�，我们更新整个屏幕的内�?
         */
        postInvalidateDelayed(ANIMATION_DELAY);
    }
}
