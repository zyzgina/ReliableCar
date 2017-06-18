package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 申请清单详情适配器
 * Created by Doris on 2017/5/19.
 */
public class ApplyDetailsAdapter extends BaseAdapter {

    private Context mContext;

    public ApplyDetailsAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_apply_details, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        if (i % 3 == 0){
            holder.state.setText("初审中");
            holder.state.setBackgroundResource(R.drawable.button_gray_circular);
        } else if (i % 3 == 1){
            holder.state.setText("高审中");
            holder.state.setBackgroundResource(R.drawable.button_blue_circular);
        } else {
            holder.state.setText("已放款");
            holder.state.setBackgroundResource(R.drawable.button_green_circular);
        }
        return view;
    }

    class Holder{
        TextView name, state, vin, price;

        public Holder(View view){
            name = (TextView) view.findViewById(R.id.applyDetailsName_item);
            state = (TextView) view.findViewById(R.id.applyDetailsState_item);
            vin = (TextView) view.findViewById(R.id.applyDetailsVIN_item);
            price = (TextView) view.findViewById(R.id.applyDetailsPrice_item);
        }
    }
}
