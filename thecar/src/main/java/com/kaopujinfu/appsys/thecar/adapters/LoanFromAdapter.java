package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.LoanFormBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款清单适配器
 * Created by Doris on 2017/5/12.
 */
public class LoanFromAdapter extends BaseAdapter {

    private Context mContext;
    private List<LoanFormBean.LoanItemsEntity> itemsEntities;

    public LoanFromAdapter(Context context) {
        mContext = context;
        this.itemsEntities = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return itemsEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ApplyFromHold hold;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_loanfrom, null);
            hold = new ApplyFromHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromHold) view.getTag();
        }
        LoanFormBean.LoanItemsEntity itemsEntity=itemsEntities.get(i);
        if(itemsEntity!=null){
            hold.distributor.setText(itemsEntity.getDlrName());
            hold.creditAmount.setText(itemsEntity.getCreditAmount());
            hold.creditAvailable.setText(itemsEntity.getLeftAmount());
        }
        return view;
    }

    class ApplyFromHold {
        TextView distributor; // 经销商
        TextView creditAmount; //授信额
        TextView creditAvailable; //可用额

        public ApplyFromHold(View view) {
            distributor = (TextView) view.findViewById(R.id.loanFromDistributor_item);
            creditAmount = (TextView) view.findViewById(R.id.loanFromCreditAmount_item);
            creditAvailable = (TextView) view.findViewById(R.id.loanFromCreditAvailable_item);
        }
    }

    public void setListDate(List<LoanFormBean.LoanItemsEntity> itemsEntities) {
        this.itemsEntities = itemsEntities;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.itemsEntities.clear();
        notifyDataSetChanged();
    }

    public LoanFormBean.LoanItemsEntity getLoanbean(int position){
        return itemsEntities.get(position);
    }
}
