package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
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
    String imagePath;
    @Override
    public void initView() {
        imagePath = getIntent().getStringExtra("imagePath");
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        top_btn.setText("删除");
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.deleteFile(imagePath);
                finish();
            }
        });
        mTvTitle.setText("预览图片");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this),0,0);

        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        FinalBitmap.create(this).display(imagePreview, imagePath);
    }
}
