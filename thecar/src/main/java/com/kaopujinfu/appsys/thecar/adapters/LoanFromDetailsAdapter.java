package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 贷款清单详情适配器
 * Created by Doris on 2017/5/12.
 */
public class LoanFromDetailsAdapter extends BaseAdapter {

    private Context mContext;

    public LoanFromDetailsAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ApplyFromDetailsHold hold;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_loanfrom_details, null);
            hold = new ApplyFromDetailsHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromDetailsHold) view.getTag();
        }
        return view;
    }

    class ApplyFromDetailsHold {
        TextView distributor, date, time, vin, money;

        public ApplyFromDetailsHold(View view){
            distributor = (TextView) view.findViewById(R.id.loanFromDetailsDistributor_item);
            date = (TextView) view.findViewById(R.id.loanFromDetailsDate_item);
            time = (TextView) view.findViewById(R.id.loanFromDetailsTime_item);
            vin = (TextView) view.findViewById(R.id.loanFromDetailsVIN_item);
            money = (TextView) view.findViewById(R.id.loanFromMoney_item);
        }

    }
}
