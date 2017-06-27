package com.kaopujinfu.appsys.thecar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 监管清单详情
 * Created Author: Gina
 * Created Date: 2017/6/23.
 */

public class SupervicerDetailsBean implements Serializable {


    /**
     * result : SUCCESS
     * success : true
     * comment : OK
     * total : 7
     * items : [{"id":1,"source":"手工添加","afc":"177004","afcShortName":"建元资本","afcLongName":"建元资本有限公司","afcProvince":"","afcCity":"","afcRegion":"","sv":"G-177004-7818","svShortName":"北京看车","svLongName":"北京看车有限公司","svProvince":"北京","svCity":"北京市","svRegion":"东城区","sp":"G-177004-8786","spShortName":"上海真格数码","spLongName":"上海真格数码有限公司","spProvince":"上海","spCity":"上海市","spRegion":"长宁区","dlr":"G-177004-0153","dlrShortName":"上海申协日产","dlrLongName":"上海申协日产有限公司","dlrProvince":"上海","dlrCity":"上海市","dlrRegion":"长宁区","creationUserId":"admin@177004","creationUserName":"","startTime":"2017-06-12","endTime":"","status":"正常监管","comment":"","vinNo":"LWVDA2045HB039415","carBrand":"广汽菲克","carSubBrand":"自由侠","carModel":"自由侠 2016款 1.4T 自动 动能版+","carPrice":"144444","totalKm":"","evalCarPrice":"144444","price":"144,444.00","priceR":"144444","regDate":"","usedCar":"0","loanId":"","loanAmount":"","loanStatus":"","alertStatus":"","rfDevCode":"","rfInitCount":"","rfAlertDismount":"","rfAlertBattery":"","rfAlertMsgTimeout":"","rfLastMsg":"","rfLastWarehouseId":"","rfLastWarehouseName":"","rfLat":"","rfLng":"","rfLatBaidu":"","rfLngBaidu":"","rfidId":"","rfidLastScan":"","rfidLastResult":"","rfidLastWarehouseId":"","rfidLastWarehouseName":"","rfidLat":"","rfidLng":"","rfidLatBaidu":"","rfidLngBaidu":"","gpsDevId":"","gpsSleepTime":"","gpsAlertBattery":"","gpsAlertDismount":"","gpsLastMsg":"","gpsLastMsgType":"","gpsLastWarehouseId":"","gpsLastWarehouseName":"","gpsLat":"","gpsLng":"","gpsLatBaidu":"","gpsLngBaidu":"","appLastCheck":"","appCheckUserId":"","appCheckUserName":"","appCheckResult":"","appCheckTaskId":"","docMonitorType":"","docList":"","docRfid":"","docLastCheck":"","docLastCheckMethod":"","docStatus":"","docLastAudit":"","docAuditUserId":"","docAuditUserName":"","docAuditResult":"","docAuditMethod":"","docAuditTaskId":""}]
     */

    private String result;
    private boolean success;
    private String comment;
    private int total;
    private List<SupDetailsItemsEntity> items;

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

    public void setItems(List<SupDetailsItemsEntity> items) {
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

    public int getTotal() {
        return total;
    }

    public List<SupDetailsItemsEntity> getItems() {
        return items;
    }

    public static class SupDetailsItemsEntity {
        /**
         * id : 1
         * source : 手工添加
         * afc : 177004
         * afcShortName : 建元资本
         * afcLongName : 建元资本有限公司
         * afcProvince :
         * afcCity :
         * afcRegion :
         * sv : G-177004-7818
         * svShortName : 北京看车
         * svLongName : 北京看车有限公司
         * svProvince : 北京
         * svCity : 北京市
         * svRegion : 东城区
         * sp : G-177004-8786
         * spShortName : 上海真格数码
         * spLongName : 上海真格数码有限公司
         * spProvince : 上海
         * spCity : 上海市
         * spRegion : 长宁区
         * dlr : G-177004-0153
         * dlrShortName : 上海申协日产
         * dlrLongName : 上海申协日产有限公司
         * dlrProvince : 上海
         * dlrCity : 上海市
         * dlrRegion : 长宁区
         * creationUserId : admin@177004
         * creationUserName :
         * startTime : 2017-06-12
         * endTime :
         * status : 正常监管
         * comment :
         * vinNo : LWVDA2045HB039415
         * carBrand : 广汽菲克
         * carSubBrand : 自由侠
         * carModel : 自由侠 2016款 1.4T 自动 动能版+
         * carPrice : 144444
         * totalKm :
         * evalCarPrice : 144444
         * price : 144,444.00
         * priceR : 144444
         * regDate :
         * usedCar : 0
         * loanId :
         * loanAmount :
         * loanStatus :
         * alertStatus :
         * rfDevCode :
         * rfInitCount :
         * rfAlertDismount :
         * rfAlertBattery :
         * rfAlertMsgTimeout :
         * rfLastMsg :
         * rfLastWarehouseId :
         * rfLastWarehouseName :
         * rfLat :
         * rfLng :
         * rfLatBaidu :
         * rfLngBaidu :
         * rfidId :
         * rfidLastScan :
         * rfidLastResult :
         * rfidLastWarehouseId :
         * rfidLastWarehouseName :
         * rfidLat :
         * rfidLng :
         * rfidLatBaidu :
         * rfidLngBaidu :
         * gpsDevId :
         * gpsSleepTime :
         * gpsAlertBattery :
         * gpsAlertDismount :
         * gpsLastMsg :
         * gpsLastMsgType :
         * gpsLastWarehouseId :
         * gpsLastWarehouseName :
         * gpsLat :
         * gpsLng :
         * gpsLatBaidu :
         * gpsLngBaidu :
         * appLastCheck :
         * appCheckUserId :
         * appCheckUserName :
         * appCheckResult :
         * appCheckTaskId :
         * docMonitorType :
         * docList :
         * docRfid :
         * docLastCheck :
         * docLastCheckMethod :
         * docStatus :
         * docLastAudit :
         * docAuditUserId :
         * docAuditUserName :
         * docAuditResult :
         * docAuditMethod :
         * docAuditTaskId :
         */

        private int id;
        private String source;
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
        private String creationUserId;
        private String creationUserName;
        private String startTime;
        private String endTime;
        private String status;
        private String comment;
        private String vinNo;
        private String carBrand;
        private String carSubBrand;
        private String carModel;
        private String carPrice;
        private String totalKm;
        private String evalCarPrice;
        private String price;
        private String priceR;
        private String regDate;
        private String usedCar;
        private String loanId;
        private String loanAmount;
        private String loanStatus;
        private String alertStatus;
        private String rfDevCode;
        private String rfInitCount;
        private String rfAlertDismount;
        private String rfAlertBattery;
        private String rfAlertMsgTimeout;
        private String rfLastMsg;
        private String rfLastWarehouseId;
        private String rfLastWarehouseName;
        private String rfLat;
        private String rfLng;
        private String rfLatBaidu;
        private String rfLngBaidu;
        private String rfidId;
        private String rfidLastScan;
        private String rfidLastResult;
        private String rfidLastWarehouseId;
        private String rfidLastWarehouseName;
        private String rfidLat;
        private String rfidLng;
        private String rfidLatBaidu;
        private String rfidLngBaidu;
        private String gpsDevId;
        private String gpsSleepTime;
        private String gpsAlertBattery;
        private String gpsAlertDismount;
        private String gpsLastMsg;
        private String gpsLastMsgType;
        private String gpsLastWarehouseId;
        private String gpsLastWarehouseName;
        private String gpsLat;
        private String gpsLng;
        private String gpsLatBaidu;
        private String gpsLngBaidu;
        private String appLastCheck;
        private String appCheckUserId;
        private String appCheckUserName;
        private String appCheckResult;
        private String appCheckTaskId;
        private String docMonitorType;
        private String docList;
        private String docRfid;
        private String docLastCheck;
        private String docLastCheckMethod;
        private String docStatus;
        private String docLastAudit;
        private String docAuditUserId;
        private String docAuditUserName;
        private String docAuditResult;
        private String docAuditMethod;
        private String docAuditTaskId;

        public void setId(int id) {
            this.id = id;
        }

        public void setSource(String source) {
            this.source = source;
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

        public void setCreationUserId(String creationUserId) {
            this.creationUserId = creationUserId;
        }

        public void setCreationUserName(String creationUserName) {
            this.creationUserName = creationUserName;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setVinNo(String vinNo) {
            this.vinNo = vinNo;
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

        public void setCarPrice(String carPrice) {
            this.carPrice = carPrice;
        }

        public void setTotalKm(String totalKm) {
            this.totalKm = totalKm;
        }

        public void setEvalCarPrice(String evalCarPrice) {
            this.evalCarPrice = evalCarPrice;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPriceR(String priceR) {
            this.priceR = priceR;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public void setUsedCar(String usedCar) {
            this.usedCar = usedCar;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public void setLoanAmount(String loanAmount) {
            this.loanAmount = loanAmount;
        }

        public void setLoanStatus(String loanStatus) {
            this.loanStatus = loanStatus;
        }

        public void setAlertStatus(String alertStatus) {
            this.alertStatus = alertStatus;
        }

        public void setRfDevCode(String rfDevCode) {
            this.rfDevCode = rfDevCode;
        }

        public void setRfInitCount(String rfInitCount) {
            this.rfInitCount = rfInitCount;
        }

        public void setRfAlertDismount(String rfAlertDismount) {
            this.rfAlertDismount = rfAlertDismount;
        }

        public void setRfAlertBattery(String rfAlertBattery) {
            this.rfAlertBattery = rfAlertBattery;
        }

        public void setRfAlertMsgTimeout(String rfAlertMsgTimeout) {
            this.rfAlertMsgTimeout = rfAlertMsgTimeout;
        }

        public void setRfLastMsg(String rfLastMsg) {
            this.rfLastMsg = rfLastMsg;
        }

        public void setRfLastWarehouseId(String rfLastWarehouseId) {
            this.rfLastWarehouseId = rfLastWarehouseId;
        }

        public void setRfLastWarehouseName(String rfLastWarehouseName) {
            this.rfLastWarehouseName = rfLastWarehouseName;
        }

        public void setRfLat(String rfLat) {
            this.rfLat = rfLat;
        }

        public void setRfLng(String rfLng) {
            this.rfLng = rfLng;
        }

        public void setRfLatBaidu(String rfLatBaidu) {
            this.rfLatBaidu = rfLatBaidu;
        }

        public void setRfLngBaidu(String rfLngBaidu) {
            this.rfLngBaidu = rfLngBaidu;
        }

        public void setRfidId(String rfidId) {
            this.rfidId = rfidId;
        }

        public void setRfidLastScan(String rfidLastScan) {
            this.rfidLastScan = rfidLastScan;
        }

        public void setRfidLastResult(String rfidLastResult) {
            this.rfidLastResult = rfidLastResult;
        }

        public void setRfidLastWarehouseId(String rfidLastWarehouseId) {
            this.rfidLastWarehouseId = rfidLastWarehouseId;
        }

        public void setRfidLastWarehouseName(String rfidLastWarehouseName) {
            this.rfidLastWarehouseName = rfidLastWarehouseName;
        }

        public void setRfidLat(String rfidLat) {
            this.rfidLat = rfidLat;
        }

        public void setRfidLng(String rfidLng) {
            this.rfidLng = rfidLng;
        }

        public void setRfidLatBaidu(String rfidLatBaidu) {
            this.rfidLatBaidu = rfidLatBaidu;
        }

        public void setRfidLngBaidu(String rfidLngBaidu) {
            this.rfidLngBaidu = rfidLngBaidu;
        }

        public void setGpsDevId(String gpsDevId) {
            this.gpsDevId = gpsDevId;
        }

        public void setGpsSleepTime(String gpsSleepTime) {
            this.gpsSleepTime = gpsSleepTime;
        }

        public void setGpsAlertBattery(String gpsAlertBattery) {
            this.gpsAlertBattery = gpsAlertBattery;
        }

        public void setGpsAlertDismount(String gpsAlertDismount) {
            this.gpsAlertDismount = gpsAlertDismount;
        }

        public void setGpsLastMsg(String gpsLastMsg) {
            this.gpsLastMsg = gpsLastMsg;
        }

        public void setGpsLastMsgType(String gpsLastMsgType) {
            this.gpsLastMsgType = gpsLastMsgType;
        }

        public void setGpsLastWarehouseId(String gpsLastWarehouseId) {
            this.gpsLastWarehouseId = gpsLastWarehouseId;
        }

        public void setGpsLastWarehouseName(String gpsLastWarehouseName) {
            this.gpsLastWarehouseName = gpsLastWarehouseName;
        }

        public void setGpsLat(String gpsLat) {
            this.gpsLat = gpsLat;
        }

        public void setGpsLng(String gpsLng) {
            this.gpsLng = gpsLng;
        }

        public void setGpsLatBaidu(String gpsLatBaidu) {
            this.gpsLatBaidu = gpsLatBaidu;
        }

        public void setGpsLngBaidu(String gpsLngBaidu) {
            this.gpsLngBaidu = gpsLngBaidu;
        }

        public void setAppLastCheck(String appLastCheck) {
            this.appLastCheck = appLastCheck;
        }

        public void setAppCheckUserId(String appCheckUserId) {
            this.appCheckUserId = appCheckUserId;
        }

        public void setAppCheckUserName(String appCheckUserName) {
            this.appCheckUserName = appCheckUserName;
        }

        public void setAppCheckResult(String appCheckResult) {
            this.appCheckResult = appCheckResult;
        }

        public void setAppCheckTaskId(String appCheckTaskId) {
            this.appCheckTaskId = appCheckTaskId;
        }

        public void setDocMonitorType(String docMonitorType) {
            this.docMonitorType = docMonitorType;
        }

        public void setDocList(String docList) {
            this.docList = docList;
        }

        public void setDocRfid(String docRfid) {
            this.docRfid = docRfid;
        }

        public void setDocLastCheck(String docLastCheck) {
            this.docLastCheck = docLastCheck;
        }

        public void setDocLastCheckMethod(String docLastCheckMethod) {
            this.docLastCheckMethod = docLastCheckMethod;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public void setDocLastAudit(String docLastAudit) {
            this.docLastAudit = docLastAudit;
        }

        public void setDocAuditUserId(String docAuditUserId) {
            this.docAuditUserId = docAuditUserId;
        }

        public void setDocAuditUserName(String docAuditUserName) {
            this.docAuditUserName = docAuditUserName;
        }

        public void setDocAuditResult(String docAuditResult) {
            this.docAuditResult = docAuditResult;
        }

        public void setDocAuditMethod(String docAuditMethod) {
            this.docAuditMethod = docAuditMethod;
        }

        public void setDocAuditTaskId(String docAuditTaskId) {
            this.docAuditTaskId = docAuditTaskId;
        }

        public int getId() {
            return id;
        }

        public String getSource() {
            return source;
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

        public String getCreationUserId() {
            return creationUserId;
        }

        public String getCreationUserName() {
            return creationUserName;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getStatus() {
            return status;
        }

        public String getComment() {
            return comment;
        }

        public String getVinNo() {
            return vinNo;
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

        public String getCarPrice() {
            return carPrice;
        }

        public String getTotalKm() {
            return totalKm;
        }

        public String getEvalCarPrice() {
            return evalCarPrice;
        }

        public String getPrice() {
            return price;
        }

        public String getPriceR() {
            return priceR;
        }

        public String getRegDate() {
            return regDate;
        }

        public String getUsedCar() {
            return usedCar;
        }

        public String getLoanId() {
            return loanId;
        }

        public String getLoanAmount() {
            return loanAmount;
        }

        public String getLoanStatus() {
            return loanStatus;
        }

        public String getAlertStatus() {
            return alertStatus;
        }

        public String getRfDevCode() {
            return rfDevCode;
        }

        public String getRfInitCount() {
            return rfInitCount;
        }

        public String getRfAlertDismount() {
            return rfAlertDismount;
        }

        public String getRfAlertBattery() {
            return rfAlertBattery;
        }

        public String getRfAlertMsgTimeout() {
            return rfAlertMsgTimeout;
        }

        public String getRfLastMsg() {
            return rfLastMsg;
        }

        public String getRfLastWarehouseId() {
            return rfLastWarehouseId;
        }

        public String getRfLastWarehouseName() {
            return rfLastWarehouseName;
        }

        public String getRfLat() {
            return rfLat;
        }

        public String getRfLng() {
            return rfLng;
        }

        public String getRfLatBaidu() {
            return rfLatBaidu;
        }

        public String getRfLngBaidu() {
            return rfLngBaidu;
        }

        public String getRfidId() {
            return rfidId;
        }

        public String getRfidLastScan() {
            return rfidLastScan;
        }

        public String getRfidLastResult() {
            return rfidLastResult;
        }

        public String getRfidLastWarehouseId() {
            return rfidLastWarehouseId;
        }

        public String getRfidLastWarehouseName() {
            return rfidLastWarehouseName;
        }

        public String getRfidLat() {
            return rfidLat;
        }

        public String getRfidLng() {
            return rfidLng;
        }

        public String getRfidLatBaidu() {
            return rfidLatBaidu;
        }

        public String getRfidLngBaidu() {
            return rfidLngBaidu;
        }

        public String getGpsDevId() {
            return gpsDevId;
        }

        public String getGpsSleepTime() {
            return gpsSleepTime;
        }

        public String getGpsAlertBattery() {
            return gpsAlertBattery;
        }

        public String getGpsAlertDismount() {
            return gpsAlertDismount;
        }

        public String getGpsLastMsg() {
            return gpsLastMsg;
        }

        public String getGpsLastMsgType() {
            return gpsLastMsgType;
        }

        public String getGpsLastWarehouseId() {
            return gpsLastWarehouseId;
        }

        public String getGpsLastWarehouseName() {
            return gpsLastWarehouseName;
        }

        public String getGpsLat() {
            return gpsLat;
        }

        public String getGpsLng() {
            return gpsLng;
        }

        public String getGpsLatBaidu() {
            return gpsLatBaidu;
        }

        public String getGpsLngBaidu() {
            return gpsLngBaidu;
        }

        public String getAppLastCheck() {
            return appLastCheck;
        }

        public String getAppCheckUserId() {
            return appCheckUserId;
        }

        public String getAppCheckUserName() {
            return appCheckUserName;
        }

        public String getAppCheckResult() {
            return appCheckResult;
        }

        public String getAppCheckTaskId() {
            return appCheckTaskId;
        }

        public String getDocMonitorType() {
            return docMonitorType;
        }

        public String getDocList() {
            return docList;
        }

        public String getDocRfid() {
            return docRfid;
        }

        public String getDocLastCheck() {
            return docLastCheck;
        }

        public String getDocLastCheckMethod() {
            return docLastCheckMethod;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public String getDocLastAudit() {
            return docLastAudit;
        }

        public String getDocAuditUserId() {
            return docAuditUserId;
        }

        public String getDocAuditUserName() {
            return docAuditUserName;
        }

        public String getDocAuditResult() {
            return docAuditResult;
        }

        public String getDocAuditMethod() {
            return docAuditMethod;
        }

        public String getDocAuditTaskId() {
            return docAuditTaskId;
        }

        @Override
        public String toString() {
            return "ItemsEntity{" +
                    "id=" + id +
                    ", source='" + source + '\'' +
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
                    ", creationUserId='" + creationUserId + '\'' +
                    ", creationUserName='" + creationUserName + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", status='" + status + '\'' +
                    ", comment='" + comment + '\'' +
                    ", vinNo='" + vinNo + '\'' +
                    ", carBrand='" + carBrand + '\'' +
                    ", carSubBrand='" + carSubBrand + '\'' +
                    ", carModel='" + carModel + '\'' +
                    ", carPrice='" + carPrice + '\'' +
                    ", totalKm='" + totalKm + '\'' +
                    ", evalCarPrice='" + evalCarPrice + '\'' +
                    ", price='" + price + '\'' +
                    ", priceR='" + priceR + '\'' +
                    ", regDate='" + regDate + '\'' +
                    ", usedCar='" + usedCar + '\'' +
                    ", loanId='" + loanId + '\'' +
                    ", loanAmount='" + loanAmount + '\'' +
                    ", loanStatus='" + loanStatus + '\'' +
                    ", alertStatus='" + alertStatus + '\'' +
                    ", rfDevCode='" + rfDevCode + '\'' +
                    ", rfInitCount='" + rfInitCount + '\'' +
                    ", rfAlertDismount='" + rfAlertDismount + '\'' +
                    ", rfAlertBattery='" + rfAlertBattery + '\'' +
                    ", rfAlertMsgTimeout='" + rfAlertMsgTimeout + '\'' +
                    ", rfLastMsg='" + rfLastMsg + '\'' +
                    ", rfLastWarehouseId='" + rfLastWarehouseId + '\'' +
                    ", rfLastWarehouseName='" + rfLastWarehouseName + '\'' +
                    ", rfLat='" + rfLat + '\'' +
                    ", rfLng='" + rfLng + '\'' +
                    ", rfLatBaidu='" + rfLatBaidu + '\'' +
                    ", rfLngBaidu='" + rfLngBaidu + '\'' +
                    ", rfidId='" + rfidId + '\'' +
                    ", rfidLastScan='" + rfidLastScan + '\'' +
                    ", rfidLastResult='" + rfidLastResult + '\'' +
                    ", rfidLastWarehouseId='" + rfidLastWarehouseId + '\'' +
                    ", rfidLastWarehouseName='" + rfidLastWarehouseName + '\'' +
                    ", rfidLat='" + rfidLat + '\'' +
                    ", rfidLng='" + rfidLng + '\'' +
                    ", rfidLatBaidu='" + rfidLatBaidu + '\'' +
                    ", rfidLngBaidu='" + rfidLngBaidu + '\'' +
                    ", gpsDevId='" + gpsDevId + '\'' +
                    ", gpsSleepTime='" + gpsSleepTime + '\'' +
                    ", gpsAlertBattery='" + gpsAlertBattery + '\'' +
                    ", gpsAlertDismount='" + gpsAlertDismount + '\'' +
                    ", gpsLastMsg='" + gpsLastMsg + '\'' +
                    ", gpsLastMsgType='" + gpsLastMsgType + '\'' +
                    ", gpsLastWarehouseId='" + gpsLastWarehouseId + '\'' +
                    ", gpsLastWarehouseName='" + gpsLastWarehouseName + '\'' +
                    ", gpsLat='" + gpsLat + '\'' +
                    ", gpsLng='" + gpsLng + '\'' +
                    ", gpsLatBaidu='" + gpsLatBaidu + '\'' +
                    ", gpsLngBaidu='" + gpsLngBaidu + '\'' +
                    ", appLastCheck='" + appLastCheck + '\'' +
                    ", appCheckUserId='" + appCheckUserId + '\'' +
                    ", appCheckUserName='" + appCheckUserName + '\'' +
                    ", appCheckResult='" + appCheckResult + '\'' +
                    ", appCheckTaskId='" + appCheckTaskId + '\'' +
                    ", docMonitorType='" + docMonitorType + '\'' +
                    ", docList='" + docList + '\'' +
                    ", docRfid='" + docRfid + '\'' +
                    ", docLastCheck='" + docLastCheck + '\'' +
                    ", docLastCheckMethod='" + docLastCheckMethod + '\'' +
                    ", docStatus='" + docStatus + '\'' +
                    ", docLastAudit='" + docLastAudit + '\'' +
                    ", docAuditUserId='" + docAuditUserId + '\'' +
                    ", docAuditUserName='" + docAuditUserName + '\'' +
                    ", docAuditResult='" + docAuditResult + '\'' +
                    ", docAuditMethod='" + docAuditMethod + '\'' +
                    ", docAuditTaskId='" + docAuditTaskId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SupervicerDetailsBean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", total=" + total +
                ", items=" + items +
                '}';
    }
    public static SupervicerDetailsBean getSupervicerDetailsBean(String result){
        SupervicerDetailsBean bean=null;
        try{
            bean=new Gson().fromJson(result,new TypeToken<SupervicerDetailsBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }
}
