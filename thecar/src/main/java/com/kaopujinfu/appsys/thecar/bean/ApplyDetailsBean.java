package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 申请清单详情
 * Created Author: Gina
 * Created Date: 2017/6/27.
 */

public class ApplyDetailsBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * items : [{"id":1,"stage":"现场办理","carBrand":"长城宽带","vinNo":"LWVCAE782HA150788","carCount":"1","totalCarPrice":"60000","totalEvalCarPrice":"60000","loanAmount":"2998","loanId":"170627-001733"},{"id":2,"stage":"贷款初审","carBrand":"广汽菲克","vinNo":"LWVCAE732HA131226","carCount":"1","totalCarPrice":"600000","totalEvalCarPrice":"600000","loanAmount":"30000","loanId":"170627-005703"},{"id":3,"stage":"贷款终审","carBrand":"广汽菲克","vinNo":"LWVCAFM96HA145581","carCount":"1","totalCarPrice":"250000","totalEvalCarPrice":"250000","loanAmount":"12500","loanId":"170627-802031"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<ApplyDetailsItemsEntity> items;



    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<ApplyDetailsItemsEntity> items) {
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

    public List<ApplyDetailsItemsEntity> getItems() {
        return items;
    }

    public static class ApplyDetailsItemsEntity implements Serializable {
        /**
         * id : 1
         * stage : 现场办理
         * carBrand : 长城宽带
         * vinNo : LWVCAE782HA150788
         * carCount : 1
         * totalCarPrice : 60000
         * totalEvalCarPrice : 60000
         * loanAmount : 2998
         * loanId : 170627-001733
         */

        private int id;
        private String stage;
        private String carBrand;
        private String vinNo;
        private String carCount;
        private String totalCarPrice;
        private String totalEvalCarPrice;
        private String loanAmount;
        private String loanId;

        public void setId(int id) {
            this.id = id;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public void setCarCount(String carCount) {
            this.carCount = carCount;
        }

        public void setTotalCarPrice(String totalCarPrice) {
            this.totalCarPrice = totalCarPrice;
        }

        public void setTotalEvalCarPrice(String totalEvalCarPrice) {
            this.totalEvalCarPrice = totalEvalCarPrice;
        }

        public void setLoanAmount(String loanAmount) {
            this.loanAmount = loanAmount;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public int getId() {
            return id;
        }

        public String getStage() {
            return stage;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getCarCount() {
            return carCount;
        }

        public String getTotalCarPrice() {
            return totalCarPrice;
        }

        public String getTotalEvalCarPrice() {
            return totalEvalCarPrice;
        }

        public String getLoanAmount() {
            return loanAmount;
        }

        public String getLoanId() {
            return loanId;
        }

        @Override
        public String toString() {
            return "ItemsEntity{" +
                    "id=" + id +
                    ", stage='" + stage + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", carCount='" + carCount + '\'' +
                    ", totalCarPrice='" + totalCarPrice + '\'' +
                    ", totalEvalCarPrice='" + totalEvalCarPrice + '\'' +
                    ", loanAmount='" + loanAmount + '\'' +
                    ", loanId='" + loanId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApplyDetailsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", items=" + items +
                '}';
    }

    public static ApplyDetailsBean getApplyDetailsBean(String result) {
        ApplyDetailsBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<ApplyDetailsBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
