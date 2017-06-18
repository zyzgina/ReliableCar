package com.kaopujinfu.appsys.thecar.applys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ApplyOrSupervisesFromAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;


/**
 * 申请清单
 * Created by 左丽姬 on 2017/5/11.
 */
public class ApplyActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_applyfrom;
    private ListView applyFromList;
    private ApplyOrSupervisesFromAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyform);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("申请清单");
        top_meun.setVisibility(View.GONE);

        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0, padding, 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));

        applyFromList = (ListView) findViewById(R.id.applyFromList);
        mAdapter = new ApplyOrSupervisesFromAdapter(this, true);
        applyFromList.setAdapter(mAdapter);
        applyFromList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ApplyActivity.this, ApplyDetailsActivity.class);
                startActivity(intent);
            }
        });

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_applyfrom = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_applyfrom);
        refreshLayout_applyfrom.setHeaderView(progressLayout);
        refreshLayout_applyfrom.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                refreshLayout.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
            }
        });

        ImageView newApply = (ImageView) findViewById(R.id.newApply);
        newApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyActivity.this, AddApplyActivity.class);
                startActivity(intent);
            }
        });

    }
}
