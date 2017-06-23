package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;

/**
 * Describe: 解决7.0图片canvas trying to draw too large(217055232bytes) bitmap问题
 * Created Author: Gina
 * Created Date: 2017/6/22.
 */

public class BitmapImageView extends ImageView {
    int width;
    int height;

    public BitmapImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.start_show)).getBitmap();
        canvas.drawBitmap(resizeBitmap(bitmap), 0, 0, null);
    }

    public Bitmap resizeBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int mScreenWidth = dm.widthPixels;
            int mScreenHeight = dm.heightPixels;
            Bitmap res = Bitmap.createScaledBitmap(bitmap, mScreenWidth, mScreenHeight, true);
            return res;
        } else {
            return null;
        }
    }
}
