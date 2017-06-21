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

/**
 * Created by 左丽姬 on 2017/5/12.
 */

public class MyselfMsgAadapter extends BaseAdapter {
    private Context context;
    private StatisticsBean beens;

    public MyselfMsgAadapter(Context context) {
        this.context = context;
        beens = new StatisticsBean();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
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
        String[] colorRes;
        float[] datas = new float[3];
        hodler.vinmyselMsg.setVisibility(View.GONE);
        if (position == 0) {
            colorRes = new String[]{"#99CC00", "#F65355", "#D3D3D3"};
            datas[0] = beens.getRfNormal();
            datas[1] = beens.getRfAlert();
            datas[2] = beens.getCarTotal() - beens.getRfAlert() - beens.getRfNormal();
            hodler.myselMsg.setPaintText("车辆监管");
            hodler.normalText.setText("正常: " + (int) datas[0]);
            hodler.abnormalText.setText("异常: " + (int) datas[1]);
            hodler.otherText.setText("未监管: " + (int) datas[2]);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.check_error));
        } else if (position == 1) {
            colorRes = new String[]{"#99CC00", "#FFBB34", "#D3D3D3"};
            datas[0] = beens.getDocCount();
            datas[1] = beens.getDocRelease();
            datas[2] = beens.getCarTotal() - beens.getDocCount() - beens.getDocRelease();
            hodler.myselMsg.setPaintText("文档监管");
            hodler.normalText.setText("监管中: " + (int) datas[0]);
            hodler.abnormalText.setText("释放待取: " + (int) datas[1]);
            hodler.otherText.setText("未监管: " + (int) datas[2]);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.yellow));
        } else {
            colorRes = new String[]{"#6392C8", "#D3D3D3"};
            datas[0] = beens.getVinScan();
            datas[1] = beens.getCarTotal() - beens.getVinScan();
            hodler.myselMsg.setPaintText("");
            hodler.vinmyselMsg.setVisibility(View.VISIBLE);
            hodler.vinmyselMsg.setPaintText("盘库");
            hodler.normalText.setText("RFID: " + beens.getRfidScan());
            hodler.abnormalText.setText("VIN: " + (int) datas[0]);
            hodler.otherText.setVisibility(View.GONE);
            hodler.otherSpot.setVisibility(View.GONE);
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.blue));
            hodler.vinmyselMsg.setDatas(new float[]{beens.getRfidScan(), beens.getCarTotal() - beens.getRfidScan()});
            hodler.vinmyselMsg.setColorRes(new String[]{"#99CC00", "#D3D3D3"});
            hodler.vinmyselMsg.startDraw();
        }
        hodler.otherSpot.setTextColor(context.getResources().getColor(R.color.plain_gray));
        if (beens.getCarTotal() == 0) {
            hodler.otherText.setText("未添加车辆");
        }
        hodler.myselMsg.setDatas(datas);
        hodler.myselMsg.setColorRes(colorRes);
        hodler.myselMsg.startDraw();
        return convertView;
    }

    class MyMsgHodler {
        SelfStatistics myselMsg, vinmyselMsg;
        TextView normalSpot, abnormalSpot, otherSpot, abnormalText, normalText, otherText;

        public MyMsgHodler(View view) {
            myselMsg = (SelfStatistics) view.findViewById(R.id.myselMsg);
            vinmyselMsg = (SelfStatistics) view.findViewById(R.id.vinmyselMsg);
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

    public void setLists(StatisticsBean beens) {
        this.beens = beens;
        notifyDataSetChanged();
    }
}
