package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.PermissionsUntils;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle.ConstantApp;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle.WorkerThread;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ChecksdetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.TaskListBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Describe:视频盘库
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class CheacksVideoActivity extends BaseNoScoActivity {
    private LinearLayout mNodate;
    private TwinklingRefreshLayout mRefreshLayout;
    private ListView mLists;
    private ChecksdetailsAdapter detailsAdapter;
    private String taskCode;
    private TextView yes_checksdetails, vinnum_checksdetails, rfidnum_checksdetails, no_checksdetails;
    private TaskListBean.TaskListItem item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksdetails);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        //请求语音权限
        if(!PermissionsUntils.checkRecordPermissions(this)){
            PermissionsUntils.requesetRecordPermissions(this);
        }
    }

    @Override
    public void initView() {
        dialog.dismiss();
        item = (TaskListBean.TaskListItem) getIntent().getSerializableExtra("item");
        taskCode = item.getTaskCode();
        mTvTitle.setText(item.getDlrShortName());
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_meun.setVisibility(View.GONE);
        dialog.dismiss();

        Button vinChecksdetails= (Button) findViewById(R.id.vinChecksdetails);
        vinChecksdetails.setVisibility(View.GONE);
        Button rfidChecksdetails= (Button) findViewById(R.id.rfidChecksdetails);
        rfidChecksdetails.setVisibility(View.GONE);
        Button videoChecksdetails= (Button) findViewById(R.id.videoChecksdetails);
        videoChecksdetails.setVisibility(View.VISIBLE);

        yes_checksdetails = (TextView) findViewById(R.id.yes_checksdetails);
        yes_checksdetails.setText(item.getCheckCount() + "");

        vinnum_checksdetails = (TextView) findViewById(R.id.vinnum_checksdetails);
        vinnum_checksdetails.setText("VIN盘库: " + item.getVinCount());

        rfidnum_checksdetails = (TextView) findViewById(R.id.rfidnum_checksdetails);
        rfidnum_checksdetails.setText("RFID盘库: " + item.getRfidCount());

        no_checksdetails = (TextView) findViewById(R.id.no_checksdetails);
        no_checksdetails.setText(item.getCarCount() - item.getCheckCount() + "");


        mNodate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNodate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无车辆数据");

        mLists = (ListView) findViewById(R.id.lists_checksdetails);
        detailsAdapter = new ChecksdetailsAdapter(this);
        mLists.setAdapter(detailsAdapter);

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.checkdetails_refreshLayout);
        mRefreshLayout.setEnableLoadmore(false);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setHeaderView(progressLayout);
        mRefreshLayout.startRefresh();
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if(isRefresh){
                    page=1;
                    isRefresh=false;
                    getDate();
                }else{
                    refreshLayout.finishRefreshing();
                }

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
            }
        });
    }
    /**
     * 获取数据
     */
    private void getDate() {
        HttpBank.getIntence(this).getTaskItem(taskCode, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("获取车辆数据:" + o.toString());
                TaskItemBean bean = TaskItemBean.getTaskItemBean(o.toString(), item.getDlrShortName(), item.getAfcShortName());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
                    detailsAdapter.clearItemList();
                    mRefreshLayout.finishRefreshing();
                }
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    detailsAdapter.addItemList(bean.getItems(),1);
                    mNodate.setVisibility(View.GONE);
                    mLists.setVisibility(View.VISIBLE);
                } else {
                    mNodate.setVisibility(View.VISIBLE);
                    mLists.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (page == 1)
                    mRefreshLayout.finishRefreshing();
                IBaseMethod.implementError(CheacksVideoActivity.this, errorNo);
            }
        });
    }

    /**
     * 视频盘库
     * */
    public void checkVideo(View view){
        Intent i = new Intent(CheacksVideoActivity.this, VideoActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, "23");
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_KEY, "");
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, "");
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE,"AES-128-XTS");
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_UID,6);
        WorkerThread.appId="363c3de5a41041fba59c73c4729b62dd";
        WorkerThread.recordKey="";
        startActivity(i);
    }

}
