package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.adpater.SpinnerListAdapter;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.LoanDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款清单详情适配器
 * Created by Doris on 2017/5/12.
 */
public class LoanFromDetailsAdapter extends BaseAdapter implements SpinnerListAdapter {

    private Context mContext;
    private List<LoanDetailsBean.LoanDetailsItemsEntity> itemsEntities;
    private List<LoanDetailsBean.LoanDetailsItemsEntity> tempLists;

    public LoanFromDetailsAdapter(Context context) {
        mContext = context;
        itemsEntities = new ArrayList<>();
        tempLists = itemsEntities;
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
        ApplyFromDetailsHold hold;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_loanfrom_details, null);
            hold = new ApplyFromDetailsHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromDetailsHold) view.getTag();
        }
        LoanDetailsBean.LoanDetailsItemsEntity itemsEntity = itemsEntities.get(i);
        if (itemsEntity != null) {
            hold.distributor.setText(itemsEntity.getCarBrand());
            hold.vin.setText(itemsEntity.getVinNo());
            hold.date.setText(itemsEntity.getTotalDay() + "天");
            hold.time.setText("(" + itemsEntity.getDisbTime() + ")");
            double price = itemsEntity.getLoanAmoutR() + itemsEntity.getInterestR();
            String string = price + "";
            string = string.substring(string.lastIndexOf("."), string.length());
            hold.money.setText("￥" + getPrice(itemsEntity.getLoanAmoutR()) + "/" + "￥" + getPrice((int) price)+ string);
        }
        return view;
    }

    @Override
    public List<String> getTitles() {
        List<String> lists = new ArrayList<>();
        for (LoanDetailsBean.LoanDetailsItemsEntity itemsEntity : tempLists) {
            lists.add(itemsEntity.getStage());
        }
        return lists;
    }

    @Override
    public void setTitles(List<String> titles) {
        if (titles == null) {
            itemsEntities = tempLists;
        } else {
            List<LoanDetailsBean.LoanDetailsItemsEntity> itemsBean = new ArrayList<>();
            for (String item : titles) {
                for (LoanDetailsBean.LoanDetailsItemsEntity itemBean : tempLists) {
                    if (item.equals(itemBean.getStage()))
                        itemsBean.add(itemBean);
                }
            }
            itemsEntities = itemsBean;
        }
        notifyDataSetChanged();
    }

    class ApplyFromDetailsHold {
        TextView distributor, date, time, vin, money;

        public ApplyFromDetailsHold(View view) {
            distributor = (TextView) view.findViewById(R.id.loanFromDetailsDistributor_item);
            date = (TextView) view.findViewById(R.id.loanFromDetailsDate_item);
            time = (TextView) view.findViewById(R.id.loanFromDetailsTime_item);
            vin = (TextView) view.findViewById(R.id.loanFromDetailsVIN_item);
            money = (TextView) view.findViewById(R.id.loanFromMoney_item);
        }

    }

    public void setListDate(List<LoanDetailsBean.LoanDetailsItemsEntity> itemsEntities) {
        this.itemsEntities.addAll(itemsEntities);
        this.tempLists = this.itemsEntities;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.itemsEntities.clear();
        notifyDataSetChanged();
    }

    private String getPrice(int price) {
        String str = "";
        while (true) {
            if (price < 1000) {
                break;
            }
            if (price % 1000 != 0) {
                if (price % 1000 < 100)
                    str = "," + price % 1000 + "0" + str;
                else if (price % 1000 < 10)
                    str = "," + price % 1000 + "00" + str;
                else
                    str = "," + price % 1000 + str;
            } else
                str += ",000";
            price = price / 1000;
        }
        return price + str;
    }
}
