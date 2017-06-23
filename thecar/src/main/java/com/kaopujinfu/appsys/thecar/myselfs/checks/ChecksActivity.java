package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ChecksListsAdapter;
import com.kaopujinfu.appsys.thecar.bean.TaskListBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * 盘库
 * Created by 左丽姬 on 2017/5/12.
 */

public class ChecksActivity extends BaseNoScoActivity {
    private TextView mNodate;
    private ListView mLists;
    private TwinklingRefreshLayout mRefreshLayout;
    private ChecksListsAdapter checksListsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checks);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("盘库");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_meun.setVisibility(View.GONE);

        mNodate = (TextView) findViewById(R.id.no_date_text);
        mNodate.setText("暂无盘库数据");
        mNodate.setVisibility(View.GONE);


        mLists = (ListView) findViewById(R.id.lists_checks);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.chechs_refreshLayout);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setHeaderView(progressLayout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.startRefresh();
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

        checksListsAdapter = new ChecksListsAdapter(this);
        mLists.setAdapter(checksListsAdapter);
        mLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskListBean.TaskListItem item = checksListsAdapter.selectItem(position);
                Intent intent = new Intent(ChecksActivity.this, ChecksDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);
                intent.putExtras(bundle);
                startActivityForResult(intent, IBase.RETAIL_CHECK);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getDate() {
        HttpBank.getIntence(this).getTaskList(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("获取任务列表数据:" + o.toString());
                TaskListBean bean = TaskListBean.getTaskListBean(o.toString());
                LogUtils.debug("解析数据:" + bean.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60 * 2, handler);
                    checksListsAdapter.clearList();
                }
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    checksListsAdapter.addList(bean.getItems());
                    mNodate.setVisibility(View.GONE);
                    mLists.setVisibility(View.VISIBLE);
                } else {
                    mNodate.setVisibility(View.VISIBLE);
                    mLists.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                mNodate.setVisibility(View.VISIBLE);
                mLists.setVisibility(View.GONE);
                if(errorNo==IBase.CONSTANT_ONE){
                    IBaseMethod.showToast(ChecksActivity.this,strMsg,IBase.RETAIL_ONE);
                }
                IBaseMethod.implementError(ChecksActivity.this, errorNo);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_CHECK) {
            getDate();
        }
    }
}
