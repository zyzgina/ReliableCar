package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.BluetoothMainAdapter;

import java.util.Set;

/**
 * 蓝牙模块
 * Created by 左丽姬 on 2017/5/8.
 */

public class BluetoothActivity extends Activity implements View.OnClickListener {
    private Button mScanningbluetooth, mScanningBlebluetooth;
    private MyListView mListsbluetooth;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothMainAdapter mBluetoothMainAdapter;
    private int mPosition = -1;
    private boolean isSm = false;
    private LinearLayout bluelists_buletooth;
    private TextView msg_bluetooth;
    private String bluetoothAddr;
    private String mName;
    String taskCode,dlrShortName;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.RETAIL_COUNTDOWN:
                    int time = (int) msg.obj;
                    if (time == 0) {
                        if (mBluetoothMainAdapter.lists.size() == 0) {
                            bluelists_buletooth.setVisibility(View.GONE);
                            msg_bluetooth.setVisibility(View.VISIBLE);
                            msg_bluetooth.setText("未搜索到蓝牙设备...");
                            mScanningbluetooth.setVisibility(View.GONE);
                            mScanningBlebluetooth.setText("重新搜索");
                            isSm = false;
                            scanningBle(false);
                            mScanningBlebluetooth.setBackgroundResource(R.drawable.button_bottom5_yellow);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueooth);
        setDialogWindowAttr(0.55);
        initView();
        initData();
    }

    public void setDialogWindowAttr(double nums) {
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.8
        p.height = (int) (d.getHeight() * nums);   //高度设置为屏幕的1.0
        getWindow().setAttributes(p);     //设置生效
        getWindow().setGravity(Gravity.CENTER);       //设置靠右对齐
    }

    public void initData() {

        // 判断设备是否支持BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            mScanningBlebluetooth.setVisibility(View.GONE);
        }
        // 获取蓝牙管理者
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        //获取蓝牙列表
        Set<BluetoothDevice> mSet = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice mDevice : mSet) {
//            mBluetoothMainAdapter.addDevice(mDevice);
        }
        mBluetoothMainAdapter.notifyData();
        mScanningbluetooth.setOnClickListener(this);
        mScanningBlebluetooth.setOnClickListener(this);
        mListsbluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                for (int i = 0; i < mBluetoothMainAdapter.getCount(); i++) {
                    View chilldView = mListsbluetooth.getChildAt(i);
                    chilldView.setBackgroundColor(getResources().getColor(R.color.white));
                    ImageView iv_bluetooth = (ImageView) chilldView.findViewById(R.id.iv_bluetooth);
                    iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_gray);
                }
                view.setBackgroundColor(getResources().getColor(R.color.plain));
                ImageView iv_bluetooth = (ImageView) view.findViewById(R.id.iv_bluetooth);
                iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_blue);
                if (isSm) {
                    mScanningBlebluetooth.setText("重新搜索");
                    scanningBle(false);
                    isSm = false;
                }
            }
        });
        mName = SPUtils.get(this, "bluetoothName", "").toString();
        bluetoothAddr = SPUtils.get(this, "bluetoothAddr", "").toString();
        if (GeneralUtils.isEmpty(bluetoothAddr)) {
            if (mBluetoothMainAdapter.lists.size() == 0) {
                bluelists_buletooth.setVisibility(View.GONE);
                msg_bluetooth.setVisibility(View.VISIBLE);
                msg_bluetooth.setText("请搜索蓝牙设备...");
                mScanningbluetooth.setVisibility(View.GONE);
                mScanningBlebluetooth.setText("搜索设备");
                mScanningBlebluetooth.setBackgroundResource(R.drawable.button_bottom5_yellow);
            } else {
                bluelists_buletooth.setVisibility(View.VISIBLE);
                msg_bluetooth.setVisibility(View.GONE);
            }
        } else {
            msg_bluetooth.setVisibility(View.VISIBLE);
            bluelists_buletooth.setVisibility(View.GONE);
            String addr = "将与蓝牙设备" + mName + "进行连接,请确认是否启动设备";
            SpannableStringBuilder builder = new SpannableStringBuilder(addr);
            ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.car_theme));
            builder.setSpan(yellowSpan, addr.indexOf("备") + 1, addr.indexOf("进"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            msg_bluetooth.setText(builder);
        }
    }

    public void initView() {
        taskCode = getIntent().getStringExtra("taskCode");
        dlrShortName = getIntent().getStringExtra("dlrShortName");
        mScanningbluetooth = (Button) findViewById(R.id.scanning_bluetooth);
        mScanningBlebluetooth = (Button) findViewById(R.id.scanningble_bluetooth);
        mListsbluetooth = (MyListView) findViewById(R.id.lists_bluetooth);
        mBluetoothMainAdapter = new BluetoothMainAdapter(this);
        mListsbluetooth.setAdapter(mBluetoothMainAdapter);
        bluelists_buletooth = (LinearLayout) findViewById(R.id.bluelists_buletooth);
        msg_bluetooth = (TextView) findViewById(R.id.msg_bluetooth);
        ImageView checkdel_bluetooth = (ImageView) findViewById(R.id.checkdel_bluetooth);
        checkdel_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.scanning_bluetooth) {
            //连接设备
            if (mPosition != -1) {
                BluetoothDevice mDevice = mBluetoothMainAdapter.getDevice(mPosition);
                if (mDevice == null)
                    return;
                if (GeneralUtils.isEmpty(mDevice.getName()))
                    SPUtils.put(this, "bluetoothName", "NO DEVICE");
                else
                    SPUtils.put(this, "bluetoothName", mDevice.getName());
                SPUtils.put(this, "bluetoothAddr", mDevice.getAddress());
                commitBluetooth(mDevice.getName(), mDevice.getAddress());
            } else {
                if (GeneralUtils.isEmpty(bluetoothAddr))
                    IBaseMethod.showToast(this, "请选择设备", IBase.RETAIL_TWO);
                else
                    commitBluetooth(mName, bluetoothAddr);
            }
        } else if (i == R.id.scanningble_bluetooth) {
            //判断是否有设备
            if (mBluetoothMainAdapter.lists.size() == 0) {
                bluelists_buletooth.setVisibility(View.GONE);
                msg_bluetooth.setVisibility(View.VISIBLE);
                msg_bluetooth.setText("正在搜索蓝牙设备...");
                mScanningbluetooth.setVisibility(View.GONE);
                mScanningBlebluetooth.setBackgroundResource(R.drawable.button_half5_plain_gray);
                IBaseMethod.jumpCountdown(60, handler);
            } else {
                bluelists_buletooth.setVisibility(View.VISIBLE);
                msg_bluetooth.setVisibility(View.GONE);
            }
            if (!isSm) {
                mScanningBlebluetooth.setText("停止扫描");
                mBluetoothMainAdapter.clear();
                isSm = true;
                scanningBle(true);
            } else {
                mScanningBlebluetooth.setText("重新搜索");
                isSm = false;
                scanningBle(false);
                if (mBluetoothMainAdapter.lists.size() == 0) {
                    msg_bluetooth.setText("请重新搜索蓝牙设备...");
                    mScanningBlebluetooth.setBackgroundResource(R.drawable.button_bottom5_yellow);
                } else {
                    mScanningBlebluetooth.setBackgroundResource(R.drawable.button_leftbottom5_yellow);
                }
            }

        }
    }

    /**
     * 连接Ble
     */
    private void commitBluetooth(String name, String addr) {
        Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, name);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, addr);
        intent.putExtra("taskCode", taskCode);
        intent.putExtra("dlrShortName", dlrShortName);
        startActivity(intent);
        finish();
    }


    /**
     * 扫描BLE
     */
    private void scanningBle(boolean isScanning) {
        if (isScanning) {
            // 开始扫描
            mBluetoothAdapter.startLeScan(leScanCallback);
        } else {
            //停止扫描
            mBluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            mBluetoothMainAdapter.addDevice(device);
            mBluetoothMainAdapter.notifyData();
            if (mBluetoothMainAdapter.lists.size() > 0) {
                bluelists_buletooth.setVisibility(View.VISIBLE);
                msg_bluetooth.setVisibility(View.GONE);
                mScanningBlebluetooth.setBackgroundResource(R.drawable.button_leftbottom5_yellow);
                mScanningbluetooth.setVisibility(View.VISIBLE);
            }
        }
    };


}
