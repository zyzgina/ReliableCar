package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 监管清单详情
 * Created Author: Gina
 * Created Date: 2017/6/23.
 */

public class SupervicerDetailsBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 7
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<SupDetailsItemsEntity> items;

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

    public void setItems(List<SupDetailsItemsEntity> items) {
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

    public List<SupDetailsItemsEntity> getItems() {
        return items;
    }

    public static class SupDetailsItemsEntity implements Serializable{

        /**
         * vinStatus : 1
         * rfidStatus : 1
         * appCheckStatus : 0
         * gpsStatus : 0
         * rfDevStatus : 0
         * docStatus : 0
         * vinNo : 22222222222222222
         * carBrand : 1
         */

        private String vinStatus;
        private String rfidStatus;
        private String appCheckStatus;
        private String gpsStatus;
        private String rfDevStatus;
        private String docStatus;
        private String vinNo;
        private String carBrand;

        public void setVinStatus(String vinStatus) {
            this.vinStatus = vinStatus;
        }

        public void setRfidStatus(String rfidStatus) {
            this.rfidStatus = rfidStatus;
        }

        public void setAppCheckStatus(String appCheckStatus) {
            this.appCheckStatus = appCheckStatus;
        }

        public void setGpsStatus(String gpsStatus) {
            this.gpsStatus = gpsStatus;
        }

        public void setRfDevStatus(String rfDevStatus) {
            this.rfDevStatus = rfDevStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getVinStatus() {
            return vinStatus;
        }

        public String getRfidStatus() {
            return rfidStatus;
        }

        public String getAppCheckStatus() {
            return appCheckStatus;
        }

        public String getGpsStatus() {
            return gpsStatus;
        }

        public String getRfDevStatus() {
            return rfDevStatus;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getCarBrand() {
            return carBrand;
        }

        @Override
        public String toString() {
            return "SupDetailsItemsEntity{" +
                    "vinStatus='" + vinStatus + '\'' +
                    ", rfidStatus='" + rfidStatus + '\'' +
                    ", appCheckStatus='" + appCheckStatus + '\'' +
                    ", gpsStatus='" + gpsStatus + '\'' +
                    ", rfDevStatus='" + rfDevStatus + '\'' +
                    ", docStatus='" + docStatus + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SupervicerDetailsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
    public static SupervicerDetailsBean getSupervicerDetailsBean(String result){
        SupervicerDetailsBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<SupervicerDetailsBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
