package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/28.
 */

public class LoanDetailsBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * items : [{"disbTime":"2017-06-27","carBrand":"广汽菲克","carPrice":"600000","vinNo":"LWVCAE732HA131226","totalCarPrice":"600000","totalEvalCarPrice":"600000","totalDay":1,"interest":"16.44","interestR":16.44,"loanAmout":"30,000.00","loanAmoutR":30000},{"disbTime":"2017-06-27","carBrand":"广汽菲克","carPrice":"250000","vinNo":"LWVCAFM96HA145581","totalCarPrice":"250000","totalEvalCarPrice":"250000","totalDay":1,"interest":"6.85","interestR":6.85,"loanAmout":"12,500.00","loanAmoutR":12500}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<LoanDetailsItemsEntity> items;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<LoanDetailsItemsEntity> items) {
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

    public List<LoanDetailsItemsEntity> getItems() {
        return items;
    }

    public static class LoanDetailsItemsEntity implements Serializable {
        /**
         * disbTime : 2017-06-27
         * carBrand : 广汽菲克
         * carPrice : 600000
         * vinNo : LWVCAE732HA131226
         * totalCarPrice : 600000
         * totalEvalCarPrice : 600000
         * totalDay : 1
         * interest : 16.44
         * interestR : 16.44
         * loanAmout : 30,000.00
         * loanAmoutR : 30000
         */

        private String disbTime;
        private String stage;
        private String carBrand;
        private String carPrice;
        private String vinNo;
        private String totalCarPrice;
        private String totalEvalCarPrice;
        private int totalDay;
        private String interest;
        private double interestR;
        private String loanAmout;
        private int loanAmoutR;

        public void setDisbTime(String disbTime) {
            this.disbTime = disbTime;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public void setCarPrice(String carPrice) {
            this.carPrice = carPrice;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public void setTotalCarPrice(String totalCarPrice) {
            this.totalCarPrice = totalCarPrice;
        }

        public void setTotalEvalCarPrice(String totalEvalCarPrice) {
            this.totalEvalCarPrice = totalEvalCarPrice;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public void setInterestR(double interestR) {
            this.interestR = interestR;
        }

        public void setLoanAmout(String loanAmout) {
            this.loanAmout = loanAmout;
        }

        public void setLoanAmoutR(int loanAmoutR) {
            this.loanAmoutR = loanAmoutR;
        }

        public String getDisbTime() {
            return disbTime;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public String getCarPrice() {
            return carPrice;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getTotalCarPrice() {
            return totalCarPrice;
        }

        public String getTotalEvalCarPrice() {
            return totalEvalCarPrice;
        }

        public int getTotalDay() {
            return totalDay;
        }

        public String getInterest() {
            return interest;
        }

        public double getInterestR() {
            return interestR;
        }

        public String getLoanAmout() {
            return loanAmout;
        }

        public int getLoanAmoutR() {
            return loanAmoutR;
        }

        @Override
        public String toString() {
            return "LoanDetailsItemsEntity{" +
                    "disbTime='" + disbTime + '\'' +
                    ", stage='" + stage + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", carPrice='" + carPrice + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", totalCarPrice='" + totalCarPrice + '\'' +
                    ", totalEvalCarPrice='" + totalEvalCarPrice + '\'' +
                    ", totalDay=" + totalDay +
                    ", interest='" + interest + '\'' +
                    ", interestR=" + interestR +
                    ", loanAmout='" + loanAmout + '\'' +
                    ", loanAmoutR=" + loanAmoutR +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoanDetailsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", items=" + items +
                '}';
    }

    public static LoanDetailsBean getLoanDetailsBean(String result){
        LoanDetailsBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<LoanDetailsBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  bean;
    }
}
