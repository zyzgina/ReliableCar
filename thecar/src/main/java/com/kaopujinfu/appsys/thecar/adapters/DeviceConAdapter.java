package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/9.
 */

public class DeviceConAdapter extends BaseAdapter {
    private List<String> lists;
    private Context context;

    public DeviceConAdapter(Context context) {
        this.context = context;
        lists = new ArrayList<>();
    }

    public void addObj(String string) {
        lists.add(string);
    }

    public void lastObj(String string) {
        lists.remove(lists.size()-1);
        lists.add(string);
    }
    public void isSize(){
        if(lists.size()>=10){
           this.clear();
        }
    }

    public void addObjAll(List<String> strs) {
        lists.addAll(strs);
    }

    public void clear() {
        lists.clear();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_devicecontrol, null);
        TextView mMsg = (TextView) convertView.findViewById(R.id.msg_item_device);
        mMsg.setText(lists.get(position));
        return convertView;
    }
}
