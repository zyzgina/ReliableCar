package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:柜子详情
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class CabinetDetailsBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * cells : [{"row":1,"boxCode":"公司外演示柜","c1Id":1,"c1Content":"","c2Id":2,"c2Content":""}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<CellsEntity> cells;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCells(List<CellsEntity> cells) {
        this.cells = cells;
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

    public List<CellsEntity> getCells() {
        return cells;
    }

    public static class CellsEntity implements Serializable {
        /**
         * row : 1
         * boxCode : 公司外演示柜
         * cId : 1
         * cContent :
         * vinNo :
         * carBrand :
         */

        private int id;
        private String boxCode;
        private String cId;
        private String cContent;
        private String vinNo;
        private String carBrand;


        public void setBoxCode(String boxCode) {
            this.boxCode = boxCode;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public void setCContent(String cContent) {
            this.cContent = cContent;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBoxCode() {
            return boxCode;
        }

        public String getCId() {
            return cId;
        }

        public String getCContent() {
            return cContent;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getCarBrand() {
            return carBrand;
        }

        @Override
        public String toString() {
            return "CellsEntity{" +
                    "id=" + id +
                    ", boxCode='" + boxCode + '\'' +
                    ", cId=" + cId +
                    ", cContent='" + cContent + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CabinetDetailsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", cells=" + cells +
                '}';
    }

    public static CabinetDetailsBean getCabinetDetailsBean(String result) {
        CabinetDetailsBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<CabinetDetailsBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

}
