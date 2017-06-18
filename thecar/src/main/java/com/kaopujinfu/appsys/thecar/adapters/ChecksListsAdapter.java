package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.ChecksDetailHodler;
import com.kaopujinfu.appsys.thecar.bean.TaskListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/15.
 */

public class ChecksListsAdapter extends BaseAdapter {
    private Context context;
    private List<TaskListBean.TaskListItem> items;

    public ChecksListsAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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

        TaskListBean.TaskListItem item = items.get(position);
        hodler.mCompanyname.setText(item.getDlrShortName());
        hodler.mNum.setText(item.getCheckCount() + "/" + item.getCarCount());
        hodler.mDate.setText("盘库时间:" + item.getCreationTime());

        if (GeneralUtils.isEmpty(item.getTaskState()))
            item.setTaskState("未开始");
        hodler.mStatus.setText(item.getTaskState());
        if ("已完成".equals(item.getTaskState())) {
            hodler.mStatus.setBackgroundResource(R.drawable.button_green_circular);
        } else if ("执行中".equals(item.getTaskState())) {
            hodler.mStatus.setBackgroundResource(R.drawable.button_yellow_circular);
            hodler.mDate.setVisibility(View.GONE);
        } else {
            hodler.mStatus.setBackgroundResource(R.drawable.button_gray_circular);
            hodler.mDate.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * 添加数据
     */
    public void addList(List<TaskListBean.TaskListItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 清除所有数据
     */
    public void clearList() {
        this.items.clear();
    }

    /**
     * 获取选中的数据
     */
    public TaskListBean.TaskListItem selectItem(int position) {
        return items.get(position);
    }

}
