package com.kaopujinfu.appsys.thecar.supervises;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import com.kaopujinfu.appsys.thecar.adapters.SuperviseDetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.SupervicerDetailsBean;
import com.kaopujinfu.appsys.thecar.bean.SupervisersBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * 监管清单详情列表
 * Created by Doris on 2017/5/19.
 */
public class SupervisesDetailsActivity extends BaseNoScoActivity {

    private TwinklingRefreshLayout refreshLayout_supervisesDetails;
    private ListView supervisesDetailsList;
    private SuperviseDetailsAdapter mAdapter;
    private SupervisersBean.SupervisersItemsEntity itemsEntity;
    private LinearLayout mNoDate;
    private int maxTotal = IBase.limit;
    TextView alldetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        itemsEntity = (SupervisersBean.SupervisersItemsEntity) getIntent().getSerializableExtra("supervisers");
        if (itemsEntity != null)
            mTvTitle.setText(itemsEntity.getShortName());
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        top_meun.setVisibility(View.GONE);

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("未添加车辆监管");

        supervisesDetailsList = (ListView) findViewById(R.id.detailsList);
        mAdapter = new SuperviseDetailsAdapter(this);
        supervisesDetailsList.setAdapter(mAdapter);
        alldetailsList =new TextView(this);
        alldetailsList.setText("一一一一已加载全部一一一一");
        alldetailsList.setTextColor(getResources().getColor(R.color.gray));
        alldetailsList.setTextSize(12);
        alldetailsList.setGravity(Gravity.CENTER);
        alldetailsList.setPadding(0,getResources().getDimensionPixelOffset(R.dimen.common_measure_50sp),0,getResources().getDimensionPixelOffset(R.dimen.common_measure_50sp));

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_supervisesDetails = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_DetailsList);
        refreshLayout_supervisesDetails.setHeaderView(progressLayout);
        refreshLayout_supervisesDetails.startRefresh();
        refreshLayout_supervisesDetails.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                int maxPage = maxTotal / IBase.limit;
                if (maxTotal % IBase.limit != 0)
                    maxPage += 1;
                page++;
                if (!isRefresh)
                    isRefresh = true;
                if (maxPage >= page) {
                    getDate();
                } else {
                    page--;
                    supervisesDetailsList.addFooterView(alldetailsList);
                    refreshLayout.finishLoadmore();
                    refreshLayout.setEnableLoadmore(false);
                }
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (isRefresh) {
                    refreshLayout.setEnableLoadmore(true);
                    supervisesDetailsList.removeFooterView(alldetailsList);
                    page = 1;
                    isRefresh = false;
                    getDate();
                } else {
                    refreshLayout.finishRefreshing();
                }
            }
        });
    }

    private void getDate() {
        HttpBank.getIntence(this).httpSuperviserDetails(itemsEntity.getCompanyCode(), page, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug(page + "   数据:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
                    refreshLayout_supervisesDetails.finishRefreshing();
                } else {
                    refreshLayout_supervisesDetails.finishLoadmore();
                }
                SupervicerDetailsBean bean = SupervicerDetailsBean.getSupervicerDetailsBean(o.toString());
                if(bean!=null){
                    maxTotal=bean.getTotal();
                }
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearDetails();
                    }
                    mAdapter.setDetailsList(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    supervisesDetailsList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        supervisesDetailsList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_supervisesDetails.finishRefreshing();
                } else {
                    refreshLayout_supervisesDetails.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    supervisesDetailsList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(SupervisesDetailsActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }
}
