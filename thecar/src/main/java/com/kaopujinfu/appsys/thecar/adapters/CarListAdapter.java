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
import com.kaopujinfu.appsys.thecar.bean.CarListBean;
import com.kaopujinfu.appsys.thecar.bean.DocumentHold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆清单列表
 * Created by 左丽姬 on 2017/5/17.
 */

public class CarListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ExpandableListView mListView;
    private List<String> dates = new ArrayList<>();
    private Map<String, List<CarListBean.ItemsCarBean>> list = new HashMap<>();
    private List<CarListBean.ItemsCarBean> deletes = new ArrayList<>();
    private UpdateBindingUIListener mListener;
    private boolean isSelected; // 选择

    public CarListAdapter(Context context, ExpandableListView listView) {
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
    public List<CarListBean.ItemsCarBean> getGroup(int i) {
        return list.get(dates.get(i));
    }

    @Override
    public CarListBean.ItemsCarBean getChild(int i, int i1) {
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
        final CarListBean.ItemsCarBean item = getChild(i, i1);
        hold.distributor.setText(item.getDlrLongName());
        hold.number.setText(item.getStartTime());
        hold.vin.setText(item.getVinNo());
        hold.state.setText(item.getCarBrand() + "/" + item.getCarSubBrand());
        hold.state.setTextColor(mContext.getResources().getColor(R.color.back_gray));
        hold.state.setPadding(2, 0, 0, 0);
        hold.state.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        // 最后一行把线隐藏
        if (i1 == getChildrenCount(i) - 1) {
            hold.line.setVisibility(View.GONE);
        } else {
            hold.line.setVisibility(View.VISIBLE);
        }
        if (isSelected) {
            hold.documentDetailsLayout_item.setVisibility(View.VISIBLE);
            if (deletes.contains(item))
                hold.item.setImageResource(R.drawable.btn_selected);
            else
                hold.item.setImageResource(R.drawable.btn_notselected);
            hold.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletes.contains(item)) {
                        deletes.remove(item);
                        ((ImageView) v).setImageResource(R.drawable.btn_notselected);
                    } else {
                        deletes.add(item);
                        ((ImageView) v).setImageResource(R.drawable.btn_selected);
                    }
                    if (mListener != null) {
                        mListener.updateUI(deletes.size());
                    }
                }
            });
        } else {
            hold.documentDetailsLayout_item.setVisibility(View.GONE);
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
        for (int i = 0; i < getGroupCount(); i++) {
            mListView.expandGroup(i);
        }
    }

    /**
     * 设置数据
     *
     * @param listBean
     */
    public void setListBean(CarListBean listBean) {
        // 按时间排序：降序
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                CarListBean.ItemsCarBean b1 = (CarListBean.ItemsCarBean) o1;
                CarListBean.ItemsCarBean b2 = (CarListBean.ItemsCarBean) o2;
                return b2.getStartTime().compareTo(b1.getStartTime());
            }
        };
        Collections.sort(listBean.getItems(), comp);
        for (CarListBean.ItemsCarBean item : listBean.getItems()) {
            String date = DateUtil.getSimpleDateYYYYMMDD(DateUtil.stringToDate(item.getStartTime()).getTime());
            if (dates.contains(date)) {
                list.get(date).add(item);
            } else {
                dates.add(date);
                List<CarListBean.ItemsCarBean> items = new ArrayList<>();
                items.add(item);
                list.put(date, items);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 选择删除
     *
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
    public void delete() {
        for (String key : dates) {
            list.get(key).removeAll(deletes);
            if (list.get(key).size() == 0) {
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
