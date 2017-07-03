package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.adpater.SpinnerListAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.ApplyDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请清单详情适配器
 * Created by Doris on 2017/5/19.
 */
public class ApplyDetailsAdapter extends BaseAdapter implements SpinnerListAdapter {

    private Context mContext;
    private List<ApplyDetailsBean.ApplyDetailsItemsEntity> itemsEntities;
    private List<ApplyDetailsBean.ApplyDetailsItemsEntity> tempLists;

    public ApplyDetailsAdapter(Context context) {
        mContext = context;
        itemsEntities = new ArrayList<>();
        tempLists = new ArrayList<>();
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
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_apply_details, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        ApplyDetailsBean.ApplyDetailsItemsEntity itemsEntity = itemsEntities.get(i);
        if (itemsEntity != null) {
            String strs = itemsEntity.getCarBrand();
            if (GeneralUtils.isEmpty(itemsEntity.getCarBrand()))
                strs = "未知品牌";
            holder.name.setText(strs);
            holder.vin.setText(itemsEntity.getVinNo());
            int price = 0;
            if (!GeneralUtils.isEmpty(itemsEntity.getLoanAmount()))
                price = Integer.parseInt(itemsEntity.getLoanAmount());
            holder.price.setText("￥ " + getPrice(price));
            if (itemsEntity.getStage().contains("办理")) {
                holder.state.setText("办理中");
                holder.state.setBackgroundResource(R.drawable.button_gray_circular);
            } else if (itemsEntity.getStage().contains("初审")) {
                holder.state.setText("初审中");
                holder.state.setBackgroundResource(R.drawable.button_gray_circular);
            } else if (itemsEntity.getStage().contains("终审")) {
                holder.state.setText("高审中");
                holder.state.setBackgroundResource(R.drawable.button_blue_circular);
            } else if (itemsEntity.getStage().contains("待放款")) {
                holder.state.setText("待放款");
                holder.state.setBackgroundResource(R.drawable.button_blue_circular);
            } else {
                holder.state.setText("已放款");
                holder.state.setBackgroundResource(R.drawable.button_green_circular);
            }
        }
        return view;
    }

    @Override
    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (ApplyDetailsBean.ApplyDetailsItemsEntity item : tempLists) {
            titles.add(item.getStage());
        }
        return titles;
    }

    @Override
    public void setTitles(List<String> titles) {
        if (titles == null) {
            itemsEntities = tempLists;
        } else {
            List<ApplyDetailsBean.ApplyDetailsItemsEntity> itemsBean = new ArrayList<>();
            for (String item : titles) {
                for (ApplyDetailsBean.ApplyDetailsItemsEntity itemBean : tempLists) {
                    if (item.equals(itemBean.getStage()))
                        itemsBean.add(itemBean);
                }
            }
            itemsEntities = itemsBean;
        }
        notifyDataSetChanged();
    }

    class Holder {
        TextView name, state, vin, price;

        public Holder(View view) {
            name = (TextView) view.findViewById(R.id.applyDetailsName_item);
            state = (TextView) view.findViewById(R.id.applyDetailsState_item);
            vin = (TextView) view.findViewById(R.id.applyDetailsVIN_item);
            price = (TextView) view.findViewById(R.id.applyDetailsPrice_item);
        }
    }

    public void addList(List<ApplyDetailsBean.ApplyDetailsItemsEntity> itemsEntities) {
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
