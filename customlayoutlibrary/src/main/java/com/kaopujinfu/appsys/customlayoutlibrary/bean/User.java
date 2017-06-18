package com.kaopujinfu.appsys.customlayoutlibrary.bean;

/**
 * 用户
 * Created by Doris on 2017/4/10.
 */

public class User {

    /** 用户ID */
    private String user_id;
    /** 用户密码 */
    private String password;
    /** 用户名 */
    private String name;
    /** 身份证号 */
    private String idcard;
    /** 公司名称 */
    private String companyName;
    /** 邮箱 */
    private String email;
    /** 手机号 */
    private String mobile;
    /** 手机验证码 */
    private String mobile_code;
    /** 邮箱验证码 */
    private String email_code;
    /** 头像 */
    private String headImg;
    /** 真实头像 */
    private String realHeadImg;
    /** 用户最后登录时间 */
    private String lastLogin;
    /** 用户昵称 */
    private String nickName;
    /** 用户编号 */
    private int UNumber;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_code() {
        return mobile_code;
    }

    public void setMobile_code(String mobile_code) {
        this.mobile_code = mobile_code;
    }

    public String getEmail_code() {
        return email_code;
    }

    public void setEmail_code(String email_code) {
        this.email_code = email_code;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRealHeadImg() {
        return realHeadImg;
    }

    public void setRealHeadImg(String realHeadImg) {
        this.realHeadImg = realHeadImg;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUNumber() {
        return UNumber;
    }

    public void setUNumber(int UNumber) {
        this.UNumber = UNumber;
    }
}
