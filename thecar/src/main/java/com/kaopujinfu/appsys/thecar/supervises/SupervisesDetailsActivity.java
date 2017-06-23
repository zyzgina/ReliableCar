package com.kaopujinfu.appsys.thecar.supervises;

import android.os.Bundle;
import android.widget.ListView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.SuperviseDetailsAdapter;
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

        supervisesDetailsList = (ListView) findViewById(R.id.detailsList);
        mAdapter = new SuperviseDetailsAdapter(this);
        supervisesDetailsList.setAdapter(mAdapter);

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_supervisesDetails = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_DetailsList);
        refreshLayout_supervisesDetails.setHeaderView(progressLayout);
        refreshLayout_supervisesDetails.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                refreshLayout.finishLoadmore();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.finishRefreshing();
            }
        });
    }
}
