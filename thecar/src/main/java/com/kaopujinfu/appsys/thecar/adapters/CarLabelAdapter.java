package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 车辆详情 评价标签
 * Created by Doris on 2017/5/25.
 */
public class CarLabelAdapter extends BaseAdapter {

    private Context mContext;
    private String[] tag = {"动力真心不错  5", "坐骑包裹性非常好  5", "乘坐空间满意  8", "控制很好  4"};

    public CarLabelAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return tag.length;
    }

    @Override
    public String getItem(int i) {
        return tag[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView label;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_car_label, null);
            label = (TextView) view.findViewById(R.id.carLabel_item);
            view.setTag(label);
        } else {
            label = (TextView) view.getTag();
        }
        label.setText(getItem(i));
        return view;
    }
}
