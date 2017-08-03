package com.kaopujinfu.appsys.thecar.myselfs.cabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.CabinetDetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.CabinetDetailsBean;
import com.kaopujinfu.appsys.thecar.bean.CabinetListsBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Describe: 柜子详情
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class CabinetDetailsActivity extends BaseNoScoActivity {
    CabinetListsBean.CabinetEntity cabinetEntity;
    private GridView cabinerDeatilsList;
    private LinearLayout mNoDate;
    private TwinklingRefreshLayout refreshLayout;
    private CabinetDetailsAdapter mAdapter;
    private TextView toastTitle;
    int wh = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_cabinetdetails);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        WindowManager wm = this.getWindowManager();
        wh = wm.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        cabinetEntity = (CabinetListsBean.CabinetEntity) getIntent().getSerializableExtra("cabinetEntity");
        dialog.dismiss();
        mTvTitle.setText("柜体详情");
        top_meun.setVisibility(View.GONE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);

        mNoDate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNoDate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("尚未有柜体详情");


        cabinerDeatilsList = (GridView) findViewById(R.id.cabinerDeatilsList);
        mAdapter = new CabinetDetailsAdapter(this);
        mAdapter.setOnCabinetTocuhListenis(tocuhListenis);
        cabinerDeatilsList.setAdapter(mAdapter);
        cabinerDeatilsList.setOnItemClickListener(itemClickListener);

        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.cabinetDetailsRefreshLayout);
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

        toastTitle = (TextView) findViewById(R.id.toastTitle);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        toastTitle.measure(w, h);
        toastTitle.getBackground().setAlpha(180);
    }

    private void getDate() {
        HttpBank.getIntence(this).httpCabinetDetails(cabinetEntity.getId(), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("柜体详情:" + o.toString());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
                    refreshLayout.finishRefreshing();
                } else {
                    refreshLayout.finishLoadmore();
                }
                CabinetDetailsBean bean = CabinetDetailsBean.getCabinetDetailsBean(o.toString());
                if (bean != null && bean.getCells() != null && bean.getCells().size() > 0) {
                    if (page == 1) {
                        mAdapter.clearCabinetDetails();
                    }
                    mAdapter.addCabinetDetails(bean.getCells());
                    mNoDate.setVisibility(View.GONE);
                    cabinerDeatilsList.setVisibility(View.VISIBLE);
                } else {
                    if (mAdapter.getCount() == 0) {
                        mNoDate.setVisibility(View.VISIBLE);
                        cabinerDeatilsList.setVisibility(View.GONE);
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
                    cabinerDeatilsList.setVisibility(View.GONE);
                }
            }
        });
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toastTitle.setVisibility(View.GONE);
            CabinetDetailsBean.CellsEntity entity = (CabinetDetailsBean.CellsEntity) mAdapter.getItem(position);
            if (GeneralUtils.isEmpty(entity.getCContent()) || entity.getCContent().indexOf("]") != entity.getCContent().length() - 1) {
                Intent intent = new Intent(CabinetDetailsActivity.this, LockActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cellsEntity", entity);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };

    private CabinetDetailsAdapter.CabinetTocuhListenis tocuhListenis = new CabinetDetailsAdapter.CabinetTocuhListenis() {
        @Override
        public void onCabinetTocuhListenis(int poistion, boolean isVisibility, float x, float y) {
            if (isVisibility) {
                toastTitle.setVisibility(View.VISIBLE);
                CabinetDetailsBean.CellsEntity entity = (CabinetDetailsBean.CellsEntity) mAdapter.getItem(poistion);
                toastTitle.setText(entity.getCContent());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int top = (int) y - IBaseMethod.setPaing(CabinetDetailsActivity.this) - header.getHeight() + toastTitle.getMeasuredHeight();
                params.topMargin = top;
                int size = 0;
                if (wh != 0) {
                    size = wh / 2;
                }
                if (size < x) {
                    int xs = (int) x - size;
                    xs = size - xs;
                    params.rightMargin = xs;
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    params.leftMargin = (int) x;
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                toastTitle.setLayoutParams(params);
            } else
                toastTitle.setVisibility(View.GONE);

        }
    };
    @Subscribe
    public void onEventMainThread(JumpEventBus jumpEventBus) {
        if("柜体操作".equals(jumpEventBus.getName())){
            page=1;
            getDate();
        }
    }
}
