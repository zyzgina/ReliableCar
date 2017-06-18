package com.kaopujinfu.appsys.thecar.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by 左丽姬 on 2017/5/9.
 */

public class BluetoothLeService extends Service {

    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothGattCharacteristic mNotifyCharacteristic;

    public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String ACTION_BLUE_DISCONNECTED = "com.example.bluetooth.le.ACTION_BLUE_DISCONNECTED";
    public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";

    public final static UUID UUID_NOTIFY = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");

    private DeviceBind mBind = new DeviceBind();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    /**
     * IBinder
     */
    public class DeviceBind extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    /**
     * 销毁
     */
    private void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * 赋值
     */
    public void WriteValue(String strValue) {
        LogUtils.debug("strValue==="+strValue);
        mNotifyCharacteristic.setValue(strValue);
        mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);
    }

    /**
     * findService
     */
    public void findService(List<BluetoothGattService> gattServices) {
        for (BluetoothGattService mGatt : gattServices) {
            if (mGatt.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString())) {
                List<BluetoothGattCharacteristic> mCharacteristics = mGatt.getCharacteristics();
                LogUtils.debug("BluetoothGattCharacteristic size : " + mCharacteristics.size());
                for (BluetoothGattCharacteristic mCharacteristic : mCharacteristics) {
                    if (mCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString())) {
                        LogUtils.debug("mCharacteristic===" + mCharacteristic.getUuid().toString());
                        LogUtils.debug("UUID_NOTIFY===" + UUID_NOTIFY.toString());
                        mNotifyCharacteristic = mCharacteristic;
                        setCharacteristicNotification(mCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                    }
                }
            }
        }
    }

    /**
     * Enables or disables notification on a give characteristic.
     * 启用或禁用给定特性的通知。
     *
     * @param mCharacteristic Characteristic to act on.
     * @param enabled         If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic mCharacteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogUtils.debug("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(mCharacteristic, enabled);
    }

    /**
     * 发送广播
     *
     * @param actiion 广播action
     */
    public void broadcastUpdate(String actiion) {
        Intent intent = new Intent(actiion);
        sendBroadcast(intent);
    }

    /**
     * 发送广播
     *
     * @param action         广播action
     * @param characteristic 传送的参数
     */
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            intent.putExtra(EXTRA_DATA, new String(data));
        }
        sendBroadcast(intent);
    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     * 初始化本地蓝牙适配器的引用。
     *
     * @return Return true if the initialization is successful. 如果初始化成功，返回true。
     */
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                LogUtils.debug("Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            LogUtils.debug("Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     * 连接到在蓝牙LE设备上托管的GATT服务器。
     *
     * @param address The device address of the destination device. 目的设备的设备地址
     * @return Return true if the connection is initiated successfully. (返回true如果连接的是启动成功。) The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(String address) {
        //判断mBluetoothAdapter address
        if (mBluetoothAdapter == null || GeneralUtils.isEmpty(address)) {
            return false;
        }

        BluetoothDevice mDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (mDevice == null)
            return false;
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        mBluetoothGatt = mDevice.connectGatt(this, false, mGattCallback);
        return true;
    }

    /**
     * 为应用程序关心的关贸总协定事件实现回调方法。
     * 例如,改变连接和服务发现
     */
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            String intentAction;
            LogUtils.debug("oldStatus=" + status + " NewStates=" + newState);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    intentAction = ACTION_GATT_CONNECTED;
                    broadcastUpdate(intentAction);
                    LogUtils.debug("Connected to GATT server.");
                    // Attempts to discover services after successful connection.
                    LogUtils.debug("Attempting to start service discovery:" +
                            mBluetoothGatt.discoverServices());
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    intentAction = ACTION_GATT_DISCONNECTED;
                    mBluetoothGatt.close();
                    mBluetoothGatt = null;
                    LogUtils.debug("Disconnected from GATT server.");
                    broadcastUpdate(intentAction);
                }
            }else{
                if (newState == BluetoothProfile.STATE_DISCONNECTED){
                    intentAction=ACTION_BLUE_DISCONNECTED;
                    broadcastUpdate(intentAction);
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.debug("onServicesDiscovered received: " + status);
                findService(gatt.getServices());
            } else {
                if (mBluetoothGatt.getDevice().getUuids() == null)
                    LogUtils.debug("onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtils.debug("接受消息" + status + "====" + BluetoothGatt.GATT_SUCCESS);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.debug("成功=接受消息=发送广播");
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            LogUtils.debug("OnCharacteristicWrite");
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic,
                                          int status) {
            LogUtils.debug("OnCharacteristicWrite");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            LogUtils.debug("onDescriptorRead");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor bd, int status) {
            LogUtils.debug("onDescriptorWrite");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int a, int b) {
            LogUtils.debug("onReadRemoteRssi");
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int a) {
            LogUtils.debug("onReliableWriteCompleted");
        }
    };

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * 断开现有连接或取消挂起的连接。断开的结果
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogUtils.debug("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogUtils.debug("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * the connected device. This shoulRetrieves a list of supported GATT services ond be 连接设备。这shoulretrieves名单支持服务，二是关贸总协定
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }


}
