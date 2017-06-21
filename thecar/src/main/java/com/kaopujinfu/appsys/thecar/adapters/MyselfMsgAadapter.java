package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.view.SelfStatistics;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.StatisticsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/12.
 */

public class MyselfMsgAadapter extends BaseAdapter {
    private Context context;
    private List<StatisticsBean.StatItemBean> beens;

    public MyselfMsgAadapter(Context context) {
        this.context = context;
        beens = new ArrayList<>();
        beens.add(new StatisticsBean.StatItemBean());
        beens.add(new StatisticsBean.StatItemBean());
        beens.add(new StatisticsBean.StatItemBean());
    }

    @Override
    public int getCount() {
        return beens.size();
    }

    @Override
    public Object getItem(int position) {
        return beens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyMsgHodler hodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myself_message, null);
            hodler = new MyMsgHodler(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (MyMsgHodler) convertView.getTag();
        }
        StatisticsBean.StatItemBean itemBean = beens.get(position);
        String[] colorRes = itemBean.getColorRes();
        if (colorRes == null) {
            colorRes = new String[]{"#99CC00", "#F65355", "#D3D3D3"};
        }
        float[] datas = itemBean.getDatas();
        if (datas == null) {
            datas = new float[]{0, 0, 100};
        }
        if (position == 0) {
            hodler.myselMsg.setPaintText("车辆监管");
            hodler.normalText.setText("正常: " + (int) datas[0]);
            hodler.abnormalText.setText("异常: " + (int) datas[1]);
            hodler.otherText.setText("未监管: " + (int) datas[2]);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.check_error));
        } else if (position == 1) {
            hodler.myselMsg.setPaintText("文档监管");
            hodler.normalText.setText("监管中: " + (int) datas[0]);
            hodler.abnormalText.setText("释放待取: " + (int) datas[1]);
            hodler.otherText.setText("未监管: " + (int) datas[2]);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.yellow));
            if (colorRes == null) {
                colorRes = new String[]{"#99CC00", "#FFBB34", "#D3D3D3"};
            }
        } else {
            hodler.myselMsg.setPaintText("人工盘库");
            hodler.normalText.setText("RFID: " + (int) datas[0]);
            hodler.abnormalText.setText("VIN: " + (int) datas[1]);
            hodler.otherText.setText("未盘库: " + (int) datas[2]);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.check_error));
        }
        hodler.otherSpot.setTextColor(context.getResources().getColor(R.color.plain_gray));
        if (itemBean.getDatas() == null) {
            hodler.otherText.setText("未添加车辆");
        }
        hodler.myselMsg.setDatas(datas);
        hodler.myselMsg.setColorRes(colorRes);
        hodler.myselMsg.startDraw();
        return convertView;
    }

    class MyMsgHodler {
        SelfStatistics myselMsg;
        TextView normalSpot, abnormalSpot, otherSpot, abnormalText, normalText, otherText;

        public MyMsgHodler(View view) {
            myselMsg = (SelfStatistics) view.findViewById(R.id.myselMsg);
            normalSpot = (TextView) view.findViewById(R.id.normalSpot);
//            TextPaint tp = normalSpot.getPaint();
//            tp.setFakeBoldText(true);
            abnormalSpot = (TextView) view.findViewById(R.id.abnormalSpot);
            normalText = (TextView) view.findViewById(R.id.normalText);
            abnormalText = (TextView) view.findViewById(R.id.abnormalText);
            otherSpot = (TextView) view.findViewById(R.id.otherSpot);
            otherText = (TextView) view.findViewById(R.id.otherText);

        }
    }

    public void setLists(List<StatisticsBean.StatItemBean> beens) {
        this.beens.clear();
        this.beens.addAll(beens);
        notifyDataSetChanged();
    }
}
