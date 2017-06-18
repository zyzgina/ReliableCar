package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

/**
 * http请求成功返回值
 * Created by Doris on 2017/4/11.
 */

public class Result {

    /**
     * SUCCESS代表成功，FAIL代表失败
     */
    private String result;
    /**
     * true,代表成功，false代表失败
     */
    private boolean success;
    /**
     * 处理结果说明
     */
    private String comment;

    /**
     * 获取验证码时用的mc
     */
    private String mc;

    /** 用户个人信息 */
    /**
     * 登录用户ID
     */
    private String login_user_id;
    /**
     * 登录session
     */
    private String login_s_id;
    /**
     * 公司信息
     */
    private CompanyBean companyBean;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户Email
     */
    private String email;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 用户身份证号
     */
    private String idcard;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * 用户真实头像
     */
    private String realHeadImg;
    /**
     * 用户最后登录时间
     */
    private String lastLogin;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户编号
     */
    private int UNumber;

    /**
     * 行业资讯详情已经阅读次数增加
     */
    private String articleContent;

    public Result() {
    }

    public Result(String result, boolean success, String comment, String mc) {
        this.result = result;
        this.success = success;
        this.comment = comment;
        this.mc = mc;
    }

    public Result(String result, boolean success, String comment, String login_user_id, String login_s_id) {
        this.result = result;
        this.success = success;
        this.comment = comment;
        this.login_user_id = login_user_id;
        this.login_s_id = login_s_id;
    }

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

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getLogin_user_id() {
        return login_user_id;
    }

    public void setLogin_user_id(String login_user_id) {
        this.login_user_id = login_user_id;
    }

    public String getLogin_s_id() {
        return login_s_id;
    }

    public void setLogin_s_id(String login_s_id) {
        this.login_s_id = login_s_id;
    }

    public CompanyBean getCompanyBean() {
        return companyBean;
    }

    public void setCompanyBean(CompanyBean companyBean) {
        this.companyBean = companyBean;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                ", mc='" + mc + '\'' +
                ", login_user_id='" + login_user_id + '\'' +
                ", login_s_id='" + login_s_id + '\'' +
                ", companyBean=" + companyBean +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idcard='" + idcard + '\'' +
                ", headImg='" + headImg + '\'' +
                ", realHeadImg='" + realHeadImg + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", nickName='" + nickName + '\'' +
                ", UNumber=" + UNumber +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }

    /**
     * mc解析
     */
    public static Result getMcJson(String result) {
        Result resultBean = null;
        try {
            resultBean = new Gson().fromJson(result, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug("登录解析出错");
        }
        return resultBean;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
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
