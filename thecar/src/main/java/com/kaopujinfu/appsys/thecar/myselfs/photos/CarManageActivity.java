package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.timeselection.view.TimeSelector;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;

import java.util.Calendar;

/**
 * Created by 左丽姬 on 2017/5/24.
 */

public class CarManageActivity extends BaseActivity implements View.OnClickListener {
    private EditText offerCarManage, guidanceCarManage, tripCarManage, telCarManage, nameCarManage;
    private TextView dateCarManage, addrCarManage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmanage);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mTvTitle.setText("车辆信息管理");
        top_btn.setText("修改");
        top_btn.setVisibility(View.VISIBLE);
        top_meun.setVisibility(View.GONE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);


        offerCarManage = (EditText) findViewById(R.id.offerCarManage);
        guidanceCarManage = (EditText) findViewById(R.id.guidanceCarManage);
        tripCarManage = (EditText) findViewById(R.id.tripCarManage);
        telCarManage = (EditText) findViewById(R.id.telCarManage);
        nameCarManage = (EditText) findViewById(R.id.nameCarManage);

        dateCarManage = (TextView) findViewById(R.id.dateCarManage);
        addrCarManage = (TextView) findViewById(R.id.addrCarManage);
        dateCarManage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateCarManage) {
            String startDate = DateUtil.getSimpleDateYYYYMMDDHHMMM(System.currentTimeMillis());
            Calendar c = Calendar.getInstance(); //当前时间
            c.add(Calendar.YEAR, 100);
            String endDate = DateUtil.getSimpleDateYYYYMMDDHHMMM(c.getTimeInMillis());
            LogUtils.debug("当前时间" + startDate + "   " + endDate);
            TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                @Override
                public void handle(String time) {
                    dateCarManage.setText(time.substring(0, time.indexOf(" ")));
                }
            }, startDate, endDate);
            timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日

            timeSelector.setIsLoop(false);
            timeSelector.show();
        }
    }
}
