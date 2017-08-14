package com.kaopujinfu.appsys.thecar.menu;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.VersionBean;

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
        VersionBean.VersionEntity versionEntity= (VersionBean.VersionEntity) getIntent().getSerializableExtra("versionEntity");
        TextView versionMsg= (TextView) findViewById(R.id.versionMsg);
        Button btnVersion= (Button) findViewById(R.id.btnVersion);
        if(versionEntity!=null){
            PackageManager manager = this.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(this.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String version = info.versionName;
            int vernum = version.compareTo(versionEntity.getAppVersion());
            if (vernum < 0) {
                btnVersion.setVisibility(View.VISIBLE);
                versionMsg.setText("最新版本：v"+versionEntity.getAppVersion()+"\n\n更新内容：\n"+versionEntity.getChangeLog());
            }else{
                versionMsg.setText("当前版本：\n\n"+versionEntity.getChangeLog());
            }
        }
        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.25pp.com/android/detail_7621405/");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });
    }
}
