package com.kaopujinfu.appsys.thecar.myselfs.newcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.LableListAdapter;
import com.kaopujinfu.appsys.thecar.bean.CarListBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import static com.kaopujinfu.appsys.thecar.R.id.refreshLayout_task;

/**
 * Describe: 车辆绑标签列表
 * Created Author: Gina
 * Created Date: 2017/6/20.
 */

public class LableActivity extends BaseNoScoActivity implements View.OnClickListener {

    private TwinklingRefreshLayout refreshLayout_bindings;
    private ExpandableListView bindingsList;
    private LableListAdapter mAdapter;
    private boolean flag = false;//判读是否选择
    private RelativeLayout addBindings;
    private TextView newBindingsSelectNum;
    private LinearLayout newBindingsBottomLayout, delete, bindingsNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grouplist);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("监管清单");
        top_meun.setVisibility(View.GONE);
        top_btn.setText("选择");
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);

        addBindings = (RelativeLayout) findViewById(R.id.newTaskLayout);
        newBindingsBottomLayout = (LinearLayout) findViewById(R.id.newTaskBottomLayout);
        newBindingsSelectNum = (TextView) findViewById(R.id.num);
        delete = (LinearLayout) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        ImageView newDocument = (ImageView) findViewById(R.id.newTask);
        newDocument.setOnClickListener(this);
        TextView txtDocument = (TextView) findViewById(R.id.txtTask);
        txtDocument.setText("车辆标签绑定");

        bindingsList = (ExpandableListView) findViewById(R.id.taskList);
        bindingsList.setGroupIndicator(null); // 隐藏默认指示器
        mAdapter = new LableListAdapter(this, bindingsList);
        mAdapter.setUpdateBindingUIListener(new LableListAdapter.UpdateBindingUIListener() {
            @Override
            public void updateUI(int num) {
                newBindingsSelectNum.setText("已选择" + num + "个任务");
            }
        });
        bindingsList.setAdapter(mAdapter);

        bindingsNoData = (LinearLayout) findViewById(R.id.TaskNoData);
        TextView DocumentDaer = (TextView) findViewById(R.id.TaskDaer);
        DocumentDaer.setText("亲爱的监管员~");
        TextView DocumentMsg = (TextView) findViewById(R.id.TaskMsg);
        DocumentMsg.setText("您还没有绑定车辆标签哦~");

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_bindings = (TwinklingRefreshLayout) findViewById(refreshLayout_task);
        refreshLayout_bindings.setHeaderView(progressLayout);
        refreshLayout_bindings.startRefresh();
        refreshLayout_bindings.setEnableLoadmore(false);
        refreshLayout_bindings.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                initDate();
            }
        });
    }

    private void initDate() {
        HttpBank.getIntence(this).newCarList(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("获取数据:" + o.toString());
                refreshLayout_bindings.finishRefreshing();
                CarListBean carListBean = CarListBean.getCarListBean(o.toString());
                if (carListBean.isSuccess()) {
                    mAdapter.setListBean(carListBean);
                    if (mAdapter.getGroupCount() == 0) {
                        bindingsList.setVisibility(View.GONE);
                        bindingsNoData.setVisibility(View.VISIBLE);
                    } else {
                        bindingsNoData.setVisibility(View.GONE);
                        bindingsList.setVisibility(View.VISIBLE);
                        refreshLayout_bindings.setEnableRefresh(false);
                    }
                } else {
                    bindingsList.setVisibility(View.GONE);
                    bindingsNoData.setVisibility(View.VISIBLE);
                    IBaseMethod.showToast(LableActivity.this, carListBean.getComment(), IBase.RETAIL_ZERO);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                bindingsList.setVisibility(View.GONE);
                bindingsNoData.setVisibility(View.VISIBLE);
                refreshLayout_bindings.finishRefreshing();
                LogTxt.getInstance().writeLog("获取监管器绑定列表失败，错误编码：" + errorNo + "，错误信息：" + strMsg);
                IBaseMethod.showToast(LableActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            DialogUtil.prompt(this, "确认删除?", "取消", "删除", new DialogButtonListener() {
                @Override
                public void ok() {
                    mAdapter.delete();
                    flag = false;
                    top_btn.setText("选择");
                    addBindings.setVisibility(View.VISIBLE);
                    newBindingsBottomLayout.setVisibility(View.GONE);
                    mAdapter.updateSelected(flag);
                }

                @Override
                public void cancel() {

                }
            });
        } else if (v == top_btn) {
            if (flag) {
                flag = false;
                top_btn.setText("选择");
                addBindings.setVisibility(View.VISIBLE);
                newBindingsBottomLayout.setVisibility(View.GONE);
            } else {
                flag = true;
                top_btn.setText("取消");
                addBindings.setVisibility(View.GONE);
                newBindingsBottomLayout.setVisibility(View.VISIBLE);
            }
            mAdapter.updateSelected(flag);
        } else if (v.getId() == R.id.newTask) {
            Intent intent = new Intent(LableActivity.this, AddLableActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
