package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 车辆详情 参数配置 适配器
 * Created by Doris on 2017/5/24.
 */
public class CarParameterConfigAdapter extends BaseAdapter {

    private Context mContext;
    private String[][] info = {{"13年1个月", "2004年4月上牌"}, {"14.00万", "行驶里程"},
            {"北京", "上牌地区"}, {"国1", "排放标准"}, {"自动", "变速箱"}, {"0次", "过户次数"}};

    public CarParameterConfigAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return info.length;
    }

    @Override
    public String[] getItem(int i) {
        return info[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CarParameterConfigHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_parameter_config, null);
            holder = new CarParameterConfigHolder(view);
            view.setTag(holder);
        } else {
            holder = (CarParameterConfigHolder) view.getTag();
        }
        holder.info.setText(getItem(i)[0]);
        holder.explain.setText(getItem(i)[1]);
        return view;
    }


    class CarParameterConfigHolder  {

        TextView info, explain;

        public CarParameterConfigHolder(View itemView) {
            info = (TextView) itemView.findViewById(R.id.parameterConfigInfo_item);
            explain = (TextView) itemView.findViewById(R.id.parameterConfigExplain_item);
        }
    }
}