package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 经销商GPS定位返回数据
 * Created Author: Gina
 * Created Date: 2017/6/14.
 */

public class DistributorGpsBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * in1KM : [{"name":"[0.7KM] 上海申协日产","dlr":"G-123456-0674"}]
     */

    private String result;
    private boolean success;
    private String comment;
    private List<GpsEntity> in1KM;
    private List<GpsEntity> other;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIn1KM(List<GpsEntity> in1KM) {
        this.in1KM = in1KM;
    }

    public void setOther(List<GpsEntity> other) {
        this.other = other;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<GpsEntity> getOther() {
        return other;
    }

    public String getResult() {
        return result;
    }


    public String getComment() {
        return comment;
    }

    public List<GpsEntity> getIn1KM() {
        return in1KM;
    }

    @Table(name = ("GpsEntity"))
    public static class GpsEntity implements Serializable {
        /**
         * name : [0.7KM] 上海申协日产
         * dlr : G-123456-0674
         */
        @Id(column = ("id"))
        private int id;
        private String loginId;//登录账户
        private String name;
        private String dist;
        private String dlr;
        private String data;

        public void setName(String name) {
            this.name = name;
        }

        public void setDlr(String dlr) {
            this.dlr = dlr;
        }

        public String getName() {
            return name;
        }

        public String getDlr() {
            return dlr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getDist() {
            return dist;
        }

        public void setDist(String dist) {
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "GpsEntity{" +
                    "id=" + id +
                    ", loginId='" + loginId + '\'' +
                    ", name='" + name + '\'' +
                    ", dist='" + dist + '\'' +
                    ", dlr='" + dlr + '\'' +
                    ", data='" + data + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DistributorGpsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", in1KM=" + in1KM +
                ", other=" + other +
                '}';
    }

    public static DistributorGpsBean getDistributorGpsBean(String result) {
        DistributorGpsBean gpsBean = null;
        try {
            gpsBean = new Gson().fromJson(result, new TypeToken<DistributorGpsBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gpsBean;
    }
}
