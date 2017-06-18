package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 贷款清单适配器
 * Created by Doris on 2017/5/12.
 */
public class LoanFromAdapter extends BaseAdapter {

    private Context mContext;

    public LoanFromAdapter(Context context){
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
        ApplyFromHold hold;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_loanfrom, null);
            hold = new ApplyFromHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromHold) view.getTag();
        }
        return view;
    }

    class ApplyFromHold{
        TextView distributor; // 经销商
        TextView creditAmount; //授信额
        TextView creditAvailable; //可用额

        public ApplyFromHold(View view){
            distributor = (TextView) view.findViewById(R.id.loanFromDistributor_item);
            creditAmount = (TextView) view.findViewById(R.id.loanFromCreditAmount_item);
            creditAvailable = (TextView) view.findViewById(R.id.loanFromCreditAvailable_item);
        }

    }
}
