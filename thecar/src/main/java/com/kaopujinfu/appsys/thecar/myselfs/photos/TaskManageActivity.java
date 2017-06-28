package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.ReleaseIcno;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogReleaseListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务管理
 * Created by 左丽姬 on 2017/5/24.
 */

public class TaskManageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView taskManageQrcode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskmanage);
        IBaseMethod.setBarStyle(this,getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mTvTitle.setText("任务信息管理");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this),0,0);
        top_meun.setVisibility(View.GONE);
        top_btn.setText("分享");
        top_btn.setVisibility(View.VISIBLE);

        taskManageQrcode = (ImageView) findViewById(R.id.taskManageQrcode);
        Button taskManageDetails= (Button) findViewById(R.id.taskManageDetails);
        Button taskManageCarmsgMange= (Button) findViewById(R.id.taskManageCarmsgMange);
        Button taskManageRelease= (Button) findViewById(R.id.taskManageRelease);
        taskManageDetails.setOnClickListener(this);
        taskManageCarmsgMange.setOnClickListener(this);
        taskManageRelease.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.taskManageDetails){
            //查看展示详情页
            Intent intent = new Intent(this, CarDetailsActivity.class);
            startActivity(intent);
        }else  if(v.getId()==R.id.taskManageCarmsgMange){
            //车辆信息管理
            Intent intent=new Intent(this,CarManageActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.taskManageRelease){
            //发布
            showDialog();
        }
    }
    private void showDialog(){
        final List<ReleaseIcno> icnos=new ArrayList<>();
        ReleaseIcno icno1=new ReleaseIcno();
        icno1.setName("58同城");
        icno1.setIcon(R.drawable.ic_myself_icon_blue);

        ReleaseIcno icno2=new ReleaseIcno();
        icno2.setName("人人车");
        icno2.setIcon(R.drawable.ic_myself_icon_blue);

        ReleaseIcno icno3=new ReleaseIcno();
        icno3.setName("大搜车");
        icno3.setIcon(R.drawable.ic_myself_icon_blue);
        icnos.add(icno1);
        icnos.add(icno2);
        icnos.add(icno3);

        DialogUtil.selectReleaseDialog(this, icnos, new DialogReleaseListener() {
            @Override
            public void okClick(int position) {
                ReleaseIcno icno=icnos.get(position);
                LogUtils.debug("点击了:"+icno.getName());
            }

            @Override
            public void cancel() {

            }
        });
    }
}
