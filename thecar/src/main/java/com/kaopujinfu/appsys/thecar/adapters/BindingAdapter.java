package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.BindingBean;
import com.kaopujinfu.appsys.thecar.bean.DocumentHold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监管器列表
 * Created by 左丽姬 on 2017/5/17.
 */

public class BindingAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ExpandableListView mListView;
    private List<String> dates = new ArrayList<>();
    private Map<String, List<BindingBean.BindingItem>> list = new HashMap<>();
    private List<BindingBean.BindingItem> deletes = new ArrayList<>();
    private UpdateBindingUIListener mListener;
    private boolean isSelected; // 选择

    public BindingAdapter(Context context, ExpandableListView listView) {
        this.mContext = context;
        this.mListView = listView;
        // 点击不收缩
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(dates.get(i)).size();
    }

    @Override
    public List<BindingBean.BindingItem> getGroup(int i) {
        return list.get(dates.get(i));
    }

    @Override
    public BindingBean.BindingItem getChild(int i, int i1) {
        return list.get(dates.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView time;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_grouplist_time, null);
            time = (TextView) view.findViewById(R.id.documentTime_item);
            view.setTag(time);
        } else {
            time = (TextView) view.getTag();
        }
        time.setText(dates.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        DocumentHold hold;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_grouplist, null);
            hold = new DocumentHold(view);
            view.setTag(hold);
        } else {
            hold = (DocumentHold) view.getTag();
        }
        final BindingBean.BindingItem item = getChild(i, i1);
        hold.distributor.setText(item.getDlrShortName());
        hold.number.setText("编号：" + item.getDevCode());
        hold.vin.setText(item.getInitVinNo());
        hold.state.setText(item.getStatus());
        if ("未监管".equals(item.getStatus())) {
            hold.state.setBackgroundResource(R.drawable.button_gray_circular);
        } else {
            hold.state.setBackgroundResource(R.drawable.button_green_circular);
        }
        // 最后一行把线隐藏
        if (i1 == getChildrenCount(i) - 1){
            hold.line.setVisibility(View.GONE);
        } else {
            hold.line.setVisibility(View.VISIBLE);
        }
        if (isSelected) {
            if (deletes.contains(item))
                hold.item.setImageResource(R.drawable.btn_selected);
            else
                hold.item.setImageResource(R.drawable.btn_notselected);
            hold.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletes.contains(item)) {
                        deletes.remove(item);
                        ((ImageView) v ).setImageResource(R.drawable.btn_notselected);
                    } else {
                        deletes.add(item);
                        ((ImageView) v ).setImageResource(R.drawable.btn_selected);
                    }
                    if (mListener != null) {
                        mListener.updateUI(deletes.size());
                    }
                }
            });
        } else {
            hold.item.setImageResource(R.drawable.easyicon);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (int i = 0; i < getGroupCount(); i ++){
            mListView.expandGroup(i);
        }
    }

    /**
     * 设置数据
     * @param listBean
     */
    public void setListBean(BindingBean listBean) {
        // 按时间排序：降序
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                BindingBean.BindingItem b1 = (BindingBean.BindingItem) o1;
                BindingBean.BindingItem b2 = (BindingBean.BindingItem) o2;
                return b2.getInitTime().compareTo(b1.getInitTime());
            }
        };
        Collections.sort(listBean.getItems(), comp);
        for (BindingBean.BindingItem item : listBean.getItems()) {
            String date = DateUtil.getSimpleDateYYYYMMDD(DateUtil.stringToDate(item.getInitTime()).getTime());
            if (dates.contains(date)) {
                list.get(date).add(item);
            } else {
                dates.add(date);
                List<BindingBean.BindingItem> items = new ArrayList<>();
                items.add(item);
                list.put(date, items);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 选择删除
     * @param flag
     */
    public void updateSelected(boolean flag) {
        this.isSelected = flag;
        deletes.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除
     */
    public void delete(){
        for (String key : dates){
            list.get(key).removeAll(deletes);
            if (list.get(key).size() == 0){
                list.remove(key);
                dates.remove(key);
            }
        }
        notifyDataSetChanged();
    }
    /**
     * 清空数据
     */
    public void clearDate() {
        for (String key : dates) {
            list.get(key).clear();
            if (list.get(key).size() == 0) {
                list.remove(key);
            }
        }
        dates.clear();
        notifyDataSetChanged();
    }

    /**
     * 修改前台UI监听
     *
     * @param listener
     */
    public void setUpdateBindingUIListener(UpdateBindingUIListener listener) {
        this.mListener = listener;
    }

    public interface UpdateBindingUIListener {
        void updateUI(int num);
    }
}
