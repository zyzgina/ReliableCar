package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 贷款清单
 * Created Author: Gina
 * Created Date: 2017/6/28.
 */

public class LoanFormBean implements Serializable{


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * items : [{"dlrCode":"G-177004-5472","dlrName":"上海申协日产有限公司","creditAmount":"60000.00","leftAmount":"59998.75"},{"dlrCode":"G-177004-5472","dlrName":"上海申协日产有限公司","creditAmount":"60000.00","leftAmount":"59998.75"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<LoanItemsEntity> items;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<LoanItemsEntity> items) {
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

    public List<LoanItemsEntity> getItems() {
        return items;
    }

    public static class LoanItemsEntity implements Serializable {
        /**
         * dlrCode : G-177004-5472
         * dlrName : 上海申协日产有限公司
         * creditAmount : 60000.00
         * leftAmount : 59998.75
         */

        private String dlrCode;
        private String dlrName;
        private String creditAmount;
        private String leftAmount;

        public void setDlrCode(String dlrCode) {
            this.dlrCode = dlrCode;
        }

        public void setDlrName(String dlrName) {
            this.dlrName = dlrName;
        }

        public void setCreditAmount(String creditAmount) {
            this.creditAmount = creditAmount;
        }

        public void setLeftAmount(String leftAmount) {
            this.leftAmount = leftAmount;
        }

        public String getDlrCode() {
            return dlrCode;
        }

        public String getDlrName() {
            return dlrName;
        }

        public String getCreditAmount() {
            return creditAmount;
        }

        public String getLeftAmount() {
            return leftAmount;
        }

        @Override
        public String toString() {
            return "LoanItemsEntity{" +
                    "dlrCode='" + dlrCode + '\'' +
                    ", dlrName='" + dlrName + '\'' +
                    ", creditAmount='" + creditAmount + '\'' +
                    ", leftAmount='" + leftAmount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoanFormBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", items=" + items +
                '}';
    }

    public static LoanFormBean getLoanFormBean(String result){
        LoanFormBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<LoanFormBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
