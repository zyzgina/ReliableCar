package com.kaopujinfu.appsys.thecar.supervises;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ApplyOrSupervisesFromAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;


/**
 * 监管清单
 * Created by 左丽姬 on 2017/5/11.
 */
public class SupervisesActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_supervisesfrom;
    private ListView supervisesFromList;

    private ApplyOrSupervisesFromAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervises);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        mTvTitle.setText("监管清单");
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);

        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0, padding, 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));

        mAdapter = new ApplyOrSupervisesFromAdapter(this, false);
        supervisesFromList = (ListView) findViewById(R.id.supervisesFromList);
        supervisesFromList.setAdapter(mAdapter);
        supervisesFromList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SupervisesActivity.this, SupervisesDetailsActivity.class);
                startActivity(intent);
            }
        });

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_supervisesfrom = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_supervisesfrom);
        refreshLayout_supervisesfrom.setHeaderView(progressLayout);
        refreshLayout_supervisesfrom.setOnRefreshListener(new RefreshListenerAdapter() {
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
    }
}
