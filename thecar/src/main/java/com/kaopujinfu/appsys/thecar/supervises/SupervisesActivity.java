package com.kaopujinfu.appsys.thecar.supervises;

import android.content.Intent;
import android.os.Bundle;
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
import com.kaopujinfu.appsys.thecar.adapters.ApplyOrSupervisesFromAdapter;
import com.kaopujinfu.appsys.thecar.bean.SupervisersBean;
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
    private TextView mNoDate;

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

        mNoDate = (TextView) findViewById(R.id.no_date_text);
        mNoDate.setText("未添加监管数据");
        mNoDate.setVisibility(View.GONE);
        int padding = IBaseMethod.setPaing(this);
        header.setPadding(0, padding, 0, 0);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));

        mAdapter = new ApplyOrSupervisesFromAdapter(this, false);
        supervisesFromList = (ListView) findViewById(R.id.supervisesFromList);
        supervisesFromList.setAdapter(mAdapter);
        supervisesFromList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SupervisersBean.SupervisersItemsEntity itemsEntity = mAdapter.getSuperviserItem(i);
                Intent intent = new Intent(SupervisesActivity.this, SupervisesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("supervisers", itemsEntity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_supervisesfrom = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_supervisesfrom);
        refreshLayout_supervisesfrom.setHeaderView(progressLayout);
        refreshLayout_supervisesfrom.startRefresh();
        refreshLayout_supervisesfrom.setEnableLoadmore(false);
        refreshLayout_supervisesfrom.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                } else {
                    refreshLayout_supervisesfrom.finishRefreshing();
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
        HttpBank.getIntence(this).httpSupervises(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("监管清单:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60, handler);
                    refreshLayout_supervisesfrom.finishRefreshing();
                } else {
                    refreshLayout_supervisesfrom.finishLoadmore();
                }
                SupervisersBean bean = SupervisersBean.getSupervisersBean(o.toString());
                if (bean != null && bean.getItems().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearSuperviser();
                    }
                    mAdapter.setSpuerviserList(bean.getItems());
                    mNoDate.setVisibility(View.GONE);
                    supervisesFromList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        supervisesFromList.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (page == 1) {
                    refreshLayout_supervisesfrom.finishRefreshing();
                } else {
                    refreshLayout_supervisesfrom.finishLoadmore();
                }
                if (mAdapter.getCount() == 0) {
                    mNoDate.setVisibility(View.VISIBLE);
                    supervisesFromList.setVisibility(View.GONE);
                }
                IBaseMethod.showToast(SupervisesActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }

}
