package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.view.SelfStatistics;
import com.kaopujinfu.appsys.thecar.R;

/**
 * Created by 左丽姬 on 2017/5/12.
 */

public class MyselfMsgAadapter extends BaseAdapter {
    private Context context;

    public MyselfMsgAadapter(Context context) {
        this.context = context;
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

//        float datas[] = new float[]{4000,3000,7000,8000};
//        String colorRes[] = new String[]{"#fdb128", "#4a90e2", "#89c732", "#f46950"};
        float datas[] = new float[2];
        String colorRes[] = new String[2];
        if (position == 0) {
            hodler.myselMsg.setPaintText("车辆监管");
            hodler.normalText.setText("正常: 90");
            hodler.abnormalText.setText("异常: 7");
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.check_error));
            colorRes[0]="#99CC00";
            colorRes[1]="#F65355";
            datas[0]=30;
            datas[1]=7;
        } else if (position == 1) {
            hodler.myselMsg.setPaintText("文档监管");
            hodler.normalText.setText("监管中: 40");
            hodler.abnormalText.setText("释放待取: 0");
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.yellow));
            colorRes[0]="#99CC00";
            colorRes[1]="#FFBB34";
            datas[0]=40;
            datas[1]=0;
        } else {
            hodler.myselMsg.setPaintText("人工盘库");
            hodler.normalText.setText("正常: 20");
            hodler.abnormalText.setText("异常: 1");
            hodler.abnormalSpot.setTextColor(context.getResources().getColor(R.color.check_error));
            colorRes[0]="#99CC00";
            colorRes[1]="#F65355";
            datas[0]=20;
            datas[1]=1;
        }
        hodler.myselMsg.setDatas(datas);
        hodler.myselMsg.setColorRes(colorRes);
        hodler.myselMsg.startDraw();
        return convertView;
    }

    class MyMsgHodler {
        SelfStatistics myselMsg;
        TextView normalSpot,abnormalSpot,abnormalText,normalText;

        public MyMsgHodler(View view) {
            myselMsg = (SelfStatistics) view.findViewById(R.id.myselMsg);
            normalSpot = (TextView) view.findViewById(R.id.normalSpot);
//            TextPaint tp = normalSpot.getPaint();
//            tp.setFakeBoldText(true);
            abnormalSpot = (TextView) view.findViewById(R.id.abnormalSpot);
//            TextPaint tprt = abnormalSpot.getPaint();
//            tprt.setFakeBoldText(true);
            normalText = (TextView) view.findViewById(R.id.normalText);
            abnormalText = (TextView) view.findViewById(R.id.abnormalText);

        }
    }
}
