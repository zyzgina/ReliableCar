package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/4/26.
 */

public class BrandBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 191
     * items : [{"id":1,"brand":"AC Schnitzer","brandId":"117","brandLogo":"/car/brand/117.jpg"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<BrandItems> items;

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

    public void setItems(List<BrandItems> items) {
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

    public List<BrandItems> getItems() {
        return items;
    }

    @Table(name = "tb_brand")
    public static class BrandItems implements Serializable {
        @Id(column = "bid")
        private int bid;
        /**
         * id : 1
         * brand : AC Schnitzer
         * brandId : 117
         * brandLogo : /car/brand/117.jpg
         */
        private int id;
        private String brand;
        private String brandId;
        private String brandLogo;
        /**
         * subBrand : Dacia Duster
         * subBrandId : 2625
         * subBrandImg : /car/subbrand/2625.jpg
         */

        private String subBrand;
        private String subBrandId;
        private String subBrandImg;
        /**
         * model : 2011款 ACS6 35i
         * modelId : 7713
         * onSale : 停售
         * manuracturer : AC Schnitzer
         * modelYear : 2011
         * publicPrice :
         * carLevel :
         * engine : 3.0升 涡轮增压 320马力
         * gearBox : 8挡手自一体
         * bodyStructure :
         * sizeL :
         * sizeW :
         * sizeH :
         * sizeWheelBase :
         * countDoor :
         * countSeat :
         * engineVolumn :
         * engineType :
         * engineLayout :
         * engineRoomCount :
         * engineRoomDoor :
         */

        private String model;
        private String modelId;
        private String onSale;
        private String manuracturer;
        private String modelYear;
        private String publicPrice;
        private String carLevel;
        private String engine;
        private String gearBox;
        private String bodyStructure;
        private String sizeL;
        private String sizeW;
        private String sizeH;
        private String sizeWheelBase;
        private String countDoor;
        private String countSeat;
        private String engineVolumn;
        private String engineType;
        private String engineLayout;
        private String engineRoomCount;
        private String engineRoomDoor;

        private int leve=0;


        public void setId(int id) {
            this.id = id;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public int getLeve() {
            return leve;
        }

        public void setLeve(int leve) {
            this.leve = leve;
        }

        public int getId() {
            return id;
        }

        public String getBrand() {
            return brand;
        }

        public String getBrandId() {
            return brandId;
        }

        public String getBrandLogo() {
            return brandLogo;
        }


        @Override
        public String toString() {
            return "BrandItems{" +
                    "bid=" + bid +
                    ", id=" + id +
                    ", brand='" + brand + '\'' +
                    ", brandId='" + brandId + '\'' +
                    ", brandLogo='" + brandLogo + '\'' +
                    ", subBrand='" + subBrand + '\'' +
                    ", subBrandId='" + subBrandId + '\'' +
                    ", subBrandImg='" + subBrandImg + '\'' +
                    ", model='" + model + '\'' +
                    ", modelId='" + modelId + '\'' +
                    ", onSale='" + onSale + '\'' +
                    ", manuracturer='" + manuracturer + '\'' +
                    ", modelYear='" + modelYear + '\'' +
                    ", publicPrice='" + publicPrice + '\'' +
                    ", carLevel='" + carLevel + '\'' +
                    ", engine='" + engine + '\'' +
                    ", gearBox='" + gearBox + '\'' +
                    ", bodyStructure='" + bodyStructure + '\'' +
                    ", sizeL='" + sizeL + '\'' +
                    ", sizeW='" + sizeW + '\'' +
                    ", sizeH='" + sizeH + '\'' +
                    ", sizeWheelBase='" + sizeWheelBase + '\'' +
                    ", countDoor='" + countDoor + '\'' +
                    ", countSeat='" + countSeat + '\'' +
                    ", engineVolumn='" + engineVolumn + '\'' +
                    ", engineType='" + engineType + '\'' +
                    ", engineLayout='" + engineLayout + '\'' +
                    ", engineRoomCount='" + engineRoomCount + '\'' +
                    ", engineRoomDoor='" + engineRoomDoor + '\'' +
                    ", leve=" + leve +
                    '}';
        }

        public void setSubBrand(String subBrand) {
            this.subBrand = subBrand;
        }

        public void setSubBrandId(String subBrandId) {
            this.subBrandId = subBrandId;
        }

        public void setSubBrandImg(String subBrandImg) {
            this.subBrandImg = subBrandImg;
        }

        public String getSubBrand() {
            return subBrand;
        }

        public String getSubBrandId() {
            return subBrandId;
        }

        public String getSubBrandImg() {
            return subBrandImg;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public void setOnSale(String onSale) {
            this.onSale = onSale;
        }

        public void setManuracturer(String manuracturer) {
            this.manuracturer = manuracturer;
        }

        public void setModelYear(String modelYear) {
            this.modelYear = modelYear;
        }

        public void setPublicPrice(String publicPrice) {
            this.publicPrice = publicPrice;
        }

        public void setCarLevel(String carLevel) {
            this.carLevel = carLevel;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public void setGearBox(String gearBox) {
            this.gearBox = gearBox;
        }

        public void setBodyStructure(String bodyStructure) {
            this.bodyStructure = bodyStructure;
        }

        public void setSizeL(String sizeL) {
            this.sizeL = sizeL;
        }

        public void setSizeW(String sizeW) {
            this.sizeW = sizeW;
        }

        public void setSizeH(String sizeH) {
            this.sizeH = sizeH;
        }

        public void setSizeWheelBase(String sizeWheelBase) {
            this.sizeWheelBase = sizeWheelBase;
        }

        public void setCountDoor(String countDoor) {
            this.countDoor = countDoor;
        }

        public void setCountSeat(String countSeat) {
            this.countSeat = countSeat;
        }

        public void setEngineVolumn(String engineVolumn) {
            this.engineVolumn = engineVolumn;
        }

        public void setEngineType(String engineType) {
            this.engineType = engineType;
        }

        public void setEngineLayout(String engineLayout) {
            this.engineLayout = engineLayout;
        }

        public void setEngineRoomCount(String engineRoomCount) {
            this.engineRoomCount = engineRoomCount;
        }

        public void setEngineRoomDoor(String engineRoomDoor) {
            this.engineRoomDoor = engineRoomDoor;
        }

        public String getModel() {
            return model;
        }

        public String getModelId() {
            return modelId;
        }

        public String getOnSale() {
            return onSale;
        }

        public String getManuracturer() {
            return manuracturer;
        }

        public String getModelYear() {
            return modelYear;
        }

        public String getPublicPrice() {
            return publicPrice;
        }

        public String getCarLevel() {
            return carLevel;
        }

        public String getEngine() {
            return engine;
        }

        public String getGearBox() {
            return gearBox;
        }

        public String getBodyStructure() {
            return bodyStructure;
        }

        public String getSizeL() {
            return sizeL;
        }

        public String getSizeW() {
            return sizeW;
        }

        public String getSizeH() {
            return sizeH;
        }

        public String getSizeWheelBase() {
            return sizeWheelBase;
        }

        public String getCountDoor() {
            return countDoor;
        }

        public String getCountSeat() {
            return countSeat;
        }

        public String getEngineVolumn() {
            return engineVolumn;
        }

        public String getEngineType() {
            return engineType;
        }

        public String getEngineLayout() {
            return engineLayout;
        }

        public String getEngineRoomCount() {
            return engineRoomCount;
        }

        public String getEngineRoomDoor() {
            return engineRoomDoor;
        }
    }

    @Override
    public String toString() {
        return "BrandBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
    public static BrandBean getBrandBean(String result){
        BrandBean brandBean=null;
        try{
            brandBean=new Gson().fromJson(result,new TypeToken<BrandBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return brandBean;
    }
}
