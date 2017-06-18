package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.util.List;

/**
 * Created by Doris on 2017/5/22.
 */

public class BusinessBean {
    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 2
     * items : [{"id":6,"companyCode":"17050851","shortName":"上海兴业","longName":"shanghasd","province":"北京","city":"北京市","region":"东城区"},{"id":4,"companyCode":"17017440","shortName":"上海申协日产","longName":"上海申协日产专卖店","province":"上海","city":"上海市","region":"奉贤区"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<ItemsBean> items;

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

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 6
         * companyCode : 17050851
         * shortName : 上海兴业
         * longName : shanghasd
         * province : 北京
         * city : 北京市
         * region : 东城区
         */

        private int id;
        private String companyCode;
        private String shortName;
        private String longName;
        private String province;
        private String city;
        private String region;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }

    /**
     * 经销商列表解析
     * @param result
     * @return
     * */
    public static BusinessBean getBusiness(String result) {
        BusinessBean resultBean = null;
        try {
            resultBean = new Gson().fromJson(result, new TypeToken<BusinessBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            LogTxt.getInstance().writeLog("经销商列表解析出错");
            LogUtils.debug("经销商列表解析出错");
        }
        return resultBean;
    }
}
