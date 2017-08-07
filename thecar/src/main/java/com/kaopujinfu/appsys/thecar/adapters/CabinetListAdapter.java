package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.CabinetListsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:柜子列表
 * Created Author: Gina
 * Created Date: 2017/7/18.
 */

public class CabinetListAdapter extends BaseAdapter {
    private Context context;
    private List<CabinetListsBean.CabinetEntity> entities;
    private CabinetHodler hodler = null;

    public CabinetListAdapter(Context context) {
        this.context = context;
        entities = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cabinet_lists, null);
            hodler = new CabinetHodler(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (CabinetHodler) convertView.getTag();
        }
        CabinetListsBean.CabinetEntity entity = entities.get(position);
        hodler.codeItemCabinet.setText(entity.getBoxCode());
        hodler.sizeItemCabinet.setText(entity.getCellCount());
        hodler.companyItemCabinet.setText(entity.getCompanyShortName());
        setValuesCab(entity);
        return convertView;
    }

    class CabinetHodler {
        LinearLayout contentlableItem, goItemCab, downItemCab;
        View viewCabItem, viewLineItemCabinet;
        TextView batteryItem, noiseItem, shockItem, openthedoorItem, humidityItem, networkItem, humanbodyItem, tiltItem,
                temperatureItem, powerSupplyItem, companyItemCabinet, sizeItemCabinet, codeItemCabinet;

        public CabinetHodler(View view) {
            contentlableItem = (LinearLayout) view.findViewById(R.id.contentlableItem);
            viewCabItem = view.findViewById(R.id.viewCabItem);
            viewLineItemCabinet = view.findViewById(R.id.viewLineItemCabinet);
            codeItemCabinet = (TextView) view.findViewById(R.id.codeItemCabinet);
            sizeItemCabinet = (TextView) view.findViewById(R.id.sizeItemCabinet);
            companyItemCabinet = (TextView) view.findViewById(R.id.companyItemCabinet);
            powerSupplyItem = (TextView) view.findViewById(R.id.powerSupplyItem);
            temperatureItem = (TextView) view.findViewById(R.id.temperatureItem);
            tiltItem = (TextView) view.findViewById(R.id.tiltItem);
            humanbodyItem = (TextView) view.findViewById(R.id.humanbodyItem);
            networkItem = (TextView) view.findViewById(R.id.networkItem);
            batteryItem = (TextView) view.findViewById(R.id.batteryItem);
            noiseItem = (TextView) view.findViewById(R.id.noiseItem);
            shockItem = (TextView) view.findViewById(R.id.shockItem);
            openthedoorItem = (TextView) view.findViewById(R.id.openthedoorItem);
            humidityItem = (TextView) view.findViewById(R.id.humidityItem);

            goItemCab = (LinearLayout) view.findViewById(R.id.goItemCab);
            downItemCab = (LinearLayout) view.findViewById(R.id.downItemCab);
        }
    }

    public void clearCabinetList() {
        this.entities.clear();
        notifyDataSetChanged();
    }

    public void setCabinetList(List<CabinetListsBean.CabinetEntity> entities) {
        this.entities.addAll(entities);
        notifyDataSetChanged();
    }

    private void setValuesCab(CabinetListsBean.CabinetEntity entity) {
        //电源
        if ("0".equals(entity.getAlert220v())) {
            hodler.powerSupplyItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.powerSupplyItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //电池
        if ("0".equals(entity.getAlertBat())) {
            hodler.batteryItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.batteryItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //温度
        if ("0".equals(entity.getAlertTemperature())) {
            hodler.temperatureItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.temperatureItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //人体
        if ("0".equals(entity.getAlertHuman())) {
            hodler.humanbodyItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.humanbodyItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //湿度
        if ("0".equals(entity.getAlertHumidity())) {
            hodler.humidityItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.humidityItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //噪音
        if ("0".equals(entity.getAlertNoise())) {
            hodler.noiseItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.noiseItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //震动、倾斜
        if ("0".equals(entity.getAlertSpeed())) {
            hodler.tiltItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
            hodler.shockItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.tiltItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
            hodler.shockItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //开门
        if ("0".equals(entity.getAlertDoorOpen())) {
            hodler.openthedoorItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.openthedoorItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
        //网络
        if ("0".equals(entity.getAlertTimeout())) {
            hodler.networkItem.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
        } else {
            hodler.networkItem.setBackgroundColor(context.getResources().getColor(R.color.check_error));
        }
    }
}
