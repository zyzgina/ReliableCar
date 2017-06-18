package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by Doris on 2017/5/10.
 */

public class CustomVideoView extends VideoView {

    private int defaultWidth = 1920, defaultHeigth = 1080;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getDefaultSize(defaultWidth, widthMeasureSpec);
        int heigth = getDefaultSize(defaultHeigth, heightMeasureSpec);
        setMeasuredDimension(width, heigth);
    }
}
