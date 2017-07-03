package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

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
    private int carCount;

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

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
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
                ", carCount=" + carCount +
                '}';
    }

    public static StatisticsBean getStatisticsBean(String result) {
        StatisticsBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<StatisticsBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
