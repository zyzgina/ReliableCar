package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/7/18.
 */

public class CabinetListsBean implements Serializable{

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 2
     * items : [{"id":"1","boxCode":"公司外演示柜","cellCount":"126","companyShortName":"建元资本","alert220v":"1","alertBat":"0","alertTemperature":"0","alertHumidity":"0","alertNoise":"0","alertSpeed":"0","alertHuman":"0","alertDoorOpen":"0","alertTimeout":"0"},{"id":"2","boxCode":"0001","cellCount":"5","companyShortName":"建元资本","alert220v":"1","alertBat":"0","alertTemperature":"0","alertHumidity":"0","alertNoise":"0","alertSpeed":"1","alertHuman":"1","alertDoorOpen":"0","alertTimeout":"0"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<CabinetEntity> items;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setItems(List<CabinetEntity> items) {
        this.items = items;
    }

    public String getResult() {
        return result;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getComment() {
        return comment;
    }

    public int getTotal() {
        return total;
    }

    public List<CabinetEntity> getItems() {
        return items;
    }

    public static class CabinetEntity implements Serializable {
        /**
         * id : 1
         * boxCode : 公司外演示柜
         * cellCount : 126
         * companyShortName : 建元资本
         * alert220v : 1
         * alertBat : 0
         * alertTemperature : 0
         * alertHumidity : 0
         * alertNoise : 0
         * alertSpeed : 0
         * alertHuman : 0
         * alertDoorOpen : 0
         * alertTimeout : 0
         */

        private String id;
        private String boxCode;
        private String cellCount;
        private String companyShortName;
        private String alert220v;
        private String alertBat;
        private String alertTemperature;
        private String alertHumidity;
        private String alertNoise;
        private String alertSpeed;
        private String alertHuman;
        private String alertDoorOpen;
        private String alertTimeout;

        public void setId(String id) {
            this.id = id;
        }

        public void setBoxCode(String boxCode) {
            this.boxCode = boxCode;
        }

        public void setCellCount(String cellCount) {
            this.cellCount = cellCount;
        }

        public void setCompanyShortName(String companyShortName) {
            this.companyShortName = companyShortName;
        }

        public void setAlert220v(String alert220v) {
            this.alert220v = alert220v;
        }

        public void setAlertBat(String alertBat) {
            this.alertBat = alertBat;
        }

        public void setAlertTemperature(String alertTemperature) {
            this.alertTemperature = alertTemperature;
        }

        public void setAlertHumidity(String alertHumidity) {
            this.alertHumidity = alertHumidity;
        }

        public void setAlertNoise(String alertNoise) {
            this.alertNoise = alertNoise;
        }

        public void setAlertSpeed(String alertSpeed) {
            this.alertSpeed = alertSpeed;
        }

        public void setAlertHuman(String alertHuman) {
            this.alertHuman = alertHuman;
        }

        public void setAlertDoorOpen(String alertDoorOpen) {
            this.alertDoorOpen = alertDoorOpen;
        }

        public void setAlertTimeout(String alertTimeout) {
            this.alertTimeout = alertTimeout;
        }

        public String getId() {
            return id;
        }

        public String getBoxCode() {
            return boxCode;
        }

        public String getCellCount() {
            return cellCount;
        }

        public String getCompanyShortName() {
            return companyShortName;
        }

        public String getAlert220v() {
            return alert220v;
        }

        public String getAlertBat() {
            return alertBat;
        }

        public String getAlertTemperature() {
            return alertTemperature;
        }

        public String getAlertHumidity() {
            return alertHumidity;
        }

        public String getAlertNoise() {
            return alertNoise;
        }

        public String getAlertSpeed() {
            return alertSpeed;
        }

        public String getAlertHuman() {
            return alertHuman;
        }

        public String getAlertDoorOpen() {
            return alertDoorOpen;
        }

        public String getAlertTimeout() {
            return alertTimeout;
        }

        @Override
        public String toString() {
            return "CabinetEntity{" +
                    "id='" + id + '\'' +
                    ", boxCode='" + boxCode + '\'' +
                    ", cellCount='" + cellCount + '\'' +
                    ", companyShortName='" + companyShortName + '\'' +
                    ", alert220v='" + alert220v + '\'' +
                    ", alertBat='" + alertBat + '\'' +
                    ", alertTemperature='" + alertTemperature + '\'' +
                    ", alertHumidity='" + alertHumidity + '\'' +
                    ", alertNoise='" + alertNoise + '\'' +
                    ", alertSpeed='" + alertSpeed + '\'' +
                    ", alertHuman='" + alertHuman + '\'' +
                    ", alertDoorOpen='" + alertDoorOpen + '\'' +
                    ", alertTimeout='" + alertTimeout + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CabinetListsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    public static CabinetListsBean getCabinetListsBean(String result){
        CabinetListsBean bean=null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<CabinetListsBean>() {
            }.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
