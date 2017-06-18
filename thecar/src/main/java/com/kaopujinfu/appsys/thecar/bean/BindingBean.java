package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 监管器-》小圆盘列表
 * Created by 左丽姬 on 2017/5/19.
 */

public class BindingBean implements Serializable {

    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 1
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<BindingItem> items;

    public static class BindingItem implements Serializable {

        /**
         * id : 3
         * companyCode : 17015353
         * companyShortName : 百财车贷
         * companyLongName : 百财车贷
         * devCode : 006902
         * mac : F51F010B0D400C
         * rfid :
         * regTime : 2017-05-18 10:21:36
         * regUserId : U161152257
         * regUserName : 陈浪
         * initTime : 2017-05-18 16:31:36
         * initUserId : U161152257
         * initUserName : 陈浪
         * initVinNo : 11111111111111111
         * initComment :
         * initDismountCount : 36
         * initStationCode : ceshi1
         * status:监管中
         * carId : 3
         * afc : 17015353
         * afcShortName : 百财车贷
         * afcLongName : 百财车贷
         * afcProvince : 上海
         * afcCity : 上海市
         * afcRegion : 黄浦区
         * sv : 17010141
         * svShortName : 靠谱金服
         * svLongName : 北京靠谱金服
         * svProvince : 北京
         * svCity : 北京市
         * svRegion : 朝阳区
         * sp : 17016531
         * spShortName : 真格数码
         * spLongName : 上海真格数码科技
         * spProvince : 上海
         * spCity : 上海市
         * spRegion : 长宁区
         * dlr : 17017440
         * dlrShortName : 上海申协日产
         * dlrLongName : 上海申协日产专卖店
         * dlrProvince : 上海
         * dlrCity : 上海市
         * dlrRegion : 奉贤区
         * lastMsgTime : 2017-05-18 17:12:09
         * lastStationCode : ceshi1
         * lat :
         * lng :
         * latBaidu :
         * lngBaidu :
         * alertTimeout : 0
         * alertBat : 0
         * alertDismount : 0
         * dismountStatus : 0
         * dismountCount : 36
         * signalSeq : 742
         * signalStrength : 149
         * errTime :
         * errMsg :
         */

        private int id;
        private String companyCode;
        private String companyShortName;
        private String companyLongName;
        private String devCode;
        private String mac;
        private String rfid;
        private String regTime;
        private String regUserId;
        private String regUserName;
        private String initTime;
        private String initUserId;
        private String initUserName;
        private String initVinNo;
        private String initComment;
        private String initDismountCount;
        private String initStationCode;
        private String status;
        private String carId;
        private String afc;
        private String afcShortName;
        private String afcLongName;
        private String afcProvince;
        private String afcCity;
        private String afcRegion;
        private String sv;
        private String svShortName;
        private String svLongName;
        private String svProvince;
        private String svCity;
        private String svRegion;
        private String sp;
        private String spShortName;
        private String spLongName;
        private String spProvince;
        private String spCity;
        private String spRegion;
        private String dlr;
        private String dlrShortName;
        private String dlrLongName;
        private String dlrProvince;
        private String dlrCity;
        private String dlrRegion;
        private String lastMsgTime;
        private String lastStationCode;
        private String lat;
        private String lng;
        private String latBaidu;
        private String lngBaidu;
        private String alertTimeout;
        private String alertBat;
        private String alertDismount;
        private String dismountStatus;
        private String dismountCount;
        private String signalSeq;
        private String signalStrength;
        private String errTime;
        private String errMsg;
        private boolean selstatus = false;
        private boolean dategroup=false;//时间分组
        private int size=0;//判断该分组是否还有数据
        private int group=0;//判断是第几组

        public void setId(int id) {
            this.id = id;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public void setCompanyShortName(String companyShortName) {
            this.companyShortName = companyShortName;
        }

        public void setCompanyLongName(String companyLongName) {
            this.companyLongName = companyLongName;
        }

        public void setDevCode(String devCode) {
            this.devCode = devCode;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public void setRfid(String rfid) {
            this.rfid = rfid;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public void setRegUserId(String regUserId) {
            this.regUserId = regUserId;
        }

        public void setRegUserName(String regUserName) {
            this.regUserName = regUserName;
        }

        public void setInitTime(String initTime) {
            this.initTime = initTime;
        }

        public void setInitUserId(String initUserId) {
            this.initUserId = initUserId;
        }

        public void setInitUserName(String initUserName) {
            this.initUserName = initUserName;
        }

        public void setInitVinNo(String initVinNo) {
            this.initVinNo = initVinNo;
        }

        public void setInitComment(String initComment) {
            this.initComment = initComment;
        }

        public void setInitDismountCount(String initDismountCount) {
            this.initDismountCount = initDismountCount;
        }

        public void setInitStationCode(String initStationCode) {
            this.initStationCode = initStationCode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public void setAfc(String afc) {
            this.afc = afc;
        }

        public void setAfcShortName(String afcShortName) {
            this.afcShortName = afcShortName;
        }

        public void setAfcLongName(String afcLongName) {
            this.afcLongName = afcLongName;
        }

        public void setAfcProvince(String afcProvince) {
            this.afcProvince = afcProvince;
        }

        public void setAfcCity(String afcCity) {
            this.afcCity = afcCity;
        }

        public void setAfcRegion(String afcRegion) {
            this.afcRegion = afcRegion;
        }

        public void setSv(String sv) {
            this.sv = sv;
        }

        public void setSvShortName(String svShortName) {
            this.svShortName = svShortName;
        }

        public void setSvLongName(String svLongName) {
            this.svLongName = svLongName;
        }

        public void setSvProvince(String svProvince) {
            this.svProvince = svProvince;
        }

        public void setSvCity(String svCity) {
            this.svCity = svCity;
        }

        public void setSvRegion(String svRegion) {
            this.svRegion = svRegion;
        }

        public void setSp(String sp) {
            this.sp = sp;
        }

        public void setSpShortName(String spShortName) {
            this.spShortName = spShortName;
        }

        public void setSpLongName(String spLongName) {
            this.spLongName = spLongName;
        }

        public void setSpProvince(String spProvince) {
            this.spProvince = spProvince;
        }

        public void setSpCity(String spCity) {
            this.spCity = spCity;
        }

        public void setSpRegion(String spRegion) {
            this.spRegion = spRegion;
        }

        public void setDlr(String dlr) {
            this.dlr = dlr;
        }

        public void setDlrShortName(String dlrShortName) {
            this.dlrShortName = dlrShortName;
        }

        public void setDlrLongName(String dlrLongName) {
            this.dlrLongName = dlrLongName;
        }

        public void setDlrProvince(String dlrProvince) {
            this.dlrProvince = dlrProvince;
        }

        public void setDlrCity(String dlrCity) {
            this.dlrCity = dlrCity;
        }

        public void setDlrRegion(String dlrRegion) {
            this.dlrRegion = dlrRegion;
        }

        public void setLastMsgTime(String lastMsgTime) {
            this.lastMsgTime = lastMsgTime;
        }

        public void setLastStationCode(String lastStationCode) {
            this.lastStationCode = lastStationCode;
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

        public void setAlertTimeout(String alertTimeout) {
            this.alertTimeout = alertTimeout;
        }

        public void setAlertBat(String alertBat) {
            this.alertBat = alertBat;
        }

        public void setAlertDismount(String alertDismount) {
            this.alertDismount = alertDismount;
        }

        public void setDismountStatus(String dismountStatus) {
            this.dismountStatus = dismountStatus;
        }

        public void setDismountCount(String dismountCount) {
            this.dismountCount = dismountCount;
        }

        public void setSignalSeq(String signalSeq) {
            this.signalSeq = signalSeq;
        }

        public void setSignalStrength(String signalStrength) {
            this.signalStrength = signalStrength;
        }

        public void setErrTime(String errTime) {
            this.errTime = errTime;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public int getId() {
            return id;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public String getCompanyShortName() {
            return companyShortName;
        }

        public String getCompanyLongName() {
            return companyLongName;
        }

        public String getDevCode() {
            return devCode;
        }

        public String getMac() {
            return mac;
        }

        public String getRfid() {
            return rfid;
        }

        public String getRegTime() {
            return regTime;
        }

        public String getRegUserId() {
            return regUserId;
        }

        public String getRegUserName() {
            return regUserName;
        }

        public String getInitTime() {
            return initTime;
        }

        public String getInitUserId() {
            return initUserId;
        }

        public String getInitUserName() {
            return initUserName;
        }

        public String getInitVinNo() {
            return initVinNo;
        }

        public String getInitComment() {
            return initComment;
        }

        public String getInitDismountCount() {
            return initDismountCount;
        }

        public String getInitStationCode() {
            return initStationCode;
        }

        public String getCarId() {
            return carId;
        }

        public String getAfc() {
            return afc;
        }

        public String getAfcShortName() {
            return afcShortName;
        }

        public String getAfcLongName() {
            return afcLongName;
        }

        public String getAfcProvince() {
            return afcProvince;
        }

        public String getAfcCity() {
            return afcCity;
        }

        public String getAfcRegion() {
            return afcRegion;
        }

        public String getSv() {
            return sv;
        }

        public String getSvShortName() {
            return svShortName;
        }

        public String getSvLongName() {
            return svLongName;
        }

        public String getSvProvince() {
            return svProvince;
        }

        public String getSvCity() {
            return svCity;
        }

        public String getSvRegion() {
            return svRegion;
        }

        public String getSp() {
            return sp;
        }

        public String getSpShortName() {
            return spShortName;
        }

        public String getSpLongName() {
            return spLongName;
        }

        public String getSpProvince() {
            return spProvince;
        }

        public String getSpCity() {
            return spCity;
        }

        public String getSpRegion() {
            return spRegion;
        }

        public String getDlr() {
            return dlr;
        }

        public String getDlrShortName() {
            return dlrShortName;
        }

        public String getDlrLongName() {
            return dlrLongName;
        }

        public String getDlrProvince() {
            return dlrProvince;
        }

        public String getDlrCity() {
            return dlrCity;
        }

        public String getDlrRegion() {
            return dlrRegion;
        }

        public String getLastMsgTime() {
            return lastMsgTime;
        }

        public String getLastStationCode() {
            return lastStationCode;
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

        public String getAlertTimeout() {
            return alertTimeout;
        }

        public String getAlertBat() {
            return alertBat;
        }

        public String getAlertDismount() {
            return alertDismount;
        }

        public String getDismountStatus() {
            return dismountStatus;
        }

        public String getDismountCount() {
            return dismountCount;
        }

        public String getSignalSeq() {
            return signalSeq;
        }

        public String getSignalStrength() {
            return signalStrength;
        }

        public String getErrTime() {
            return errTime;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public boolean isSelstatus() {
            return selstatus;
        }

        public void setSelstatus(boolean selstatus) {
            this.selstatus = selstatus;
        }

        public boolean isDategroup() {
            return dategroup;
        }

        public void setDategroup(boolean dategroup) {
            this.dategroup = dategroup;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        @Override
        public String toString() {
            return "BindingItem{" +
                    "id=" + id +
                    ", companyCode='" + companyCode + '\'' +
                    ", companyShortName='" + companyShortName + '\'' +
                    ", companyLongName='" + companyLongName + '\'' +
                    ", devCode='" + devCode + '\'' +
                    ", mac='" + mac + '\'' +
                    ", rfid='" + rfid + '\'' +
                    ", regTime='" + regTime + '\'' +
                    ", regUserId='" + regUserId + '\'' +
                    ", regUserName='" + regUserName + '\'' +
                    ", initTime='" + initTime + '\'' +
                    ", initUserId='" + initUserId + '\'' +
                    ", initUserName='" + initUserName + '\'' +
                    ", initVinNo='" + initVinNo + '\'' +
                    ", initComment='" + initComment + '\'' +
                    ", initDismountCount='" + initDismountCount + '\'' +
                    ", initStationCode='" + initStationCode + '\'' +
                    ", status='" + status + '\'' +
                    ", carId='" + carId + '\'' +
                    ", afc='" + afc + '\'' +
                    ", afcShortName='" + afcShortName + '\'' +
                    ", afcLongName='" + afcLongName + '\'' +
                    ", afcProvince='" + afcProvince + '\'' +
                    ", afcCity='" + afcCity + '\'' +
                    ", afcRegion='" + afcRegion + '\'' +
                    ", sv='" + sv + '\'' +
                    ", svShortName='" + svShortName + '\'' +
                    ", svLongName='" + svLongName + '\'' +
                    ", svProvince='" + svProvince + '\'' +
                    ", svCity='" + svCity + '\'' +
                    ", svRegion='" + svRegion + '\'' +
                    ", sp='" + sp + '\'' +
                    ", spShortName='" + spShortName + '\'' +
                    ", spLongName='" + spLongName + '\'' +
                    ", spProvince='" + spProvince + '\'' +
                    ", spCity='" + spCity + '\'' +
                    ", spRegion='" + spRegion + '\'' +
                    ", dlr='" + dlr + '\'' +
                    ", dlrShortName='" + dlrShortName + '\'' +
                    ", dlrLongName='" + dlrLongName + '\'' +
                    ", dlrProvince='" + dlrProvince + '\'' +
                    ", dlrCity='" + dlrCity + '\'' +
                    ", dlrRegion='" + dlrRegion + '\'' +
                    ", lastMsgTime='" + lastMsgTime + '\'' +
                    ", lastStationCode='" + lastStationCode + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", latBaidu='" + latBaidu + '\'' +
                    ", lngBaidu='" + lngBaidu + '\'' +
                    ", alertTimeout='" + alertTimeout + '\'' +
                    ", alertBat='" + alertBat + '\'' +
                    ", alertDismount='" + alertDismount + '\'' +
                    ", dismountStatus='" + dismountStatus + '\'' +
                    ", dismountCount='" + dismountCount + '\'' +
                    ", signalSeq='" + signalSeq + '\'' +
                    ", signalStrength='" + signalStrength + '\'' +
                    ", errTime='" + errTime + '\'' +
                    ", errMsg='" + errMsg + '\'' +
                    ", selstatus=" + selstatus +
                    ", dategroup=" + dategroup +
                    ", size=" + size +
                    ", group=" + group +
                    '}';
        }
    }


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

    public List<BindingItem> getItems() {
        return items;
    }

    public void setItems(List<BindingItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "BindingBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

    /* 解析bean */
    public static BindingBean obtainBindingBean(String result) {
        BindingBean bindingBean = null;
        try {
            bindingBean = new Gson().fromJson(result, new TypeToken<BindingBean>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug("解析出错......");
        }
        return bindingBean;
    }
}
