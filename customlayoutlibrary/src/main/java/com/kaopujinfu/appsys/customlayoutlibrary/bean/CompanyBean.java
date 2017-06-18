package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.io.Serializable;

/**
 * 公司
 * Created by 左丽姬 on 2017/4/14.
 */

public class CompanyBean implements Serializable{


    /**
     * id : 12
     * companyCode : 17010141
     * province : 北京
     * city : 北京市
     * region : 朝阳区
     * shortName : 靠谱金服
     * name : 北京靠谱金服
     * type : 服务商
     * officeAddr : 妓房乱动事件
     * regCode : 范德萨发生
     * orgCode : 佛挡杀佛
     * regAddr : fdsf
     * regPerson : fdsfsd
     * regAmount : 600
     * regDate : 2017-01-01 00:00:00
     * periodStart : 2017-01-01 00:00:00
     * periodEnd : 2017-01-31 00:00:00
     * bizRange : fdsfsf
     * website : 家乐福的教室里
     * phone : 解放路的世界里放假了
     * email : 家里附近的实力金服
     * comment : 房间里的世界里发生
     * docImg : \DATA\COMPANY\20170109\20170109105135_UVAY.JPG
     * appTime :
     * appUserId :
     * creationTime : 2017-01-09 10:51:35
     * creationUserId : U161152257
     * isValid : 1
     */

    private int id;
    private String companyCode;
    private String province;
    private String city;
    private String region;
    private String shortName;
    private String name;
    private String type;
    private String officeAddr;
    private String regCode;
    private String orgCode;
    private String regAddr;
    private String regPerson;
    private int regAmount;
    private String regDate;
    private String periodStart;
    private String periodEnd;
    private String bizRange;
    private String website;
    private String phone;
    private String email;
    private String comment;
    private String docImg;
    private String appTime;
    private String appUserId;
    private String creationTime;
    private String creationUserId;
    private int isValid;

    public CompanyBean() {
    }

    public CompanyBean(int id, String companyCode, String province, String city, String region, String shortName, String name, String type, String officeAddr, String regCode, String orgCode, String regAddr, String regPerson, int regAmount, String regDate, String periodStart, String periodEnd, String bizRange, String website, String phone, String email, String comment, String docImg, String appTime, String appUserId, String creationTime, String creationUserId, int isValid) {
        this.id = id;
        this.companyCode = companyCode;
        this.province = province;
        this.city = city;
        this.region = region;
        this.shortName = shortName;
        this.name = name;
        this.type = type;
        this.officeAddr = officeAddr;
        this.regCode = regCode;
        this.orgCode = orgCode;
        this.regAddr = regAddr;
        this.regPerson = regPerson;
        this.regAmount = regAmount;
        this.regDate = regDate;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.bizRange = bizRange;
        this.website = website;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
        this.docImg = docImg;
        this.appTime = appTime;
        this.appUserId = appUserId;
        this.creationTime = creationTime;
        this.creationUserId = creationUserId;
        this.isValid = isValid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public void setRegPerson(String regPerson) {
        this.regPerson = regPerson;
    }

    public void setRegAmount(int regAmount) {
        this.regAmount = regAmount;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public void setBizRange(String bizRange) {
        this.bizRange = bizRange;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDocImg(String docImg) {
        this.docImg = docImg;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setCreationUserId(String creationUserId) {
        this.creationUserId = creationUserId;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getId() {
        return id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getOfficeAddr() {
        return officeAddr;
    }

    public String getRegCode() {
        return regCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public String getRegPerson() {
        return regPerson;
    }

    public int getRegAmount() {
        return regAmount;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public String getBizRange() {
        return bizRange;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getComment() {
        return comment;
    }

    public String getDocImg() {
        return docImg;
    }

    public String getAppTime() {
        return appTime;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getCreationUserId() {
        return creationUserId;
    }

    public int getIsValid() {
        return isValid;
    }
    /**
    * 获取公司资料
     * @param
    * */
    public static CompanyBean getCompanyBean(String result){
        CompanyBean companyBean=null;
        LogUtils.debug("公司解析数据:"+result);
        try{
            companyBean=new Gson().fromJson(result,new TypeToken<CompanyBean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.debug("公司解析错误");
        }
        return companyBean;
    }
}
