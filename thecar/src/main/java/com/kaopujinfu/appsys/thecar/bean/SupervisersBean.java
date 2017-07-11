package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 监管清单列表
 * Created Author: Gina
 * Created Date: 2017/6/23.
 */

public class SupervisersBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * items : [{"shortName":"上海申协日产","longName":"上海申协日产有限公司","wareHouseCount":"0","companyCode":"G-177004-0153","arrcount":[{"status":"正常监管","count":"6"},{"status":"监管结束","count":"1"}]}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<SupervisersItemsEntity> items;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<SupervisersItemsEntity> items) {
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

    public List<SupervisersItemsEntity> getItems() {
        return items;
    }

    public static class SupervisersItemsEntity implements Serializable {

        /**
         * shortName : 上海申协日产
         * longName : 上海申协日产有限公司
         * wareHouseCount : 3
         * companyCode : G-177004-5472
         * sum : 13
         * status : 正常
         * count : 13
         */

        private String shortName;
        private String longName;
        private String wareHouseCount;
        private String companyCode;
        private int sum;
        private String status;
        private int count;

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public void setWareHouseCount(String wareHouseCount) {
            this.wareHouseCount = wareHouseCount;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getShortName() {
            return shortName;
        }

        public String getLongName() {
            return longName;
        }

        public String getWareHouseCount() {
            return wareHouseCount;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public int getSum() {
            return sum;
        }

        public String getStatus() {
            return status;
        }

        public int getCount() {
            return count;
        }
    }

    @Override
    public String toString() {
        return "SupervisersBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", items=" + items +
                '}';
    }

    public static SupervisersBean getSupervisersBean(String result) {
        SupervisersBean supervisersBean = null;
        try {
            supervisersBean = new Gson().fromJson(result, new TypeToken<SupervisersBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supervisersBean;
    }
}
