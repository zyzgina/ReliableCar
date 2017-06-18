package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.PhotosGridAdapter;

import java.io.File;

/**
 * 评估高清图
 * Created by 左丽姬 on 2017/5/23.
 */

public class AssessmentActivity extends BaseActivity implements View.OnClickListener {
    MyGridView assessmentGridview;
    private PhotosGridAdapter adapter;
    private File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        String type = getIntent().getStringExtra("type");
        String pathFile = getIntent().getStringExtra("path");
        file = new File(pathFile + "/" + type);
        if (!file.exists())
            file.mkdirs();

        mTvTitle.setText(type);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_btn.setText("保存");
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);


        assessmentGridview = (MyGridView) findViewById(R.id.assessmentGridview);
        adapter = new PhotosGridAdapter(this, file, getResources().getStringArray(R.array.assessment));
        assessmentGridview.setAdapter(adapter);
        assessmentGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = adapter.getString(position);
                LogUtils.debug("点击了：" + string);
                Intent intent = new Intent();
                intent.setClass(AssessmentActivity.this, UploadTaskActivity.class);
                intent.putExtra("type", string);
                intent.putExtra("path", file.getAbsolutePath());
                startActivityForResult(intent, IBase.RETAIL_NINE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_NINE && resultCode == IBase.RESUTL_THREE) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.top_btn) {
            setResult(IBase.RESUTL_THREE);
            finish();
        }
    }
}
