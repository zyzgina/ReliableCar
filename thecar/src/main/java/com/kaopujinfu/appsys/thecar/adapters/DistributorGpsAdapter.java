package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.DistributorGpsBean;
import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe: 经销商定位适配器
 * Created Author: Gina
 * Created Date: 2017/6/14.
 */

public class DistributorGpsAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> lists;
    private Map<String, List<DistributorGpsBean.GpsEntity>> maps;
    private ExpandableListView expandableListViewDistrbutor;

    public DistributorGpsAdapter(Context mContext, ExpandableListView expandableListViewDistrbutor) {
        this.mContext = mContext;
        this.expandableListViewDistrbutor = expandableListViewDistrbutor;
        lists = new ArrayList<>();
        maps = new HashMap<>();
        //去除箭头
        expandableListViewDistrbutor.setGroupIndicator(null);
        //设置点击不收缩
        expandableListViewDistrbutor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public int getGroupCount() {
        return lists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return maps.get(lists.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return maps.get(lists.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(mContext, R.layout.item_textview, null);
        LinearLayout textviewLinear = (LinearLayout) convertView.findViewById(R.id.textviewLinear);
        textviewLinear.setBackgroundColor(mContext.getResources().getColor(R.color.plain));
        TextView textview = (TextView) convertView.findViewById(R.id.textview);
        textview.setText(lists.get(groupPosition));
        textview.setTextColor(mContext.getResources().getColor(R.color.back_gray));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(mContext, R.layout.item_textview, null);
        LinearLayout textviewLinear = (LinearLayout) convertView.findViewById(R.id.textviewLinear);
        TextView textview = (TextView) convertView.findViewById(R.id.textview);
        textview.setPadding(mContext.getResources().getDimensionPixelOffset(R.dimen.common_measure_40sp), 0, 0, 0);
        DistributorGpsBean.GpsEntity gpsEntity = maps.get(lists.get(groupPosition)).get(childPosition);
        if ("近期使用".equals(lists.get(groupPosition)))
            textview.setText(gpsEntity.getName());
        else
            textview.setText("[" + gpsEntity.getDist() + "]  " + gpsEntity.getName());
        textview.setTextColor(mContext.getResources().getColor(R.color.car_theme));
        textviewLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childClickListener != null)
                    childClickListener.onChildClickListener(v, groupPosition, childPosition);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //设置展开列表
        for (int i = 0; i < getGroupCount(); i++) {
            expandableListViewDistrbutor.expandGroup(i);
        }
    }

    /* 添加数据 */
    public void addLists(String name, List<DistributorGpsBean.GpsEntity> gpsEntities) {
        lists.add(name);
        maps.put(name, gpsEntities);
    }

    /* 获取点击数据 */
    public DistributorGpsBean.GpsEntity getClikData(int groupPosition, int childPosition) {
        return maps.get(lists.get(groupPosition)).get(childPosition);
    }

    public interface ChildClickListener {
        void onChildClickListener(View view, int groupPosition, int childPosition);
    }

    private ChildClickListener childClickListener;

    public void setOnChildClickListener(ChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }
}
