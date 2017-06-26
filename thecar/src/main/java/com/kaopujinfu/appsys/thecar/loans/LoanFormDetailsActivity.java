package com.kaopujinfu.appsys.thecar.loans;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.LoanFromDetailsAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * 贷款清单列表详情
 * Created by Doris on 2017/5/22.
 */
public class LoanFormDetailsActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_loanfromdetails;
    private ListView loanFromDetailsList;
    private LoanFromDetailsAdapter mAdapter;
    private LinearLayout mNoDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IBaseMethod.setBarStyle(this,getResources().getColor(R.color.car_theme));
        setContentView(R.layout.activity_details_list);
    }

    @Override
    public void initView() {
        mTvTitle.setText("诚信二手车");
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);

        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0,padding,0,0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无贷款数据");

        loanFromDetailsList = (ListView) findViewById(R.id.detailsList);
        mAdapter = new LoanFromDetailsAdapter(this);
        loanFromDetailsList.setAdapter(mAdapter);

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_loanfromdetails = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_DetailsList);
        refreshLayout_loanfromdetails.setHeaderView(progressLayout);
        refreshLayout_loanfromdetails.setOnRefreshListener(new RefreshListenerAdapter() {

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
