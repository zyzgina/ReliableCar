package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;


/**
 * 查看任务详细并提交任务反馈
 * Created by 左丽姬 on 2017/5/12.
 */

public class MissionCommitActivity extends BaseNoScoActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missioncommit);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        mTvTitle.setText("任务详情");
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setText("提交");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);

    }
}
