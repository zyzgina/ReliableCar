package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMissionAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Created by 左丽姬 on 2017/5/15.
 */

public class MissionListsActivity extends BaseNoScoActivity {
    private TwinklingRefreshLayout mRefreshLayout;
    private int page = 1;
    private boolean isRefresh = true;

    private MyselfMissionAdapter missionAdapter;
    private ListView mLists;
    private LinearLayout mNodate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionlists);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("我的任务");
        top_meun.setVisibility(View.GONE);

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.missionlists_refreshLayout);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setHeaderView(progressLayout);

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getDate();
                mRefreshLayout.finishLoadmore();
            }
        });


        mLists = (ListView) findViewById(R.id.lists_missionlists);
        missionAdapter = new MyselfMissionAdapter(this);
        missionAdapter.setStatus(IBase.CONSTANT_ONE);
        mLists.setAdapter(missionAdapter);
        mLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //我的任务列表及详情
                Intent intent = new Intent(MissionListsActivity.this, MissionCommitActivity.class);
                startActivity(intent);
            }
        });
        mNodate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNodate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无任务");
    }

    /**
     * 获取网络数据
     */
    private void getDate() {
        IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
    }

}
