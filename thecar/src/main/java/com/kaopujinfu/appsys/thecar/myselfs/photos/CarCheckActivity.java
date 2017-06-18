package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;

/**
 * 360度看车
 * Created by Doris on 2017/5/31.
 */
public class CarCheckActivity extends BaseNoScoActivity{

    private ImageView carCheckImage;

    // 当前显示的bitmap对象
    private static Bitmap bitmap;
    // 开始按下位置
    private int startX;
    // 当前位置
    private int currentX;
    // 当前图片的编号
    private int scrNum;
    // 资源图片集合
    private int[] srcs = new int[] { R.drawable.a1, R.drawable.a2,
            R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7};
    // 图片的总数
    private static int maxNum = 7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_check);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("车辆展示");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_btn.setVisibility(View.GONE);
        top_meun.setVisibility(View.GONE);

        // 初始化当前显示图片编号
        scrNum = 1;
        carCheckImage = (ImageView) findViewById(R.id.carCheckImage);
        carCheckImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentX = (int) event.getX();
                        // 判断手势滑动方向，并切换图片
                        if (currentX - startX > 10) {
                            modifySrcR();
                        } else if (currentX - startX < -10) {
                            modifySrcL();
                        }
                        // 重置起始位置
                        startX = (int) event.getX();
                        break;
                }
                return true;
            }
        });
    }

    // 向右滑动修改资源
    private void modifySrcR() {
        if (scrNum > maxNum) {
            scrNum = 1;
        }
        if (scrNum > 0) {
            bitmap = BitmapFactory.decodeResource(getResources(),
                    srcs[scrNum - 1]);
            carCheckImage.setImageBitmap(bitmap);
            scrNum++;
        }

    }

    // 向左滑动修改资源
    private void modifySrcL() {
        if (scrNum <= 0) {
            scrNum = maxNum;
        }
        if (scrNum <= maxNum) {
            bitmap = BitmapFactory.decodeResource(getResources(),
                    srcs[scrNum - 1]);
            carCheckImage.setImageBitmap(bitmap);
            scrNum--;
        }
    }
}
