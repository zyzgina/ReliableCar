package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.SelfStatistics;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.StatisticsBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.kaopujinfu.appsys.thecar.R.id.myselMsg;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */

public class StatisActivity extends Activity {
//    private MyGridView mMessage;
//    private MyselfMsgAadapter msgAadapter;
    private SelfStatistics dMyselMsg, dVinmyselMsg;
    private TextView dNormalSpot, dAbnormalSpot, dNormalText, dAbnormalText, dOtherSpot, dOtherText;
    private SelfStatistics sMyselMsg, sVinmyselMsg;
    private TextView sNormalSpot, sAbnormalSpot, sNormalText, sAbnormalText, sOtherSpot, sOtherText;
    private SelfStatistics cMyselMsg, cVinmyselMsg;
    private TextView cNormalSpot, cAbnormalSpot, cNormalText, cAbnormalText, cOtherSpot, cOtherText;
    private String[] dColorRes = {"#99CC00", "#F65355", "#D3D3D3"}, sColorRes = {"#99CC00", "#FFBB34", "#D3D3D3"}, cColorRes = {"#99CC00", "#6392C8", "#F52E2E"};//cColorRes1 = { "#F52E2E"};
    private float[] dDatas = {8, 2, 10}, sDatas = {4, 5, 10}, cDatas = {0, 0, 10};//cDatas1 = {0, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_statis);
        initStatis();
    }

    private void initStatis() {
//        mMessage = (MyGridView) findViewById(R.id.message_myself);
//        msgAadapter = new MyselfMsgAadapter(this);
//        mMessage.setAdapter(msgAadapter);

        View docmentStatis = findViewById(R.id.docmentStatis);
        View supStatis = findViewById(R.id.supStatis);
        View checkStatis = findViewById(R.id.checkStatis);


        dMyselMsg = (SelfStatistics) docmentStatis.findViewById(myselMsg);
        dVinmyselMsg = (SelfStatistics) docmentStatis.findViewById(R.id.vinmyselMsg);
        dNormalSpot = (TextView) docmentStatis.findViewById(R.id.normalSpot);
        dAbnormalSpot = (TextView) docmentStatis.findViewById(R.id.abnormalSpot);
        dNormalText = (TextView) docmentStatis.findViewById(R.id.normalText);
        dAbnormalText = (TextView) docmentStatis.findViewById(R.id.abnormalText);
        dOtherSpot = (TextView) docmentStatis.findViewById(R.id.otherSpot);
        dOtherText = (TextView) docmentStatis.findViewById(R.id.otherText);

        dMyselMsg.setPaintText("车辆监管");
        dMyselMsg.setColorRes(dColorRes);
        dAbnormalSpot.setTextColor(getResources().getColor(R.color.check_error));
        dOtherSpot.setTextColor(Color.parseColor(dColorRes[2]));

        sMyselMsg = (SelfStatistics) supStatis.findViewById(myselMsg);
        sVinmyselMsg = (SelfStatistics) supStatis.findViewById(R.id.vinmyselMsg);
        sNormalSpot = (TextView) supStatis.findViewById(R.id.normalSpot);
        sAbnormalSpot = (TextView) supStatis.findViewById(R.id.abnormalSpot);
        sNormalText = (TextView) supStatis.findViewById(R.id.normalText);
        sAbnormalText = (TextView) supStatis.findViewById(R.id.abnormalText);
        sOtherSpot = (TextView) supStatis.findViewById(R.id.otherSpot);
        sOtherText = (TextView) supStatis.findViewById(R.id.otherText);

        sMyselMsg.setPaintText("文档监管");
        sAbnormalSpot.setTextColor(getResources().getColor(R.color.yellow));
        sMyselMsg.setColorRes(sColorRes);
        sOtherSpot.setTextColor(Color.parseColor(dColorRes[2]));

        cMyselMsg = (SelfStatistics) checkStatis.findViewById(myselMsg);
        cVinmyselMsg = (SelfStatistics) checkStatis.findViewById(R.id.vinmyselMsg);
        cNormalSpot = (TextView) checkStatis.findViewById(R.id.normalSpot);
        cAbnormalSpot = (TextView) checkStatis.findViewById(R.id.abnormalSpot);
        cAbnormalSpot.setTextColor(Color.parseColor(cColorRes[1]));
        cNormalText = (TextView) checkStatis.findViewById(R.id.normalText);
        cAbnormalText = (TextView) checkStatis.findViewById(R.id.abnormalText);
        cOtherSpot = (TextView) checkStatis.findViewById(R.id.otherSpot);
        cOtherText = (TextView) checkStatis.findViewById(R.id.otherText);
        cMyselMsg.setPaintText("人工盘库");
//        cVinmyselMsg.setColorRes(cColorRes1);
        dMyselMsg.startDraw();
        sMyselMsg.startDraw();
        cMyselMsg.startDraw();
//        cVinmyselMsg.startDraw();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(Object jumpEventBus) {
        if (jumpEventBus instanceof StatisticsBean) {
//            msgAadapter.setLists((StatisticsBean) jumpEventBus);
            StatisticsBean statisticsBean = (StatisticsBean) jumpEventBus;
            dDatas[0] = statisticsBean.getRfNormal();
            dDatas[1] = statisticsBean.getRfAlert();
            dDatas[2] = statisticsBean.getCarTotal() - statisticsBean.getRfAlert() - statisticsBean.getRfNormal();

            sDatas[0] = statisticsBean.getDocCount();
            sDatas[1] = statisticsBean.getDocRelease();
            sDatas[2] = statisticsBean.getCarTotal() - statisticsBean.getDocCount() - statisticsBean.getDocRelease();

            cDatas[0] = statisticsBean.getRfidScan();
            cDatas[1] = statisticsBean.getVinScan();
            cDatas[2] = statisticsBean.getCarCount() - statisticsBean.getRfidScan() - statisticsBean.getVinScan();
            LogUtils.debug("===="+cDatas.toString());
            if(statisticsBean.getCarCount()==0){
                cColorRes[2]="#D3D3D3";
            }else{
                cColorRes[2]="#F52E2E";
            }
//            cDatas1[0] = statisticsBean.getRfidScan();
//            cDatas1[1] = statisticsBean.getCarTotal() - statisticsBean.getRfidScan();
            setDate();
        }
    }

    private void setDate() {
        dNormalText.setText("正常: " + (int) dDatas[0]);
        dAbnormalText.setText("异常: " + (int) dDatas[1]);
        dOtherText.setText("未监管: " + (int) dDatas[2]);
        dMyselMsg.setDatas(dDatas);
        dMyselMsg.startDraw();

        sNormalText.setText("监管中: " + (int) sDatas[0]);
        sAbnormalText.setText("释放待取: " + (int) sDatas[1]);
        sOtherText.setText("未监管: " + (int) sDatas[2]);
        sMyselMsg.setDatas(sDatas);
        sMyselMsg.startDraw();


        cNormalText.setText("RFID: " + (int) cDatas[0]);
        cAbnormalText.setText("VIN: " + (int) cDatas[1]);
        cOtherText.setText("待完成: " + (int) cDatas[2]);
        cMyselMsg.setDatas(cDatas);
        cMyselMsg.setColorRes(cColorRes);
        cMyselMsg.startDraw();
//        cVinmyselMsg.setDatas(cDatas1);
//        cVinmyselMsg.startDraw();
    }
}
