package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 申请或监管清单适配器
 * Created by Doris on 2017/5/12.
 */
public class ApplyOrSupervisesFromAdapter extends BaseAdapter {

    private Context mContext;
    private boolean isApplyFrom = true; // 是否是申请清单
    private String[] applyFromTitles = {"初审", "高审", "放款"};
    private String[] supervisesFromTitles = {"正常", "异常", "仓库"};

    public ApplyOrSupervisesFromAdapter(Context context, boolean isApplyFrom){
        mContext = context;
        this.isApplyFrom = isApplyFrom;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ApplyFromHold hold;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_applyfrom, null);
            hold = new ApplyFromHold(view);
            view.setTag(hold);
        } else {
            hold = (ApplyFromHold) view.getTag();
        }
        if (isApplyFrom){
            // 申请清单
        } else {
            // 监管清单
            hold.oneTitle.setText(supervisesFromTitles[0]);
            hold.twoTitle.setText(supervisesFromTitles[1]);
            hold.threeTitle.setText(supervisesFromTitles[2]);
            hold.twoNum.setTextColor(Color.RED);
            hold.threeNum.setTextColor(Color.BLACK);
            hold.oneNum.setText("52");
            hold.twoNum.setText("2");
            hold.threeNum.setText("2");
        }
        return view;
    }

    class ApplyFromHold{
        TextView distributor; // 经销商
        // 标题
        TextView oneTitle; //初审、正常
        TextView twoTitle; //高审、异常
        TextView threeTitle; //高审、仓库
        // 数量
        TextView oneNum; //初审、正常
        TextView twoNum; //高审、异常
        TextView threeNum; //高审、仓库

        public ApplyFromHold(View view){
            distributor = (TextView) view.findViewById(R.id.applyFromDistributor_item);
            oneTitle = (TextView) view.findViewById(R.id.oneTitle_item);
            twoTitle = (TextView) view.findViewById(R.id.twoTitle_item);
            threeTitle = (TextView) view.findViewById(R.id.threeTitle_item);
            oneNum = (TextView) view.findViewById(R.id.oneNum_item);
            twoNum = (TextView) view.findViewById(R.id.twoNum_item);
            threeNum = (TextView) view.findViewById(R.id.threeNum_item);
        }

    }
}
