package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/10/20.
 */

public class GPSBean implements Serializable {


    /**
     * result : 1
     * totalProperty : 1
     * count : 1
     * data : [{"id":1,"devId":"308371 ","lastMsgTime":"2017-10-20 15:06:32.0","sleepTime":5,"confSleepTime":5,"carVinNo":"LWVEA3043HB027354","carBrand":"指南者","carModel":"指南者 2020款/广汽菲克","posLat":30.57837352,"posLng":114.23389562,"posLatBaidu":30.58225365,"posLngBaidu":114.2457199,"posType":"CELL","posCity":"武汉市","posAddr":"湖北省武汉市硚口区解放大道666","gprsMode":"N","confGprsMode":"N","gpsMode":"N","confGpsMode":"N","fangchaiMode":"N","confFangchaiMode":"N","wifiMode":"Y","confWifiMode":"Y","fangchaiAlert":0,"fangchaiCountInit":0,"fangchaiCountLast":0,"restartCount":8}]
     */

    private int result;
    private int totalProperty;
    private int count;
    private List<GPSEntity> data;

    public static GPSBean getGPSBean(String string){
        GPSBean bean=null;
        try{
            bean=new Gson().fromJson(string,new TypeToken<GPSBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setTotalProperty(int totalProperty) {
        this.totalProperty = totalProperty;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setData(List<GPSEntity> data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public int getTotalProperty() {
        return totalProperty;
    }

    public int getCount() {
        return count;
    }

    public List<GPSEntity> getData() {
        return data;
    }

    public static class GPSEntity implements Serializable {

        /**
         * id : 2
         * devId : 308380
         * lastMsgTime : 2017-10-03 16:39:03.0
         * sleepTime : 360
         * confSleepTime : 360
         * carVinNo : LWVEA3023HB002775
         * carBrand : 指南者
         * carModel : 指南者 2019款/广汽菲克
         * bindDevTime : 2017-10-20 15:13:23
         * posLat : 30.54595803
         * posLng : 114.20006238
         * posLatBaidu : 30.54944179
         * posLngBaidu : 114.21208205
         * posType : WIFI
         * posCity : 武汉市
         * posAddr : 湖北省武汉市汉阳区龙珠路
         * gprsMode : N
         * confGprsMode : N
         * gpsMode : N
         * confGpsMode : N
         * fangchaiMode : N
         * confFangchaiMode : N
         * wifiMode : Y
         * confWifiMode : Y
         * fangchaiAlert : 0
         * fangchaiCountInit : 0
         * fangchaiCountLast : 0
         * restartCount : 9
         */

        private int id;
        private String devId;
        private String lastMsgTime;
        private int sleepTime;
        private int confSleepTime;
        private String carVinNo;
        private String carBrand;
        private String carModel;
        private String bindDevTime;
        private double posLat;
        private double posLng;
        private double posLatBaidu;
        private double posLngBaidu;
        private String posType;
        private String posCity;
        private String posAddr;
        private String gprsMode;
        private String confGprsMode;
        private String gpsMode;
        private String confGpsMode;
        private String fangchaiMode;
        private String confFangchaiMode;
        private String wifiMode;
        private String confWifiMode;
        private int fangchaiAlert;
        private int fangchaiCountInit;
        private int fangchaiCountLast;
        private int restartCount;

        public void setId(int id) {
            this.id = id;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        public void setLastMsgTime(String lastMsgTime) {
            this.lastMsgTime = lastMsgTime;
        }

        public void setSleepTime(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        public void setConfSleepTime(int confSleepTime) {
            this.confSleepTime = confSleepTime;
        }

        public void setCarVinNo(String carVinNo) {
            this.carVinNo = carVinNo;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public void setBindDevTime(String bindDevTime) {
            this.bindDevTime = bindDevTime;
        }

        public void setPosLat(double posLat) {
            this.posLat = posLat;
        }

        public void setPosLng(double posLng) {
            this.posLng = posLng;
        }

        public void setPosLatBaidu(double posLatBaidu) {
            this.posLatBaidu = posLatBaidu;
        }

        public void setPosLngBaidu(double posLngBaidu) {
            this.posLngBaidu = posLngBaidu;
        }

        public void setPosType(String posType) {
            this.posType = posType;
        }

        public void setPosCity(String posCity) {
            this.posCity = posCity;
        }

        public void setPosAddr(String posAddr) {
            this.posAddr = posAddr;
        }

        public void setGprsMode(String gprsMode) {
            this.gprsMode = gprsMode;
        }

        public void setConfGprsMode(String confGprsMode) {
            this.confGprsMode = confGprsMode;
        }

        public void setGpsMode(String gpsMode) {
            this.gpsMode = gpsMode;
        }

        public void setConfGpsMode(String confGpsMode) {
            this.confGpsMode = confGpsMode;
        }

        public void setFangchaiMode(String fangchaiMode) {
            this.fangchaiMode = fangchaiMode;
        }

        public void setConfFangchaiMode(String confFangchaiMode) {
            this.confFangchaiMode = confFangchaiMode;
        }

        public void setWifiMode(String wifiMode) {
            this.wifiMode = wifiMode;
        }

        public void setConfWifiMode(String confWifiMode) {
            this.confWifiMode = confWifiMode;
        }

        public void setFangchaiAlert(int fangchaiAlert) {
            this.fangchaiAlert = fangchaiAlert;
        }

        public void setFangchaiCountInit(int fangchaiCountInit) {
            this.fangchaiCountInit = fangchaiCountInit;
        }

        public void setFangchaiCountLast(int fangchaiCountLast) {
            this.fangchaiCountLast = fangchaiCountLast;
        }

        public void setRestartCount(int restartCount) {
            this.restartCount = restartCount;
        }

        public int getId() {
            return id;
        }

        public String getDevId() {
            return devId;
        }

        public String getLastMsgTime() {
            return lastMsgTime;
        }

        public int getSleepTime() {
            return sleepTime;
        }

        public int getConfSleepTime() {
            return confSleepTime;
        }

        public String getCarVinNo() {
            return carVinNo;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public String getCarModel() {
            return carModel;
        }

        public String getBindDevTime() {
            return bindDevTime;
        }

        public double getPosLat() {
            return posLat;
        }

        public double getPosLng() {
            return posLng;
        }

        public double getPosLatBaidu() {
            return posLatBaidu;
        }

        public double getPosLngBaidu() {
            return posLngBaidu;
        }

        public String getPosType() {
            return posType;
        }

        public String getPosCity() {
            return posCity;
        }

        public String getPosAddr() {
            return posAddr;
        }

        public String getGprsMode() {
            return gprsMode;
        }

        public String getConfGprsMode() {
            return confGprsMode;
        }

        public String getGpsMode() {
            return gpsMode;
        }

        public String getConfGpsMode() {
            return confGpsMode;
        }

        public String getFangchaiMode() {
            return fangchaiMode;
        }

        public String getConfFangchaiMode() {
            return confFangchaiMode;
        }

        public String getWifiMode() {
            return wifiMode;
        }

        public String getConfWifiMode() {
            return confWifiMode;
        }

        public int getFangchaiAlert() {
            return fangchaiAlert;
        }

        public int getFangchaiCountInit() {
            return fangchaiCountInit;
        }

        public int getFangchaiCountLast() {
            return fangchaiCountLast;
        }

        public int getRestartCount() {
            return restartCount;
        }
    }
}
