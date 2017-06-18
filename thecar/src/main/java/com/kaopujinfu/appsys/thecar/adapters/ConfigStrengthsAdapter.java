package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆详情 配置亮点 适配器
 * Created by Doris on 2017/5/25.
 */
public class ConfigStrengthsAdapter extends BaseAdapter {

    private Context mContext;
    private List<TestInfo> infos = new ArrayList<>();

    public ConfigStrengthsAdapter(Context context) {
        mContext = context;
        init();
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public TestInfo getItem(int i) {
        return infos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ConfigStrengthsHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_config_strengths, null);
            holder = new ConfigStrengthsHolder(view);
            view.setTag(holder);
        } else {
            holder = (ConfigStrengthsHolder) view.getTag();
        }
        holder.image.setImageResource(getItem(i).image);
        holder.name.setText(getItem(i).name);
        return view;
    }

    class ConfigStrengthsHolder {

        ImageView image;
        TextView name;

        public ConfigStrengthsHolder(View view) {
            image = (ImageView) view.findViewById(R.id.configStrengthsImage_item);
            name = (TextView) view.findViewById(R.id.configStrengthsName_item);
        }
    }

    class TestInfo {
        int image;
        String name;

        public TestInfo(int image, String name) {
            this.image = image;
            this.name = name;
        }
    }

    private void init() {
        infos.add(new TestInfo(R.drawable.preview_icon_merit_1, "儿童座椅固定装置"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_2, "电动尾门"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_3, "Esp车身稳定"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_4, "定速巡航"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_5, "真皮座椅"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_6, "蓝牙车载电话"));
        infos.add(new TestInfo(R.drawable.preview_icon_merit_7, "倒车雷达"));
    }
}
