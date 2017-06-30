package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
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
        SupervicerDetailsBean.SupDetailsItemsEntity itemsEntity = itemsEntities.get(i);
        String strs = itemsEntity.getCarBrand();
        if (GeneralUtils.isEmpty(itemsEntity.getCarBrand()))
            strs = "未知品牌";
        holder.name.setText(strs);
        holder.vin.setText(itemsEntity.getVinNo());
        if (GeneralUtils.isEmpty(itemsEntity.getVinStatus()))
            itemsEntity.setVinStatus("0");
        int vinNo = Integer.parseInt(itemsEntity.getVinStatus());
        switch (vinNo) {
            case 11:
                holder.vinCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 12:
                holder.vinCheck.setBackgroundColor(Color.parseColor("#F65355"));
                break;
            default:
                holder.vinCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
        }
        if (GeneralUtils.isEmpty(itemsEntity.getRfidStatus()))
            itemsEntity.setRfidStatus("0");
        int rfid = Integer.parseInt(itemsEntity.getRfidStatus());
        switch (rfid) {
            case 21:
                holder.rfidCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 22:
                holder.rfidCheck.setBackgroundColor(Color.parseColor("#F65355"));
                break;
            default:
                holder.rfidCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
        }
        if (GeneralUtils.isEmpty(itemsEntity.getAppCheckStatus()))
            itemsEntity.setAppCheckStatus("0");
        int appch = Integer.parseInt(itemsEntity.getAppCheckStatus());
        switch (appch) {
            case 31:
                holder.flightCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 32:
                holder.flightCheck.setBackgroundColor(Color.parseColor("#F65355"));
                break;
            default:
                holder.flightCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
        }
        if (GeneralUtils.isEmpty(itemsEntity.getGpsStatus()))
            itemsEntity.setGpsStatus("0");
        int gpss = Integer.parseInt(itemsEntity.getGpsStatus());
        switch (gpss) {
            case 41:
                holder.gpsCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 42:
                holder.gpsCheck.setBackgroundColor(Color.parseColor("#F65355"));
                break;
            case 43:
                holder.gpsCheck.setBackgroundColor(Color.parseColor("#D05F4F"));
                break;
            case 44:
                holder.carCheck.setBackgroundColor(Color.parseColor("#FF8000"));
                break;
            default:
                holder.gpsCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
        }
        if (GeneralUtils.isEmpty(itemsEntity.getRfDevStatus()))
            itemsEntity.setRfDevStatus("0");
        int rfdev = Integer.parseInt(itemsEntity.getRfDevStatus());
        switch (rfdev) {
            case 51:
                holder.carCheck.setBackgroundColor(Color.parseColor("#FF8000"));
                break;
            case 52:
                holder.carCheck.setBackgroundColor(Color.parseColor("#F65355"));
                break;
            case 53:
                holder.carCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 54:
                holder.carCheck.setBackgroundColor(Color.parseColor("#FF8000"));
                break;
            default:
                holder.carCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
        }
        if (GeneralUtils.isEmpty(itemsEntity.getDocStatus()))
            itemsEntity.setDocStatus("0");
        int doc = Integer.parseInt(itemsEntity.getDocStatus());
        switch (doc) {
            case 61:
                holder.docCheck.setBackgroundColor(Color.parseColor("#99CC00"));
                break;
            case 62:
                holder.docCheck.setBackgroundColor(Color.parseColor("#516D92"));
                break;
            case 63:
                holder.docCheck.setBackgroundColor(Color.parseColor("#FFBB34"));
                break;
            default:
                holder.docCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                break;
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
        this.itemsEntities.addAll(itemsEntities);
        notifyDataSetChanged();
    }

    public void clearDetails() {
        this.itemsEntities.clear();
        notifyDataSetChanged();
    }
}
