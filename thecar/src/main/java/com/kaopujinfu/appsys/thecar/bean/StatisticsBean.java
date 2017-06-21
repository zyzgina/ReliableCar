package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describe: 统计信息
 * Created Author: Gina
 * Created Date: 2017/6/21.
 */

public class StatisticsBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * carTotal : 8
     * rfAlert : 0
     * rfNormal : 0
     * docCount : 1
     * docRelease : 0
     * rfidScan : 0
     */

    private String result;
    private boolean success;
    private String comment;
    private int carTotal;
    private int rfAlert;
    private int rfNormal;
    private int docCount;
    private int docRelease;
    private int rfidScan;
    private int vinScan;
    private List<StatItemBean> itemBeen;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCarTotal(int carTotal) {
        this.carTotal = carTotal;
    }

    public void setRfAlert(int rfAlert) {
        this.rfAlert = rfAlert;
    }

    public void setRfNormal(int rfNormal) {
        this.rfNormal = rfNormal;
    }

    public void setDocCount(int docCount) {
        this.docCount = docCount;
    }

    public void setDocRelease(int docRelease) {
        this.docRelease = docRelease;
    }

    public void setRfidScan(int rfidScan) {
        this.rfidScan = rfidScan;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getComment() {
        return comment;
    }

    public int getCarTotal() {
        return carTotal;
    }

    public int getRfAlert() {
        return rfAlert;
    }

    public int getRfNormal() {
        return rfNormal;
    }

    public int getDocCount() {
        return docCount;
    }

    public int getDocRelease() {
        return docRelease;
    }

    public int getRfidScan() {
        return rfidScan;
    }

    public int getVinScan() {
        return vinScan;
    }

    public void setVinScan(int vinScan) {
        this.vinScan = vinScan;
    }

    public List<StatItemBean> getItemBeen() {
        return itemBeen;
    }

    public void setItemBeen(List<StatItemBean> itemBeen) {
        this.itemBeen = itemBeen;
    }

    public static class StatItemBean implements Serializable {
        private float datas[];
        private String colorRes[];

        public float[] getDatas() {
            return datas;
        }

        public void setDatas(float[] datas) {
            this.datas = datas;
        }

        public String[] getColorRes() {
            return colorRes;
        }

        public void setColorRes(String[] colorRes) {
            this.colorRes = colorRes;
        }

        @Override
        public String toString() {
            return "StatItemBean{" +
                    "datas=" + Arrays.toString(datas) +
                    ", colorRes=" + Arrays.toString(colorRes) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StatisticsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", carTotal=" + carTotal +
                ", rfAlert=" + rfAlert +
                ", rfNormal=" + rfNormal +
                ", docCount=" + docCount +
                ", docRelease=" + docRelease +
                ", rfidScan=" + rfidScan +
                ", vinScan=" + vinScan +
                ", itemBeen=" + itemBeen +
                '}';
    }

    public static StatisticsBean getStatisticsBean(String result) {
        StatisticsBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<StatisticsBean>() {
            }.getType());
            if (bean != null && bean.isSuccess()) {
                float datas[] = new float[3];
                String colorRes[] = new String[3];
                colorRes[0] = "#99CC00";
                colorRes[1] = "#F65355";
                colorRes[2] = "#D3D3D3";
                datas[0] = bean.getRfNormal();
                datas[1] = bean.getRfAlert();
                datas[2] = bean.getCarTotal() - bean.getRfAlert() - bean.getRfNormal();
//                datas[0] = 4;
//                datas[1] = 3;
//                datas[2] = 1;
                StatItemBean itemBean = new StatItemBean();
                StatItemBean itemBean1 = new StatItemBean();
                StatItemBean itemBean2 = new StatItemBean();
                itemBean.setColorRes(colorRes);
                itemBean2.setColorRes(colorRes);
                itemBean.setDatas(datas);
                bean.setItemBeen(new ArrayList<StatItemBean>());
                bean.getItemBeen().add(itemBean);
                float datas1[] = new float[3];
                String colorRes1[] = new String[3];
                colorRes1[0] = "#99CC00";
                colorRes1[1] = "#FFBB34";
                colorRes1[2] = "#D3D3D3";
                datas1[0] = bean.getDocCount();
                datas1[1] = bean.getDocRelease();
                datas1[2] = bean.getCarTotal() - bean.getDocCount() - bean.getDocRelease();
//                datas1[0] = 3;
//                datas1[1] = 2;
//                datas1[2] = 3;
                itemBean1.setColorRes(colorRes1);
                itemBean1.setDatas(datas1);
                bean.getItemBeen().add(itemBean1);
                float datas2[] = new float[3];
                datas2[0] = bean.getRfidScan();
                datas2[1] = bean.getVinScan();
                datas2[2] = bean.getCarTotal() - bean.getRfidScan() - bean.getVinScan();
//                datas2[0] = 1;
//                datas2[1] = 5;
//                datas2[2] = 2;
                itemBean2.setDatas(datas2);
                bean.getItemBeen().add(itemBean2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
