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
import com.kaopujinfu.appsys.thecar.bean.ApplyBean;
import com.kaopujinfu.appsys.thecar.bean.SupervisersBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请或监管清单适配器
 * Created by Doris on 2017/5/12.
 */
public class ApplyOrSupervisesFromAdapter extends BaseAdapter {

    private Context mContext;
    private boolean isApplyFrom = true; // 是否是申请清单
    private String[] applyFromTitles = {"初审", "高审", "放款"};
    private String[] supervisesFromTitles = {"正常", "异常", "仓库"};
    private List<SupervisersBean.SupervisersItemsEntity> itemsEntities;
    private List<ApplyBean.ApplyItemsEntity> applyItemsEntities;

    public ApplyOrSupervisesFromAdapter(Context context, boolean isApplyFrom) {
        mContext = context;
        this.isApplyFrom = isApplyFrom;
        itemsEntities = new ArrayList<>();
        applyItemsEntities = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (isApplyFrom)
            return applyItemsEntities.size();
        else
            return itemsEntities.size();
    }

    @Override
    public Object getItem(int i) {
        if (isApplyFrom)
            return applyItemsEntities.get(i);
        else
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_applyfrom, null);
            hold = new ApplyFromHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromHold) view.getTag();
        }
        if (isApplyFrom) {
            // 申请清单
            ApplyBean.ApplyItemsEntity applyItemsEntity = applyItemsEntities.get(i);
            if (applyItemsEntity != null) {
                hold.oneTitle.setText(applyFromTitles[0]);
                hold.twoTitle.setText(applyFromTitles[1]);
                hold.threeTitle.setText(applyFromTitles[2]);
                hold.oneNum.setText(applyItemsEntity.getFirst() + "");
                hold.twoNum.setText(applyItemsEntity.getFinalX() + "");
                hold.threeNum.setText(applyItemsEntity.getPend() + "");
                hold.distributor.setText(applyItemsEntity.getDlrName());
            }
        } else {
            // 监管清单
            SupervisersBean.SupervisersItemsEntity itemsEntity = itemsEntities.get(i);
            hold.oneTitle.setText(supervisesFromTitles[0]);
            hold.twoTitle.setText(supervisesFromTitles[1]);
            hold.threeTitle.setText(supervisesFromTitles[2]);
            hold.twoNum.setTextColor(Color.RED);
            hold.threeNum.setTextColor(Color.BLACK);
            for (int s = 0; s < itemsEntity.getArrcount().size(); s++) {
                if ("正常监管".equals(itemsEntity.getArrcount().get(s).getStatus())) {
                    hold.oneNum.setText(itemsEntity.getArrcount().get(s).getCount());
                } else{
                    hold.twoNum.setText(itemsEntity.getArrcount().get(s).getCount());
                }
            }
            String wareHouse = itemsEntity.getWareHouseCount();
            if (GeneralUtils.isEmpty(wareHouse))
                wareHouse = "0";
            hold.threeNum.setText(wareHouse);
            hold.distributor.setText(itemsEntity.getShortName());
        }
        return view;
    }

    class ApplyFromHold {
        TextView distributor; // 经销商
        // 标题
        TextView oneTitle; //初审、正常
        TextView twoTitle; //高审、异常
        TextView threeTitle; //高审、仓库
        // 数量
        TextView oneNum; //初审、正常
        TextView twoNum; //高审、异常
        TextView threeNum; //高审、仓库

        public ApplyFromHold(View view) {
            distributor = (TextView) view.findViewById(R.id.applyFromDistributor_item);
            oneTitle = (TextView) view.findViewById(R.id.oneTitle_item);
            twoTitle = (TextView) view.findViewById(R.id.twoTitle_item);
            threeTitle = (TextView) view.findViewById(R.id.threeTitle_item);
            oneNum = (TextView) view.findViewById(R.id.oneNum_item);
            twoNum = (TextView) view.findViewById(R.id.twoNum_item);
            threeNum = (TextView) view.findViewById(R.id.threeNum_item);
        }
    }

    /* 监管清单 */
    public void setSpuerviserList(List<SupervisersBean.SupervisersItemsEntity> itemsEntities) {
        this.itemsEntities.addAll(itemsEntities);
        notifyDataSetChanged();
    }

    /* 清除监管清单 */
    public void clearSuperviser() {
        this.itemsEntities.clear();
        notifyDataSetChanged();
    }

    /* 获取清单单条数据 */
    public SupervisersBean.SupervisersItemsEntity getSuperviserItem(int position) {
        return itemsEntities.get(position);
    }

    /* 申请清单 */
    public void setApplyList(List<ApplyBean.ApplyItemsEntity> applyItemsEntities) {
        this.applyItemsEntities.addAll(applyItemsEntities);
        notifyDataSetChanged();
    }

    /* 清除申请清单 */
    public void clearApply() {
        this.applyItemsEntities.clear();
        notifyDataSetChanged();
    }

    /* 获取单条申请数据 */
    public ApplyBean.ApplyItemsEntity getApplyItems(int position) {
        return applyItemsEntities.get(position);
    }
}
