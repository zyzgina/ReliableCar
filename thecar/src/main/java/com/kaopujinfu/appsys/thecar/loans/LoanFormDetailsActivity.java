package com.kaopujinfu.appsys.thecar.loans;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.kaopujinfu.appsys.thecar.adapters.LoanFromDetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.LoanDetailsBean;
import com.kaopujinfu.appsys.thecar.bean.LoanFormBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款清单列表详情
 * Created by Doris on 2017/5/22.
 */
public class LoanFormDetailsActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_loanfromdetails;
    private ListView loanFromDetailsList;
    private LoanFromDetailsAdapter mAdapter;
    private LinearLayout mNoDate;
    private  LoanFormBean.LoanItemsEntity itemsEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IBaseMethod.setBarStyle(this,getResources().getColor(R.color.car_theme));
        setContentView(R.layout.activity_details_list);
    }

    @Override
    public void initView() {
        itemsEntity = (LoanFormBean.LoanItemsEntity) getIntent().getSerializableExtra("LoanItems");
        mTvTitle.setText(itemsEntity.getDlrName());
        dialog.dismiss();
        top_meun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_press();
            }
        });

        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0,padding,0,0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        top_meun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_press();
            }
        });

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
        refreshLayout_loanfromdetails.startRefresh();
        refreshLayout_loanfromdetails.setEnableLoadmore(false);
        refreshLayout_loanfromdetails.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }else{
                    refreshLayout_loanfromdetails.finishRefreshing();
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

    /**
     * 弹出menu菜单
     */
    public void menu_press() {
        // 一个自定义的布局，作为显示的内容
        final String[] infos = getResources().getStringArray(R.array.loanSearch);
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
                    loanFromDetailsList.setVisibility(View.GONE);
                } else {
                    mNoDate.setVisibility(View.GONE);
                    loanFromDetailsList.setVisibility(View.VISIBLE);
                }
            }
        });
        shContextMenu.showMenu();
    }

    private void getDate() {
        HttpBank.getIntence(this).httpApply(IBaseUrl.ACTION_APP_LIST4,itemsEntity.getDlrCode(), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("申请清单:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
                    refreshLayout_loanfromdetails.finishRefreshing();
                } else {
                    refreshLayout_loanfromdetails.finishLoadmore();
                }
                LoanDetailsBean bean = LoanDetailsBean.getLoanDetailsBean(o.toString());
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearList();
                    }
                    mAdapter.setListDate(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    loanFromDetailsList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        loanFromDetailsList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_loanfromdetails.finishRefreshing();
                } else {
                    refreshLayout_loanfromdetails.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    loanFromDetailsList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(LoanFormDetailsActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }
}
