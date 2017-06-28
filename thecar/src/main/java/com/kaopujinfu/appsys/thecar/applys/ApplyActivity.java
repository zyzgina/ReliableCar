package com.kaopujinfu.appsys.thecar.applys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ApplyOrSupervisesFromAdapter;
import com.kaopujinfu.appsys.thecar.bean.ApplyBean;
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
    private LinearLayout mNoDate;

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
        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无申请清单");
        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0, padding, 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setVisibility(View.GONE);
        LinearLayout applyLinear= (LinearLayout) findViewById(R.id.applyLinear);
        applyLinear.setPadding(0, padding, 0, 0);

        applyFromList = (ListView) findViewById(R.id.applyFromList);
        mAdapter = new ApplyOrSupervisesFromAdapter(this, true);
        applyFromList.setAdapter(mAdapter);
        applyFromList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplyBean.ApplyItemsEntity itemsEntity = mAdapter.getApplyItems(i);
                Intent intent = new Intent(ApplyActivity.this, ApplyDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("applyItems",itemsEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_applyfrom = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_applyfrom);
        refreshLayout_applyfrom.setHeaderView(progressLayout);
        refreshLayout_applyfrom.startRefresh();
        refreshLayout_applyfrom.setEnableLoadmore(false);
        refreshLayout_applyfrom.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }else{
                    refreshLayout_applyfrom.finishRefreshing();
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                page++;
                getDate();
            }
        });
    }

    private void getDate() {
        HttpBank.getIntence(this).httpApply(IBaseUrl.ACTION_APP_LIST1,"", new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("申请清单:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60, handler);
                    refreshLayout_applyfrom.finishRefreshing();
                } else {
                    refreshLayout_applyfrom.finishLoadmore();
                }
                ApplyBean bean = ApplyBean.getApplyBean(o.toString());
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearApply();
                    }
                    mAdapter.setApplyList(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    applyFromList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        applyFromList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_applyfrom.finishRefreshing();
                } else {
                    refreshLayout_applyfrom.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    applyFromList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(ApplyActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }
}
