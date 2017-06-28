package com.kaopujinfu.appsys.thecar.loans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.LoanFromAdapter;
import com.kaopujinfu.appsys.thecar.bean.LoanFormBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;


/**
 * 贷款清单
 * Created by 左丽姬 on 2017/5/11.
 */
public class LoanFormActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_loanfrom;
    private ListView loanFromList;
    private LoanFromAdapter mAdapter;
    private LinearLayout mNoDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        setContentView(R.layout.activity_details_list);
    }

    @Override
    public void initView() {
        mTvTitle.setText("贷款清单");
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);

        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0, padding, 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setVisibility(View.GONE);
        RelativeLayout loanFormRelative= (RelativeLayout) findViewById(R.id.loanFormRelative);
        loanFormRelative.setPadding(0, padding, 0, 0);

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无贷款清单");

        loanFromList = (ListView) findViewById(R.id.detailsList);
        mAdapter = new LoanFromAdapter(this);
        loanFromList.setAdapter(mAdapter);
        loanFromList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LoanFormBean.LoanItemsEntity itemsEntity=mAdapter.getLoanbean(i);
                Intent intent = new Intent(LoanFormActivity.this, LoanFormDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("LoanItems",itemsEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_loanfrom = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_DetailsList);
        refreshLayout_loanfrom.setHeaderView(progressLayout);
        refreshLayout_loanfrom.startRefresh();
        refreshLayout_loanfrom.setEnableLoadmore(false);
        refreshLayout_loanfrom.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }else{
                    refreshLayout_loanfrom.finishRefreshing();
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
        HttpBank.getIntence(this).httpApply(IBaseUrl.ACTION_APP_LIST3,"", new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("申请清单:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60, handler);
                    refreshLayout_loanfrom.finishRefreshing();
                } else {
                    refreshLayout_loanfrom.finishLoadmore();
                }
                LoanFormBean bean = LoanFormBean.getLoanFormBean(o.toString());
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearList();
                    }
                    mAdapter.setListDate(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    loanFromList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        loanFromList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_loanfrom.finishRefreshing();
                } else {
                    refreshLayout_loanfrom.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    loanFromList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(LoanFormActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }


}
