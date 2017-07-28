package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

/**
 * Describe: 查询车辆品牌信息
 * Created Author: Gina
 * Created Date: 2017/7/28.
 */

public class QueryVinBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * state : YES
     * vin : LWVCAE739HA135418
     * brand : 广汽菲克
     * subBrand : 自由光
     * model : 自由光 2016款 2.0L 领先版
     * price : 20.98
     */

    private String result;
    private boolean success;
    private String comment;
    private String state;
    private String vin;
    private String brand;
    private String subBrand;
    private String model;
    private double price;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSubBrand(String subBrand) {
        this.subBrand = subBrand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getState() {
        return state;
    }

    public String getVin() {
        return vin;
    }

    public String getBrand() {
        return brand;
    }

    public String getSubBrand() {
        return subBrand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "QueryVinBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", state='" + state + '\'' +
                ", vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", subBrand='" + subBrand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
    public static QueryVinBean getQueryVinBean(String result){
        QueryVinBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<QueryVinBean>(){}.getType());
        }catch(Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
