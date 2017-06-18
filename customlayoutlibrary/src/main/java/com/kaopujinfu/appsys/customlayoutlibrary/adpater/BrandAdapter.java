package com.kaopujinfu.appsys.customlayoutlibrary.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.BrandBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/4/26.
 */

public class BrandAdapter extends BaseAdapter implements SpinnerListAdapter{

    private Context context;
    private List<BrandBean.BrandItems> lists;
    private List<BrandBean.BrandItems> tempLists;
    private int colorStatus = IBase.CONSTANT_ZERO;
    private int flag = 0; // 0品牌、1子品牌、2车型

    public BrandAdapter(Context context, List<BrandBean.BrandItems> lists) {
        this.context = context;
        this.lists = lists;
        this.tempLists = lists;
    }

    public void setColorStatus(int colorStatus) {
        this.colorStatus = colorStatus;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
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
        BrandBean.BrandItems brandItems = lists.get(i);
        if (GeneralUtils.isEmpty(brandItems.getSubBrand()) && GeneralUtils.isEmpty(brandItems.getModel())) {
            flag = 0;
            tv_title.setText(brandItems.getBrand());
        } else if (GeneralUtils.isEmpty(brandItems.getModel())) {
            flag = 1;
            tv_title.setText(brandItems.getSubBrand());
        } else {
            flag = 2;
            tv_title.setText(brandItems.getModel());
        }
        if (colorStatus == IBase.CONSTANT_ONE) {
            tv_title.setTextColor(context.getResources().getColor(R.color.gray));
        }
        return contentView;
    }

    @Override
    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        for (BrandBean.BrandItems item : tempLists){
            if (flag == 0){
                titles.add(item.getBrand());
            } else if (flag == 1){
                titles.add(item.getSubBrand());
            } else if (flag == 2){
                titles.add(item.getModel());
            }
        }
        return titles;
    }

    @Override
    public void setTitles(List<String> titles) {
        if (titles == null){
            lists = tempLists;
        } else {
            List<BrandBean.BrandItems> itemsBean = new ArrayList<>();
            for (String item : titles){
                for (BrandBean.BrandItems itemBean : tempLists){
                    if (flag == 0){
                        if (itemBean.getBrand() != null && itemBean.getBrand().equals(item)){
                            itemsBean.add(itemBean);
                        }
                    } else if (flag == 1){
                        if (itemBean.getSubBrand() != null && itemBean.getSubBrand().equals(item)){
                            itemsBean.add(itemBean);
                        }
                    } else if (flag == 2){
                        if (itemBean.getModel() != null && itemBean.getModel().equals(item)){
                            itemsBean.add(itemBean);
                        }
                    }
                }
            }
            lists = itemsBean;
        }
        notifyDataSetChanged();
    }
}
