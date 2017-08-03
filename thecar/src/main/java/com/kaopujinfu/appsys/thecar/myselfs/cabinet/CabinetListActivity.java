package com.kaopujinfu.appsys.thecar.myselfs.cabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.CabinetListAdapter;
import com.kaopujinfu.appsys.thecar.bean.CabinetListsBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * Describe:柜体列表
 * Created Author: Gina
 * Created Date: 2017/7/18.
 */

public class CabinetListActivity extends BaseNoScoActivity {
    private TwinklingRefreshLayout refreshLayout;
    private LinearLayout mNoDate;
    private ListView cabinerList;
    private CabinetListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinetlist);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("柜体列表");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_meun.setVisibility(View.GONE);

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("尚未添加柜体");

        cabinerList = (ListView) findViewById(R.id.cabinerList);
        mAdapter = new CabinetListAdapter(this);
        cabinerList.setAdapter(mAdapter);
        cabinerList.setOnItemClickListener(itemClickListener);

        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.cabinerRefreshLayout);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setHeaderView(progressLayout);
        refreshLayout.startRefresh();
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                } else {
                    refreshLayout.finishRefreshing();
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                if (!isRefresh)
                    isRefresh = true;
                getDate();
            }
        });
    }

    private void getDate() {
        HttpBank.getIntence(this).httpCabinetLists(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("柜体列表:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }
                CabinetListsBean bean = CabinetListsBean.getCabinetListsBean(o.toString());
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearCabinetList();
                    }
                    mAdapter.setCabinetList(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    cabinerList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        cabinerList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    cabinerList.setVisibility(View.GONE);
                }
            }
        });
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CabinetListsBean.CabinetEntity entity = (CabinetListsBean.CabinetEntity) mAdapter.getItem(position);
            Intent intent = new Intent(CabinetListActivity.this, CabinetDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("cabinetEntity", entity);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

}
