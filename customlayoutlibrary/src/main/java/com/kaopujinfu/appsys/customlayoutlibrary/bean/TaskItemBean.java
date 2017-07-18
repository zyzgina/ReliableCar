package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 盘库任务车辆信息
 * Created Author: Gina
 * Created Date: 2017/6/1.
 */

public class TaskItemBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 2
     * items : [{"id":5,"taskCode":"170601-648271","itemCode":"170601-476251","carId":"1","carBrand":"ALPINA","carSubBrand":"B4 BITURBO","carModel":"B4 BITURBO 2016款 B4 BITURBO Coupe","vinNo":"11111111111111111","checkTime":"","checkMethod":"","lat":"","lng":"","latBaidu":"","lngBaidu":""},{"id":6,"taskCode":"170601-648271","itemCode":"170601-236264","carId":"2","carBrand":"Jeep","carSubBrand":"切诺基(进口)","carModel":"1997款 4.0","vinNo":"22222222222222222","checkTime":"","checkMethod":"","lat":"","lng":"","latBaidu":"","lngBaidu":""}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<TaskItemsEntity> items;

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

    public void setItems(List<TaskItemsEntity> items) {
        this.items = items;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getComment() {
        return comment;
    }

    public int getTotal() {
        return total;
    }

    public List<TaskItemsEntity> getItems() {
        return items;
    }

    @Table(name = "TaskItemsEntity")
    public static class TaskItemsEntity {
        /**
         * id : 5
         * taskCode : 170601-648271 taskCode : 170601-648271
         * itemCode : 170601-476251
         * carId : 1
         * carBrand : ALPINA
         * carSubBrand : B4 BITURBO
         * carModel : B4 BITURBO 2016款 B4 BITURBO Coupe
         * vinNo : 11111111111111111
         * rfidId
         * rfidEpc
         * checkTime :
         * checkMethod :
         * lat :
         * lng :
         * latBaidu :
         * lngBaidu :
         */
        @Id(column = "tid")
        private int tid;
        private int id;
        private String taskCode;
        private String itemCode;
        private String carId;
        private String carBrand;
        private String carSubBrand;
        private String carModel;
        private String vinNo;
        private String rfidId;
        private String rfidEpc;
        private String checkTime;
        private String checkMethod;
        private String lat;
        private String lng;
        private String latBaidu;
        private String lngBaidu;
        private int commit_status;//提交状态 0:未提交 1：已完成 2:已提交服务器
        private String distributor;
        private String finance;
        private int allowVideo=1;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getCommit_status() {
            return commit_status;
        }

        public void setCommit_status(int commit_status) {
            this.commit_status = commit_status;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTaskCode(String taskCode) {
            this.taskCode = taskCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public void setCarSubBrand(String carSubBrand) {
            this.carSubBrand = carSubBrand;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public String getRfidId() {
            return rfidId;
        }

        public void setRfidId(String rfidId) {
            this.rfidId = rfidId;
        }

        public String getRfidEpc() {
            return rfidEpc;
        }

        public void setRfidEpc(String rfidEpc) {
            this.rfidEpc = rfidEpc;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public void setCheckMethod(String checkMethod) {
            this.checkMethod = checkMethod;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setLatBaidu(String latBaidu) {
            this.latBaidu = latBaidu;
        }

        public void setLngBaidu(String lngBaidu) {
            this.lngBaidu = lngBaidu;
        }

        public int getId() {
            return id;
        }

        public String getTaskCode() {
            return taskCode;
        }

        public String getItemCode() {
            return itemCode;
        }

        public String getCarId() {
            return carId;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public String getCarSubBrand() {
            return carSubBrand;
        }

        public String getCarModel() {
            return carModel;
        }

        public String getVinNo() {
            return vinNo;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public String getCheckMethod() {
            return checkMethod;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }

        public String getLatBaidu() {
            return latBaidu;
        }

        public String getLngBaidu() {
            return lngBaidu;
        }

        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public String getFinance() {
            return finance;
        }

        public void setFinance(String finance) {
            this.finance = finance;
        }

        public int getAllowVideo() {
            return allowVideo;
        }

        public void setAllowVideo(int allowVideo) {
            this.allowVideo = allowVideo;
        }

        @Override
        public String toString() {
            return "TaskItemsEntity{" +
                    "tid=" + tid +
                    ", id=" + id +
                    ", taskCode='" + taskCode + '\'' +
                    ", itemCode='" + itemCode + '\'' +
                    ", carId='" + carId + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", carSubBrand='" + carSubBrand + '\'' +
                    ", carModel='" + carModel + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", rfidId='" + rfidId + '\'' +
                    ", rfidEpc='" + rfidEpc + '\'' +
                    ", checkTime='" + checkTime + '\'' +
                    ", checkMethod='" + checkMethod + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", latBaidu='" + latBaidu + '\'' +
                    ", lngBaidu='" + lngBaidu + '\'' +
                    ", commit_status=" + commit_status +
                    ", distributor='" + distributor + '\'' +
                    ", finance='" + finance + '\'' +
                    ", allowVideo=" + allowVideo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TaskItemBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    public static TaskItemBean getTaskItemBean(String result, String distributor, String finance) {
        TaskItemBean bean = null;
        try {
            bean = new Gson().fromJson(result, new TypeToken<TaskItemBean>() {
            }.getType());
            /* 判断该车是否已经盘库 */
            if (bean != null && bean.getItems() != null) {
                for (TaskItemsEntity entity : bean.getItems()) {
                    entity.setDistributor(distributor);
                    entity.setFinance(finance);
                    if (GeneralUtils.isEmpty(entity.getCheckTime())) {
                        entity.setCommit_status(0);
                    } else {
                        entity.setCommit_status(2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

}
