package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

/**
 * Describe: 登录解析
 * Created Author: Gina
 * Created Date: 2017/6/12.
 */

public class Loginbean  implements Serializable{

    /**
     * result : SUCCESS
     * success : true
     * comment : 登录成功
     * user_id : jingxiaoshang@123456
     * name : 经销商
     * companyCode : G-123456-1101
     * role : DLR_ADMIN
     * email :
     * mobile : 15173150523
     * s_id : E5F3B5841CC540D2805CF5FDBFD67579
     * expire_time : 2017-06-13 18:50:50
     * expire_seconds : 86400
     * upload_token : wI1XAFCkN2OC2Ty28BouznH8HJEQvB429QQWpZWB:g8zxxkeh5VwWVU_RSLMsQt4zQS4=:eyJzY29wZSI6ImZwcmlzayIsImRlYWRsaW5lIjoxNDk3NDM3MzE1fQ==
     */

    private String result;
    private boolean success;
    private String comment;
    private String user_id;
    private String name;
    private String head_img;
    private String companyCode;
    private String company_logo;
    private String role;
    private String email;
    private String mobile;
    private String s_id;
    private String expire_time;
    private String expire_seconds;
    private String upload_token;
    /**
     * companyShortName : 上海测试团队
     * companyLongName : 上海测试监管团队
     * roleName : SP_ADMIN
     */

    private String companyShortName;
    private String companyLongName;
    private String roleName;

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public void setExpire_seconds(String expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public void setUpload_token(String upload_token) {
        this.upload_token = upload_token;
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

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getS_id() {
        return s_id;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public String getExpire_seconds() {
        return expire_seconds;
    }

    public String getUpload_token() {
        return upload_token;
    }

    @Override
    public String toString() {
        return "Loginbean{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", head_img='" + head_img + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", company_logo='" + company_logo + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", s_id='" + s_id + '\'' +
                ", expire_time='" + expire_time + '\'' +
                ", expire_seconds='" + expire_seconds + '\'' +
                ", upload_token='" + upload_token + '\'' +
                ", companyShortName='" + companyShortName + '\'' +
                ", companyLongName='" + companyLongName + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public static Loginbean getLoginbean(String result){
        Loginbean loginbean=null;
        try{
            loginbean=new Gson().fromJson(result,new TypeToken<Loginbean>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginbean;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyLongName() {
        return companyLongName;
    }

    public void setCompanyLongName(String companyLongName) {
        this.companyLongName = companyLongName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
