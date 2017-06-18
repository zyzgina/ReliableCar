package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.BusinessBean;

import java.util.List;

/**
 * 经销商spinner
 * Created by zuoliji on 2017/3/31.
 */
public class BusinessAdapter extends BaseAdapter {

    private Context context;
    private BusinessBean businessBean;
    private List<BusinessBean.ItemsBean> tempBusiness;

    public BusinessAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (businessBean != null) {
            return businessBean.getItems().size();
        }
        return 0;
    }

    @Override
    public BusinessBean.ItemsBean getItem(int i) {
        return businessBean.getItems().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        if (contentView == null)
            contentView = LayoutInflater.from(context).inflate(R.layout.item_textview, null);
        TextView tv_title = (TextView) contentView.findViewById(R.id.textview);
        tv_title.setText(getItem(i).getLongName());
        tv_title.setTextColor(context.getResources().getColor(R.color.car_theme));
        return contentView;
    }

    public void setBusinessBean(BusinessBean businessBean) {
        this.businessBean = businessBean;
        tempBusiness = this.businessBean.getItems();
        notifyDataSetChanged();
    }

    public BusinessBean.ItemsBean getBusinessBean(int position) {
        return tempBusiness.get(position);
    }
}
