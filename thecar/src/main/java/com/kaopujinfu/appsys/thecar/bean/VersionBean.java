package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:版本更新
 * Created Author: Gina
 * Created Date: 2017/8/14.
 */

public class VersionBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 2
     * items : [{"id":22,"appType":"Android","appVersion":"1.3","changeLog":"1.哈哈哈哈\n2.呵呵呵呵\n3.嘿嘿嘿嘿","changeTime":"2017-08-09 16:27:05","url":""},{"id":27,"appType":"Android","appVersion":"1.4","changeLog":"fff","changeTime":"2017-08-09 17:44:34","url":""}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<VersionEntity> items;

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

    public void setItems(List<VersionEntity> items) {
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

    public List<VersionEntity> getItems() {
        return items;
    }

    public static class VersionEntity implements Serializable{
        /**
         * id : 22
         * appType : Android
         * appVersion : 1.3
         * changeLog : 1.哈哈哈哈
         2.呵呵呵呵
         3.嘿嘿嘿嘿
         * changeTime : 2017-08-09 16:27:05
         * url :
         */

        private int id;
        private String appType;
        private String appVersion;
        private String changeLog;
        private String changeTime;
        private String url;

        public void setId(int id) {
            this.id = id;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public void setChangeLog(String changeLog) {
            this.changeLog = changeLog;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public String getAppType() {
            return appType;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public String getChangeLog() {
            return changeLog;
        }

        public String getChangeTime() {
            return changeTime;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "VersionEntity{" +
                    "id=" + id +
                    ", appType='" + appType + '\'' +
                    ", appVersion='" + appVersion + '\'' +
                    ", changeLog='" + changeLog + '\'' +
                    ", changeTime='" + changeTime + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    public static VersionBean getVersionBean(String result){
        VersionBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<VersionBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
