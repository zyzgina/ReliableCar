package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ChecksdetailsAdapter;
import com.kaopujinfu.appsys.thecar.bean.TaskListBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/15.
 */

public class ChecksDetailsActivity extends BaseNoScoActivity implements View.OnClickListener {
    private LinearLayout mNodate;
    private TwinklingRefreshLayout mRefreshLayout;
    private ListView mLists;
    private ChecksdetailsAdapter detailsAdapter;
    private String taskCode;
    private TextView yes_checksdetails, vinnum_checksdetails, rfidnum_checksdetails, no_checksdetails;
    private static final int REQUECODE = 0x1055;
    private FinalDb db;
    private TaskListBean.TaskListItem item;
    private boolean flagCommit = false;//判断是从哪里提交

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksdetails);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        item = (TaskListBean.TaskListItem) getIntent().getSerializableExtra("item");
        taskCode = item.getTaskCode();
        mTvTitle.setText(item.getDlrShortName());
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_btn.setText("提交(0)");
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);
        top_meun.setVisibility(View.GONE);
        mtop_back.setOnClickListener(this);
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        db.deleteAll(TaskItemBean.TaskItemsEntity.class);
        dialog.dismiss();
        yes_checksdetails = (TextView) findViewById(R.id.yes_checksdetails);
        yes_checksdetails.setText(item.getCheckCount() + "");

        vinnum_checksdetails = (TextView) findViewById(R.id.vinnum_checksdetails);
        vinnum_checksdetails.setText("VIN盘库: " + item.getVinCount());

        rfidnum_checksdetails = (TextView) findViewById(R.id.rfidnum_checksdetails);
        rfidnum_checksdetails.setText("RFID盘库: " + item.getRfidCount());

        no_checksdetails = (TextView) findViewById(R.id.no_checksdetails);
        no_checksdetails.setText(item.getCarCount() - item.getCheckCount() + "");


        mNodate = (LinearLayout) findViewById(R.id.ll_nodate);
        mNodate.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("暂无申请清单");

        mLists = (ListView) findViewById(R.id.lists_checksdetails);
        detailsAdapter = new ChecksdetailsAdapter(this);
        mLists.setAdapter(detailsAdapter);

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.checkdetails_refreshLayout);
        mRefreshLayout.setEnableLoadmore(false);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setHeaderView(progressLayout);
        mRefreshLayout.startRefresh();
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                flagCommit = true;
                commitDate();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getDate();
                mRefreshLayout.finishLoadmore();
            }
        });
    }

    /**
     * 获取数据
     */
    private void getDate() {
        HttpBank.getIntence(this).getTaskItem(taskCode, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("获取车辆数据:" + o.toString());
                TaskItemBean bean = TaskItemBean.getTaskItemBean(o.toString(), item.getDlrShortName(), item.getAfcShortName());
                if (page == 1) {
                    IBaseMethod.jumpCountdown(60, handler);
                    detailsAdapter.clearItemList();
                    db.deleteAll(TaskItemBean.TaskItemsEntity.class);
                    mRefreshLayout.finishRefreshing();
                }
                if (bean != null && bean.getItems() != null && bean.getItems().size() > 0) {
                    detailsAdapter.addItemList(bean.getItems());
                    mNodate.setVisibility(View.GONE);
                    mLists.setVisibility(View.VISIBLE);
                } else {
                    mNodate.setVisibility(View.VISIBLE);
                    mLists.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (page == 1)
                    mRefreshLayout.finishRefreshing();
                IBaseMethod.implementError(ChecksDetailsActivity.this, errorNo);
            }
        });
    }

    /**
     * vin码盘库
     */
    public void checkVINCar(View view) {
        List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" and commit_status=0");
        if (nofinish.size() == 0) {
            IBaseMethod.showToast(this, "该车库盘库已完成", IBase.RETAIL_TWO);
            return;
        }
        Intent intent = new Intent(this, VINactivity.class);
        startActivityForResult(intent, REQUECODE);
    }

    /**
     * RFID盘库
     */
    public void checkRFIDar(View view) {
        List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" and commit_status=0");
        if (nofinish.size() == 0) {
            IBaseMethod.showToast(this, "该车库盘库已完成", IBase.RETAIL_TWO);
            return;
        }
        boolean flag = openBluetooth();
        if (flag) {
            Intent intent = new Intent(this, BluetoothActivity.class);
            intent.putExtra("taskCode", taskCode);
            startActivityForResult(intent, REQUECODE);
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, IBase.RETAIL_BLUETOOTH_OPEN);
        }
    }

    /**
     * 打开蓝牙
     */
    private boolean openBluetooth() {
        // 获取蓝牙管理者
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            return true;
        } else {
            mBluetoothAdapter.enable();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_BLUETOOTH_OPEN) {
            // 打开蓝牙设备
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(this, BluetoothActivity.class);
                intent.putExtra("taskCode", taskCode);
                startActivityForResult(intent, REQUECODE);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                IBaseMethod.showToast(this, "蓝牙已关闭", IBase.RETAIL_TWO);
            }
        }
        if (requestCode == REQUECODE) {
            //查询盘库数量
            List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\"");
            detailsAdapter.clearItemList();
            detailsAdapter.updateItemList(finish);
            List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and commit_status=0");
            List<TaskItemBean.TaskItemsEntity> vin = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and checkMethod=\"" + IBase.VINCODE + "\"");
            List<TaskItemBean.TaskItemsEntity> rfid = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and checkMethod=\"" + IBase.RFIDCODE + "\"");
            yes_checksdetails.setText((finish.size() - nofinish.size()) + "");
            List<TaskItemBean.TaskItemsEntity> commits = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and commit_status=1");
            top_btn.setText("提交(" + commits.size() + ")");
            vinnum_checksdetails.setText("VIN盘库: " + vin.size());
            rfidnum_checksdetails.setText("RFID盘库: " + rfid.size());
            no_checksdetails.setText(nofinish.size() + "");

        }
    }

    public void commitDate() {
        List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and commit_status=1");
        if (finish == null || finish.size() == 0) {
            if (!isRefresh) {
                mRefreshLayout.finishRefreshing();
            }
            if (flagCommit && isRefresh) {
                page = 1;
                isRefresh = false;
                getDate();
            }
            return;
        }
        if (!flagCommit) {
            dialog.show();
            dialog.setLoadingTitle("提交中");
        }
        String commit = new Gson().toJson(finish, new TypeToken<List<TaskItemBean.TaskItemsEntity>>() {
        }.getType());
        String str = "{\"total\":" + finish.size() + ",\"data\":" + commit + "}";
        LogUtils.debug("提交数据:" + str);
        HttpBank.getIntence(this).getSubmitTask(item.getTaskCode(), str, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("提交数据:" + o.toString());
                Result result = Result.getMcJson(o.toString());
                if (!flagCommit && result.isSuccess()) {
                    dialog.dismiss();
                    IBaseMethod.showToast(ChecksDetailsActivity.this, "提交成功", IBase.RETAIL_TWO);
                }
                if (result.isSuccess()) {
                    top_btn.setText("提交(0)");
                    if (flagCommit) {
                        getDate();
                    }
                }
                if (flagCommit && isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }
                if (!isRefresh) {
                    mRefreshLayout.finishRefreshing();
                }
                if (!flagCommit) {
                    getDate();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (flagCommit && isRefresh) {
                    page = 1;
                    isRefresh = false;
                    getDate();
                }
                if (!isRefresh) {
                    mRefreshLayout.finishRefreshing();
                }
                if (!flagCommit) {
                    getDate();
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == top_btn) {
            flagCommit = false;
            commitDate();
        } else if (v == mtop_back) {
            showExit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showExit() {
        flagCommit = false;
        List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + item.getTaskCode() + "\" and commit_status=1");
        if (finish.size() > 0) {
            DialogUtil.prompt(this, "你还有盘库数据未提交，是否提交？", "残忍拒绝", "提交", new DialogButtonListener() {
                @Override
                public void ok() {
                    commitDate();
                }

                @Override
                public void cancel() {
                    //删除本地数据
                    for (TaskItemBean.TaskItemsEntity itemsEntity : detailsAdapter.getList()) {
                        List<UploadBean> uploadBeens = db.findAllByWhere(UploadBean.class, "vinCode=\"" + itemsEntity.getTaskCode() + "_" + itemsEntity.getVinNo() + "\" and label=\"VIN码盘库\"");
                        if (uploadBeens.size() > 0) {
                            for (UploadBean ub : uploadBeens) {
                                db.delete(ub);
                                FileUtils.deleteFile(ub.getLoactionPath());
                            }
                        }
                    }
                    finish();
                }
            });
        } else {
            finish();
        }
    }
}
