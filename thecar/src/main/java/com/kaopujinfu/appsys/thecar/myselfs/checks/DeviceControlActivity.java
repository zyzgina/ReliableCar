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
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VideoRecordActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VibratorUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.RippleTelView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.CaptureConfiguration;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.PredefinedCaptureConfigurations;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ChecksdetailsAdapter;
import com.kaopujinfu.appsys.thecar.service.BluetoothLeService;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.reliablel.voiceproject.VoiceUtils;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.util.ArrayList;
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
    private String taskCode, dlrShortName;
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
        voiceUtils = new VoiceUtils();
        voiceUtils.initialTts(this);
        initView();
    }


    public void initView() {
        mTvTitle.setText("RFID盘库");
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        taskCode = getIntent().getStringExtra("taskCode");
        dlrShortName = getIntent().getStringExtra("dlrShortName");
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
            mBusiness.setText("经销商：" + dlrShortName);
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
        mapUtils = new MapUtils(this);
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
    TaskItemBean.TaskItemsEntity entity;
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
//                            LogUtils.debug("模糊查询数据库:" + lists.size());
                            if (lists.size() > 0) {
                                entity = lists.get(0);
                                vinCode = entity.getVinNo();
                                tkCode = entity.getTaskCode();
                                int status_speek = entity.getCommit_status();
                                if (entity.getCommit_status() == 0) {
                                    entity.setCommit_status(1);
                                    entity.setCheckMethod(IBase.RFIDCODE);
                                    entity.setCheckTime(DateUtil.getSimpleDateYYYYMMDDHHMMMSS(System.currentTimeMillis()));
                                    entity.setLat(latitude + "");
                                    entity.setLng(longitude + "");
                                    db.update(entity);
                                    VibratorUtil.Vibrate(DeviceControlActivity.this, 30);
                                }
                                final List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\"");
                                final List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\" and commit_status=0");
                                mAlreadynum.setText((finish.size() - nofinish.size()) + "");
                                mSurplusnum.setText(nofinish.size() + "");
                                getAddData();
                                String content = "";
                                if (nofinish.size() > 0) {
                                    if (status_speek == 0) {
                                        content = "盘库成功剩余" + nofinish.size() + "台";
                                        if (entity.getAllowVideo() == 1) {
                                            showVideo();
                                        }
                                    } else {
                                        content = "该车已盘库";
                                    }
                                } else {
                                    content = "全部完成辛苦了";
                                    if (entity.getAllowVideo() == 1) {
                                        showVideo();
                                    }
                                }
                                integerList.add(nofinish.size());
                                if (nofinish.size() == 0 && num < integerList.size() && integerList.get(num) == 0) {
                                    voiceUtils.startSpeek("全部完成辛苦了", new VoiceUtils.SpeekEndListener() {
                                        @Override
                                        public void setSpeekEndListener(boolean b) {
                                            if (b) {
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
//                                                LogUtils.debug("全部完成了监听");
                                                voiceUtils.releaseSpeek();
                                                if (entity.getAllowVideo() == 0) {
                                                    finish();
                                                }
                                            }
                                        }
                                    });
                                    return;
                                }
                                goSpeek(content, integerList.get(num), nofinish.size());
                            } else {
                                integerList.add(-1);
                                goSpeek("查询失败", integerList.get(num), 1);
                            }
                        }
                    }
//                    LogUtils.debug("RECV DATA===========addStr=========" + addStr);
                }
            }
        }
    };
    private int num = 0;
    private boolean isExit = false;//判断是否退出该界面
    private List<Integer> integerList = new ArrayList<>();
    private boolean isTwo = true;//防止两次进入播报全部完成辛苦了

    private void goSpeek(String content, final int value, final int size) {
        if (GeneralUtils.isEmpty(content)) {
            num++;
        } else
            voiceUtils.startSpeek(content, new VoiceUtils.SpeekEndListener() {
                @Override
                public void setSpeekEndListener(boolean b) {
                    if (b && value != 0 && size == 0) {
//                    LogUtils.debug("进入了=====全部完成");
                        voiceUtils.stopSpeek();
                        if (isTwo) {
                            LogUtils.debug("进入全部完成播报语音");
                            isTwo = false;
                            goSpeek("全部完成辛苦了", 1, 0);
                        } else {
                            //判断是否进入了外部监听
                            LogUtils.debug("监听全部完成辛苦了播报");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            voiceUtils.releaseSpeek();
                            if (entity == null) {
                                finish();
                            }
                            if (entity != null && entity.getAllowVideo() == 0) {
                                finish();
                            }
                        }
                    }
                    //跳转至视频录制，停止语音播放
                    if (b && isShowLz && isTwo) {
                        isShowLz = false;
                        voiceUtils.stopSpeek();
                    }
                    //判断该页面是否销毁
                    if (b && isExit && size != 0) {
                        voiceUtils.releaseSpeek();
                    }
                    if (b) {
                        num++;
                    }
                }
            });
    }

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
        unregisterReceiver(mBroadcastReceiver);
        unbindService(mServiceConnection);
        mRippleTelView.stopRippleAnimation();
        mCommitrippleTelView.stopRippleAnimation();
        mapUtils.stopLocation();
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
                    isExit = true;
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

    private boolean isShowLz = false;

    /* 盘库视频录制 */
    private void showVideo() {
        DialogUtil.jumpCorrectErr(this, "该车盘库需要进行视频录制操作，请录制", "录 制", 2, getResources().getColor(android.R.color.holo_orange_light), false, new DialogButtonListener() {
            @Override
            public void ok() {
                isShowLz = true;
                CaptureConfiguration config = createCaptureConfiguration();
                Intent intent = new Intent(DeviceControlActivity.this, VideoRecordActivity.class);
                intent.putExtra(VideoRecordActivity.EXTRA_CAPTURE_CONFIGURATION, config);
//                final String dateStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
//                String pathname = "rfid_" + dateStamp + ".mp4";
//                intent.putExtra(VideoRecordActivity.SAVED_OUTPUT_FILENAME, pathname);
                startActivityForResult(intent, 101);
            }

            @Override
            public void cancel() {

            }
        });
    }

    private CaptureConfiguration createCaptureConfiguration() {
        // 设置大小(RES_360P/RES_480P/RES_720P/RES_1080P/RES_1440P/RES_2160P)
        final PredefinedCaptureConfigurations.CaptureResolution resolution = PredefinedCaptureConfigurations.CaptureResolution.RES_720P;
        // 设置清晰度 (HIGH高清、MEDIUM中等、LOW低配)
        final PredefinedCaptureConfigurations.CaptureQuality quality = PredefinedCaptureConfigurations.CaptureQuality.MEDIUM;
        CaptureConfiguration.Builder builder = new CaptureConfiguration.Builder(resolution, quality);
        // 设置视频最大时长，秒为单位
//        builder.maxDuration(60);
        // 设置视频最大大小，M为单位
//        builder.maxFileSize(5);
        // 设置每秒的帧数
//        builder.frameRate(5);
        // 设置是否显示时间
        // 显示时间
        builder.showRecordingTime();
        // 隐藏时间
//        builder.noCameraToggle();
        return builder.build();
    }

    private String vinCode = "", tkCode = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isShowLz = false;
            String path = data.getStringExtra(VideoRecordActivity.EXTRA_OUTPUT_FILENAME);
            //提交成功
            File file = new File(path);
            UploadBean uploadBean = IBaseMethod.saveUploadBean(file, tkCode + "_" + vinCode, "RFID盘库", latitude + "", longitude + "");
            FinalDb db = FinalDb.create(DeviceControlActivity.this, IBase.BASE_DATE, true);
            db.save(uploadBean);
            List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\" and commit_status=0");
            if (nofinish.size() == 0) {
                finish();
                try {
                    Thread.sleep(2000);
                    voiceUtils.releaseSpeek();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
