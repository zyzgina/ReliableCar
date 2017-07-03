package com.kaopujinfu.appsys.thecar.applys;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.menu.SHContextMenu;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ContextMenuItem;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ApplyDetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.ApplyBean;
import com.kaopujinfu.appsys.thecar.bean.ApplyDetailsBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请清单详情列表
 * Created by Doris on 2017/5/19
 */
public class ApplyDetailsActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_applyDetails;
    private ListView applyDetailsList;
    private ApplyDetailsAdapter mAdapter;
    private LinearLayout mNoDate;
    private ApplyBean.ApplyItemsEntity applyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        applyItems = (ApplyBean.ApplyItemsEntity) getIntent().getSerializableExtra("applyItems");
        mTvTitle.setText(applyItems.getDlrName());
        top_meun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_press();
            }
        });
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("未找到贷款数据");

        applyDetailsList = (ListView) findViewById(R.id.detailsList);
        mAdapter = new ApplyDetailsAdapter(this);
        applyDetailsList.setAdapter(mAdapter);

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_applyDetails = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_DetailsList);
        refreshLayout_applyDetails.setHeaderView(progressLayout);
        refreshLayout_applyDetails.setEnableLoadmore(false);
        refreshLayout_applyDetails.startRefresh();
        refreshLayout_applyDetails.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                isRefresh = true;
                page++;
                getDate();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }
            }
        });
    }

    private void getDate() {
        HttpBank.getIntence(this).httpApply(IBaseUrl.ACTION_APP_LIST2, applyItems.getDlrCode(), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("申请清单详情:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60, handler);
                    refreshLayout_applyDetails.finishRefreshing();
                } else {
                    refreshLayout_applyDetails.finishLoadmore();
                }
                ApplyDetailsBean bean = ApplyDetailsBean.getApplyDetailsBean(o.toString());
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearList();
                    }
                    mAdapter.addList(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    applyDetailsList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        applyDetailsList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_applyDetails.finishRefreshing();
                } else {
                    refreshLayout_applyDetails.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    applyDetailsList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(ApplyDetailsActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }

    /**
     * 弹出menu菜单
     */
    public void menu_press() {
        // 一个自定义的布局，作为显示的内容
        final String[] infos = getResources().getStringArray(R.array.applySearch);
        ;
        SHContextMenu shContextMenu = new SHContextMenu(this);
        List<ContextMenuItem> itemList = new ArrayList<>();
        for (int i = 0; i < infos.length; i++) {
            itemList.add(new ContextMenuItem(infos[i]));
        }
        shContextMenu.setItemList(itemList);
        shContextMenu.setOnItemSelectListener(new SHContextMenu.OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                String name = infos[position];
                if (position == 0) {
                    mAdapter.setTitles(null);
                } else {
                    List<String> lists = mAdapter.getTitles();
                    List<String> titles = new ArrayList<String>();
                    if (lists.contains(name)) {
                        titles.add(name);
                    }
                    mAdapter.setTitles(titles);
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    applyDetailsList.setVisibility(View.GONE);
                } else {
                    mNoDate.setVisibility(View.GONE);
                    applyDetailsList.setVisibility(View.VISIBLE);
                }
            }
        });
        shContextMenu.showMenu();
    }
}
