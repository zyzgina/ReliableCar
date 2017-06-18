package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VibratorUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.RippleTelView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ChecksdetailsAdapter;
import com.kaopujinfu.appsys.thecar.service.BluetoothLeService;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.reliablel.voiceproject.VoiceUtils;

import net.tsz.afinal.FinalDb;

import java.util.List;

import static com.kaopujinfu.appsys.thecar.service.BluetoothLeService.ACTION_BLUE_DISCONNECTED;


/**
 * Created by 左丽姬 on 2017/5/9.
 */

public class DeviceControlActivity extends BaseNoScoActivity implements View.OnClickListener {
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private String mDeviceName, mDeviceAddress;
    private ListView mListsDevicecontrol;
    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = false;
    private ImageView mCar;
    private ChecksdetailsAdapter checksdetailsAdapter;
    private TwinklingRefreshLayout mRefreshLayout;
    private FinalDb db;
    private String taskCode;
    private double longitude, latitude;
    private MapUtils mapUtils;
    private VoiceUtils voiceUtils;
    private Handler dHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.CONSTANT_ZERO:
                    foundDevice();
                    handler.sendEmptyMessageDelayed(0, 3000);
                    break;
            }
        }
    };


    private TextView mAlreadynum, mSurplusnum, mBusiness, mCompany, mStart;
    private RippleTelView mRippleTelView, mCommitrippleTelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicecontrol);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        voiceUtils=new VoiceUtils();
        voiceUtils.initialTts(this);
        initView();
    }


    public void initView() {
        mTvTitle.setText("RFID盘库");
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        taskCode = getIntent().getStringExtra("taskCode");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        mtop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        mAlreadynum = (TextView) findViewById(R.id.alreadynum_devicecontrol);
        mSurplusnum = (TextView) findViewById(R.id.surplusnum_devicecontrol);
        mBusiness = (TextView) findViewById(R.id.business_devicecontrol);
        mCompany = (TextView) findViewById(R.id.company_devicecontrol);

        List<TaskItemBean.TaskItemsEntity> lists = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" ");
        if (lists.size() > 0) {
            mBusiness.setText("经销商：" + lists.get(0).getDistributor());
            mCompany.setText("金融公司：" + lists.get(0).getFinance());
        }


        mRippleTelView = (RippleTelView) findViewById(R.id.rippleTelView_devicecontrol);
        mCommitrippleTelView = (RippleTelView) findViewById(R.id.commitrippleTelView_devicecontrol);
        mCommitrippleTelView.setColor(getResources().getColor(R.color.green));
        mStart = (TextView) findViewById(R.id.start_devicecontrol);
        mCar = (ImageView) findViewById(R.id.car_devicecontrol);
        mStart.setText("扫描中...");
        mRippleTelView.startRippleAnimation();
        dHandler.sendEmptyMessage(IBase.CONSTANT_ZERO);

        mDeviceName = getIntent().getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS);


        mListsDevicecontrol = (ListView) findViewById(R.id.lists_devicecontrol);
        checksdetailsAdapter = new ChecksdetailsAdapter(this);
        mListsDevicecontrol.setAdapter(checksdetailsAdapter);
        getAddData();


        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_devicecontrol);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setHeaderView(progressLayout);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (isRefresh) {
                    page = 1;
                    isRefresh = false;

                }
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mRefreshLayout.finishLoadmore();
            }
        });

        registerReceiver(mBroadcastReceiver, getIntentFilter());
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        LogUtils.debug("Try to bindService=" + bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE));
        mapUtils = new MapUtils(getApplicationContext());
        mapUtils.initOnceLocation();
        mapUtils.startLocation(new LoactionListener() {
            @Override
            public void getOnLoactionListener(double longitude, double latitude) {
                DeviceControlActivity.this.longitude = longitude;
                DeviceControlActivity.this.latitude = latitude;
            }
        });
    }

    private boolean isSuccess = false;
    private String addStr = "";
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {  //连接成功
                LogUtils.debug("连接成功");
                isSuccess = true;
                mCommitrippleTelView.setColor(getResources().getColor(R.color.green));
                mRippleTelView.setVisibility(View.GONE);
                mCommitrippleTelView.setVisibility(View.VISIBLE);
                mRippleTelView.stopRippleAnimation();
                mCommitrippleTelView.startRippleAnimation();
                mStart.setVisibility(View.GONE);
                mCar.setVisibility(View.VISIBLE);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) { //断开连接
                LogUtils.debug("断开连接");
                mConnected = false;
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //可以开始干活了
                mConnected = true;
                ShowDialog();
                LogUtils.debug("In what we need");
            } else if (ACTION_BLUE_DISCONNECTED.equals(action)) {
                //断开连接
                LogUtils.debug("设备断开.....");
                if (isSuccess)
                    IBaseMethod.showToast(DeviceControlActivity.this, "设备已断开,请重新搜索", IBase.RETAIL_TWO);
                else
                    IBaseMethod.showToast(DeviceControlActivity.this, "未连接到设备,请重新搜索", IBase.RETAIL_TWO);
                finish();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //收到数据
                LogUtils.debug("RECV DATA");
                String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if (data != null) {
                    if (data.contains("S03") || data.contains("S01") || data.contains("S02")) {
                        addStr = data;
                    } else {
                        addStr += data.trim();
                    }
                    if (addStr.contains("S02") && addStr.contains("E02")) {
//                        LogUtils.debug("扫描数据:" + addStr);
                        String[] strs = addStr.split(";");
//                        LogUtils.debug("切割的长度:" + strs.length);
                        if (strs.length > 3) {
                            String epc = strs[2];
                            LogUtils.debug("epc====" + epc);
//                            epc="E2000016910200660890C186";
                            String query = "taskCode=\"" + taskCode + "\" and rfidEpc like '%" + epc + "%'";
                            List<TaskItemBean.TaskItemsEntity> lists = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, query);
                            LogUtils.debug("模糊查询数据库:" + lists.size());
                            if (lists.size() > 0) {
                                TaskItemBean.TaskItemsEntity entity = lists.get(0);
                                if (entity.getCommit_status() == 0) {
                                    entity.setCommit_status(1);
                                    entity.setCheckMethod(IBase.RFIDCODE);
                                    entity.setCheckTime(DateUtil.getSimpleDateYYYYMMDDHHMMMSS(System.currentTimeMillis()));
                                    entity.setLat(latitude + "");
                                    entity.setLng(longitude + "");
                                    db.update(entity);
                                    VibratorUtil.Vibrate(DeviceControlActivity.this, 30);
                                    voiceUtils.startSpeek("盘库成功");
                                }else{
                                    voiceUtils.startSpeek("该车已盘库");
                                }
                                List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\"");
                                List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\" and commit_status=0");
                                mAlreadynum.setText((finish.size() - nofinish.size()) + "");
                                mSurplusnum.setText(nofinish.size() + "");
                                getAddData();
                                if (nofinish.size() == 0) {
                                    IBaseMethod.showToast(DeviceControlActivity.this, "该车库盘库已完成", IBase.RETAIL_TWO);
                                    finish();
                                }
                            }else{
                                voiceUtils.startSpeek("查询失败");
                            }
                        }
                    }
                    LogUtils.debug("RECV DATA===========addStr=========" + addStr);
                }
            }
        }
    };

    /**
     * 获取已盘数据
     */
    public void getAddData() {
        List<TaskItemBean.TaskItemsEntity> lists = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" and commit_status=1");
        checksdetailsAdapter.clearItemList();
        checksdetailsAdapter.updateItemList(lists);
    }

    /**
     * 注册广播
     */
    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_BLUE_DISCONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        return intentFilter;
    }


    private void ShowDialog() {
        IBaseMethod.showToast(this, "连接成功，现在可以正常通信！", IBase.RETAIL_ONE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == android.R.id.home) {
            if (mConnected) {
                mBluetoothLeService.disconnect();
                mConnected = false;
            }
            onBackPressed();
        } else if (i == R.id.top_back) {
            finish();

        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.DeviceBind) service).getService();
            if (!mBluetoothLeService.initialize()) {
                LogUtils.debug("Unable to initialize Bluetooth");
                finish();
            }
            if (mBluetoothLeService != null) {
                mConnected = true;
                mBluetoothLeService.connect(mDeviceAddress);
            }
            LogUtils.debug("mBluetoothLeService is okay");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        voiceUtils.releaseSpeek();
        unregisterReceiver(mBroadcastReceiver);
        unbindService(mServiceConnection);
        mRippleTelView.stopRippleAnimation();
        mCommitrippleTelView.stopRippleAnimation();
    }

    /**
     * 退出提示对话框
     */
    private void showExitDialog() {
        List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" and commit_status=0");
        if (nofinish.size() != 0) {
            DialogUtil.prompt(this, "你正在执行RFID盘库操作，是否确认退出?", "继续盘库", "退出", new DialogButtonListener() {

                @Override
                public void ok() {
                    mBluetoothLeService.disconnect();
                    mConnected = false;
                    finish();
                }

                @Override
                public void cancel() {
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }


    /**
     * 获取盘库数据
     */
    private void foundDevice() {
        List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\"");
        List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + taskCode + "\" and commit_status=0");
        mSurplusnum.setText(nofinish.size() + "");
        mAlreadynum.setText((finish.size() - nofinish.size()) + "");
        if (nofinish.size() == 0) {
            mRippleTelView.stopRippleAnimation();
            mCommitrippleTelView.stopRippleAnimation();
        }

    }



}