package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 申请清单
 * Created Author: Gina
 * Created Date: 2017/6/23.
 */

public class ApplyBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment :
     * items : [{}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<ApplyItemsEntity> items;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItems(List<ApplyItemsEntity> items) {
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

    public List<ApplyItemsEntity> getItems() {
        return items;
    }

    public static class ApplyItemsEntity implements Serializable {

        /**
         * dlrCode : G-177004-5472
         * dlrName : 上海申协日产有限公司
         * management : 1
         * first : 1
         * final : 1
         */

        private String dlrCode;
        private String dlrName;
        private int management;
        private int first;
        @SerializedName("final")
        private int finalX;
        private int pend;

        public void setDlrCode(String dlrCode) {
            this.dlrCode = dlrCode;
        }

        public void setDlrName(String dlrName) {
            this.dlrName = dlrName;
        }

        public void setManagement(int management) {
            this.management = management;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public void setFinalX(int finalX) {
            this.finalX = finalX;
        }

        public String getDlrCode() {
            return dlrCode;
        }

        public String getDlrName() {
            return dlrName;
        }

        public int getManagement() {
            return management;
        }

        public int getFirst() {
            return first;
        }

        public int getFinalX() {
            return finalX;
        }

        public int getPend() {
            return pend;
        }

        public void setPend(int pend) {
            this.pend = pend;
        }

        @Override
        public String toString() {
            return "ApplyItemsEntity{" +
                    "dlrCode='" + dlrCode + '\'' +
                    ", dlrName='" + dlrName + '\'' +
                    ", management=" + management +
                    ", first=" + first +
                    ", finalX=" + finalX +
                    ", pend=" + pend +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApplyBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", items=" + items +
                '}';
    }

    public static ApplyBean getApplyBean(String result) {
        ApplyBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<ApplyBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
