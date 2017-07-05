package com.kaopujinfu.appsys.thecar.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;

/**
 * Created by 左丽姬 on 2017/5/8.
 */

public class BluetoothMainAdapter extends BaseAdapter {
    public ArrayList<BluetoothDevice> lists;
    private Context context;

    public BluetoothMainAdapter(Context context) {
        this.context = context;
        this.lists = new ArrayList<>();
    }

    public void addDevice(BluetoothDevice device) {
        if (!lists.contains(device))
            lists.add(device);
    }

    public void clear() {
        lists.clear();
    }

    public boolean isExit(BluetoothDevice mDevice) {
        boolean flag = false;
        for (BluetoothDevice mBd : lists) {
            if (mBd.getAddress().equals(mDevice.getAddress())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public BluetoothDevice getDevice(int position) {
        return lists.get(position);
    }

    public void notifyData() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bluetooth, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_name.setVisibility(View.VISIBLE);
        TextView tv_addr = (TextView) convertView.findViewById(R.id.tv_addr);
        tv_addr.setVisibility(View.VISIBLE);
        ImageView iv_bluetooth = (ImageView) convertView.findViewById(R.id.iv_bluetooth);
        iv_bluetooth.setImageResource(R.drawable.ic_bluetooth_gray);
        String name = lists.get(position).getName();
        if (GeneralUtils.isEmpty(name))
            name = "NO DEVICE";
        tv_name.setText(name);
        tv_addr.setText(lists.get(position).getAddress());
        return convertView;
    }
}

