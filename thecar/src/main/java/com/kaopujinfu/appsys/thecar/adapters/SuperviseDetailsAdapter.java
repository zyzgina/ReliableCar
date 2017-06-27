package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.SupervicerDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 监管清单详情适配器
 * Created by Doris on 2017/5/19.
 */
public class SuperviseDetailsAdapter extends BaseAdapter {

    private Context mContext;
    private List<SupervicerDetailsBean.SupDetailsItemsEntity> itemsEntities;

    public SuperviseDetailsAdapter(Context context) {
        this.mContext = context;
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
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_supervise_details, null);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        SupervicerDetailsBean.SupDetailsItemsEntity itemsEntity=itemsEntities.get(i);
        holder.name.setText(itemsEntity.getCarBrand());
        holder.vin.setText(itemsEntity.getVinNo());

        if (i % 3 == 0) {
            holder.docCheck.setBackgroundColor(Color.parseColor("#F65355"));
            holder.rfidCheck.setBackgroundColor(Color.parseColor("#99CC00"));
        } else {
            holder.carCheck.setBackgroundColor(Color.parseColor("#F65355"));
            holder.gpsCheck.setBackgroundColor(Color.parseColor("#F65355"));
            holder.flightCheck.setBackgroundColor(Color.parseColor("#99CC00"));
        }
        return view;
    }

    class Holder {
        TextView name, vin, vinCheck, rfidCheck, flightCheck, gpsCheck, carCheck, docCheck;

        public Holder(View view) {
            name = (TextView) view.findViewById(R.id.supervisesDetailsName_item);
            vin = (TextView) view.findViewById(R.id.supervisesDetailsVIN_item);
            vinCheck = (TextView) view.findViewById(R.id.supervisesDetailsVINCheck_item);
            rfidCheck = (TextView) view.findViewById(R.id.supervisesDetailsRFIDCheck_item);
            flightCheck = (TextView) view.findViewById(R.id.supervisesDetailsFlightCheck_item);
            gpsCheck = (TextView) view.findViewById(R.id.supervisesDetailsGPSCheck_item);
            carCheck = (TextView) view.findViewById(R.id.supervisesDetailsCarCheck_item);
            docCheck = (TextView) view.findViewById(R.id.supervisesDetailsDocCheck_item);
        }
    }

    public void setDetailsList(List<SupervicerDetailsBean.SupDetailsItemsEntity> itemsEntities) {
        this.itemsEntities = itemsEntities;
        notifyDataSetChanged();
    }

    public void clearDetails() {
        this.itemsEntities.clear();
        notifyDataSetChanged();
    }
}
