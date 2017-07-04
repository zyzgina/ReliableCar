package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;

import net.tsz.afinal.FinalBitmap;

/**
 * Describe:图片预览
 * Created Author: Gina
 * Created Date: 2017/7/4.
 */

public class Picturepreview extends BaseNoScoActivity {
    private ImageView imagePreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturepreview);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        mTvTitle.setText("预览图片");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this),0,0);

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        String imagePath = getIntent().getStringExtra("imagePath");
        FinalBitmap.create(this).display(imagePreview, imagePath);
    }
}
