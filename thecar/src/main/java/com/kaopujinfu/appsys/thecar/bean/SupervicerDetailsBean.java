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
         * vinStatus : 12
         * rfidStatus : 20
         * appCheckStatus : 30
         * gpsStatus : 40
         * rfDevStatus : 50
         * docStatus : 60
         * dlrShortName : 上海申协日产
         * dlrLongName : 上海申协日产有限公司
         * carColor :
         * carParkNo :
         * carPlate :
         * vinNo : 99999999999999999
         * dlr : G-177004-5472
         * carBrand : 45
         */

        private String vinStatus;
        private String rfidStatus;
        private String appCheckStatus;
        private String gpsStatus;
        private String rfDevStatus;
        private String docStatus;
        private String dlrShortName;
        private String dlrLongName;
        private String carColor;
        private String carParkNo;
        private String carPlate;
        private String vinNo;
        private String dlr;
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

        public void setDlrShortName(String dlrShortName) {
            this.dlrShortName = dlrShortName;
        }

        public void setDlrLongName(String dlrLongName) {
            this.dlrLongName = dlrLongName;
        }

        public void setCarColor(String carColor) {
            this.carColor = carColor;
        }

        public void setCarParkNo(String carParkNo) {
            this.carParkNo = carParkNo;
        }

        public void setCarPlate(String carPlate) {
            this.carPlate = carPlate;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public void setDlr(String dlr) {
            this.dlr = dlr;
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

        public String getDlrShortName() {
            return dlrShortName;
        }

        public String getDlrLongName() {
            return dlrLongName;
        }

        public String getCarColor() {
            return carColor;
        }

        public String getCarParkNo() {
            return carParkNo;
        }

        public String getCarPlate() {
            return carPlate;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getDlr() {
            return dlr;
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
                    ", dlrShortName='" + dlrShortName + '\'' +
                    ", dlrLongName='" + dlrLongName + '\'' +
                    ", carColor='" + carColor + '\'' +
                    ", carParkNo='" + carParkNo + '\'' +
                    ", carPlate='" + carPlate + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", dlr='" + dlr + '\'' +
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
