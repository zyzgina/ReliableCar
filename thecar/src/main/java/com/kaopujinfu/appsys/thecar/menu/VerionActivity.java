package com.kaopujinfu.appsys.thecar.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;

/**
 * Describe: 版本更新
 * Created Author: Gina
 * Created Date: 2017/7/3.
 */

public class VerionActivity extends BaseNoScoActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        IBaseMethod.setBarStyle(this,getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        mTvTitle.setText("版本信息");
        top_meun.setVisibility(View.GONE);
        dialog.dismiss();
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        TextView versionName= (TextView) findViewById(R.id.versionName);
        versionName.setText("当前版本号："+ IBaseMethod.getVersion(this));
        TextView versionMsg= (TextView) findViewById(R.id.versionMsg);
    }
}
