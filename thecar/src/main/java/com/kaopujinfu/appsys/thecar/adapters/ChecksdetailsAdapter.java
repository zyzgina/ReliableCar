package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.ChecksDetailHodler;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 左丽姬 on 2017/5/15.
 */

public class ChecksdetailsAdapter extends BaseAdapter {
    private Context context;
    private List<TaskItemBean.TaskItemsEntity> itemsEntities;
    private FinalDb db;

    public ChecksdetailsAdapter(Context context) {
        this.context = context;
        this.itemsEntities = new ArrayList<>();
        this.db = FinalDb.create(context, IBase.BASE_DATE, true);
    }

    @Override
    public int getCount() {
        return itemsEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChecksDetailHodler hodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_checks_lists, null);
            hodler = new ChecksDetailHodler(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (ChecksDetailHodler) convertView.getTag();
        }
        hodler.mpk.setVisibility(View.GONE);
        hodler.mVincode.setVisibility(View.VISIBLE);

        TaskItemBean.TaskItemsEntity entity = itemsEntities.get(position);
        String strs = entity.getCarBrand();
        if (GeneralUtils.isEmpty(entity.getCarBrand()))
            strs = "未知品牌";
        hodler.mCompanyname.setText(strs);

        if (!GeneralUtils.isEmpty(entity.getVinNo()))
            hodler.mVincode.setText(entity.getVinNo());
        if (!GeneralUtils.isEmpty(entity.getCheckTime())) {
            hodler.mDate.setText("盘库时间 " + entity.getCheckTime());
            hodler.mDate.setVisibility(View.VISIBLE);
            if (entity.getCommit_status() == 2) {
                if (IBase.RFIDCODE.equals(entity.getCheckMethod())) {
                    hodler.mStatus.setVisibility(View.GONE);
                    hodler.mIcon.setVisibility(View.VISIBLE);
                    hodler.mIcon.setImageResource(R.drawable.check_icon_rfid);
                } else {
                    hodler.mStatus.setVisibility(View.GONE);
                    hodler.mIcon.setVisibility(View.VISIBLE);
                    hodler.mIcon.setImageResource(R.drawable.check_icon_vin);
                }
            } else {
                hodler.mStatus.setBackgroundResource(R.drawable.button_green_circular);
                hodler.mStatus.setText("已 盘");
                hodler.mIcon.setVisibility(View.GONE);
                hodler.mStatus.setVisibility(View.VISIBLE);
            }
        } else {
            hodler.mStatus.setBackgroundResource(R.drawable.button_gray_circular);
            hodler.mStatus.setText("未 盘");
            hodler.mStatus.setVisibility(View.VISIBLE);
            hodler.mDate.setVisibility(View.GONE);
            hodler.mIcon.setVisibility(View.GONE);
        }
        return convertView;
    }


    /**
     * 添加数据
     */
    public void addItemList(List<TaskItemBean.TaskItemsEntity> itemsEntities) {
        this.itemsEntities.addAll(itemsEntities);
        notifyDataSetChanged();
        saveDate();
    }

    /**
     * 数据发生改变
     */
    public void updateItemList(List<TaskItemBean.TaskItemsEntity> itemsEntities) {
        this.itemsEntities.addAll(itemsEntities);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearItemList() {
        this.itemsEntities.clear();
    }

    /**
     * 获取单条数据
     */
    public TaskItemBean.TaskItemsEntity getTaskItemsEntity(int position) {
        return itemsEntities.get(position);
    }

    /**
     * 获取所有数据
     * */
    public List<TaskItemBean.TaskItemsEntity> getList(){
        return itemsEntities;
    }

    /**
     * 保存数据到本地
     */
    private void saveDate() {
        for (TaskItemBean.TaskItemsEntity entity : itemsEntities) {
            //判断数据库是否存在改数据
            List<TaskItemBean.TaskItemsEntity> lists = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "itemCode=\"" + entity.getItemCode() + "\"");
            if (lists == null || lists.size() == 0) {
                db.save(entity);
            }
        }
        List<TaskItemBean.TaskItemsEntity> lists = db.findAll(TaskItemBean.TaskItemsEntity.class);
//        for(TaskItemBean.TaskItemsEntity entity:lists){
//            LogUtils.debug("查询保存的数据:"+entity.toString());
//        }
    }
}
