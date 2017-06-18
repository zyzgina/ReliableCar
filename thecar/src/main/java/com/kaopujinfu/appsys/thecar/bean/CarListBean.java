package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gina on 2017/6/18.
 */

public class CarListBean implements Serializable{


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 1
     * items : [{"id":7,"dlrLongName":"真格数码科技有限公司","carBrand":"AC Schnitzer","carSubBrand":"AC Schnitzer 7系","carModel":"2011款 ACS7 40i","vinNo":"1WWPR13C6AE170920","startTime":"2017-06-18 12:19:29"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<ItemsCarBean> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsCarBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsCarBean> items) {
        this.items = items;
    }

    public static class ItemsCarBean {
        /**
         * id : 7
         * dlrLongName : 真格数码科技有限公司
         * carBrand : AC Schnitzer
         * carSubBrand : AC Schnitzer 7系
         * carModel : 2011款 ACS7 40i
         * vinNo : 1WWPR13C6AE170920
         * startTime : 2017-06-18 12:19:29
         */

        private int id;
        private String dlrLongName;
        private String carBrand;
        private String carSubBrand;
        private String carModel;
        private String vinNo;
        private String startTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDlrLongName() {
            return dlrLongName;
        }

        public void setDlrLongName(String dlrLongName) {
            this.dlrLongName = dlrLongName;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getCarSubBrand() {
            return carSubBrand;
        }

        public void setCarSubBrand(String carSubBrand) {
            this.carSubBrand = carSubBrand;
        }

        public String getCarModel() {
            return carModel;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public String getVinNo() {
            return vinNo;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }



    @Override
    public String toString() {
        return "CarListBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    public static CarListBean getCarListBean(String result){
        CarListBean carListBean=null;
        try{
            carListBean=new Gson().fromJson(result,new TypeToken<CarListBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return carListBean;
    }
}
