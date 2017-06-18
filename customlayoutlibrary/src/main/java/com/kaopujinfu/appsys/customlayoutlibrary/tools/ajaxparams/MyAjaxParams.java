package com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams;

import android.content.Context;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.User;
import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.AjaxParams;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

import java.util.Date;

/**
 * post提交参数
 * Created by zuoliji on 2017/4/9.
 */

public class MyAjaxParams {

    private Context mContext;

    public MyAjaxParams(Context context) {
        mContext = context;
    }


    /**
     * 登录 - 发送验证码接口参数
     *
     * @param action 短信验证：IBaseUrl.ACTION_LOGIN_SMS 或  语音验证：IBaseUrl.ACTION_LOGIN_VOICE
     * @param userId 用户名/邮箱号
     * @return
     */
    public AjaxParams loginSendMobileCode(String action, String userId) {
        AjaxParams params = new AjaxParams();
        params.put("_action", action);
        params.put("user_id", userId);
        return params;
    }

    /**
     * 注册接口参数
     *
     * @param user 用户
     * @return
     */
    public AjaxParams register(User user) {
        AjaxParams params = new AjaxParams();
        params.put("_action", "REGISTER"); // 方法名，固定值：REGISTER
        params.put("name", user.getName()); // 用户名，手机号或邮箱地址
        params.put("idcard", user.getIdcard()); //身份证号
        params.put("companyName", user.getCompanyName()); // 公司名
        params.put("email", user.getEmail()); // 邮箱
        params.put("password", user.getPassword()); // 密码
        params.put("mobile", user.getMobile()); // 手机号
        params.put("mobile_code", user.getMobile_code()); // 验证码
        return params;
    }

    /**
     * 注册 - 发送验证码接口参数
     *
     * @param action 短信验证：IBaseUrl.ACTION_REGISTER_SMS 或  语音验证：IBaseUrl.ACTION_REGISTER_VOICE
     * @param mobile 用户名/邮箱号
     * @param mc     校验码
     * @param email
     * @return
     */
    public AjaxParams registerSendMobileCode(String action, String mobile, String mc, String email) {
        AjaxParams params = new AjaxParams();
        params.put("_action", action);
        params.put("mobile", mobile);
        // 在请求图形验证码时后台传来的mc值
        params.put("mc", mc);
        if (GeneralUtils.isEmpty(email)) {
            params.put("email", email); // 选填
        }
        return params;
    }

    /**
     * 忘记密码 - 验证用户是否存在 接口参数
     *
     * @param userId 手机号或邮箱
     * @return
     */
    public AjaxParams userIsExist(String userId) {
        AjaxParams params = new AjaxParams();
        params.put("_action", "RESET_PWD_IS_EXISTS"); // 方法名，固定值：RESET_PWD_IS_EXISTS
        params.put("user_id", userId);
        return params;
    }

    /**
     * 忘记密码 - 发送验证码接口参数
     *
     * @param mobile 手机号
     * @param mc     校验码
     * @return
     */
    public AjaxParams forgetSendMobileCode(String mobile, String mc) {
        AjaxParams params = new AjaxParams();
        // 方法名，固定值：SEND_RESETPWD_MOBILE_CODE_SMS
        params.put("_action", "SEND_RESETPWD_MOBILE_CODE_SMS");
        params.put("user_id", mobile);
        // 在请求图形验证码时后台传来的mc值
        params.put("mc", mc);
        return params;
    }

    /**
     * 忘记密码 - 验证短信验证码是否正确 接口参数
     *
     * @param mobile     手机号
     * @param mobileCode 验证码
     * @return
     */
    public AjaxParams forgetValidateMoblieCode(String mobile, String mobileCode) {
        AjaxParams params = new AjaxParams();
        // 方法名，固定值：RESET_PWD_MOBILE
        params.put("_action", "RESET_PWD_MOBILE");
        params.put("mobile", mobile);
        params.put("user_id", mobile);
        params.put("mobile_code", mobileCode);
        return params;
    }

    /**
     * 忘记密码 - 发送邮件验证码接口参数
     *
     * @param email 邮箱
     * @return
     */
    public AjaxParams forgetSendEmailCode(String email) {
        AjaxParams params = new AjaxParams();
        // 方法名，固定值：SEND_RESET_PWD_EMAIL
        params.put("_action", "SEND_RESET_PWD_EMAIL");
        params.put("email", email);
        return params;
    }


    /**
     * 忘记密码 - 设置新密码 接口参数
     *
     * @param user 用户
     * @return
     */
    public AjaxParams forgetNewPassword(User user) {
        AjaxParams params = new AjaxParams();
        // 方法名，固定值：RESET_PWD_NEW
        params.put("_action", "RESET_PWD_NEW");
        if (GeneralUtils.isEmpty(user.getEmail_code())) {
            params.put("user_id", user.getMobile()); // 用户ID (手机号或邮箱)
            params.put("mobile_code", user.getMobile_code()); // 手机验证码　选填
        } else {
            params.put("user_id", user.getEmail()); // 用户ID (手机号或邮箱)
            params.put("email_code", user.getEmail_code()); // 邮箱验证码　选填　两个验证码不能同时为空
        }
        params.put("new_password", user.getPassword()); // 新密码
        return params;
    }

    /**
     * 获取发送短信时的校验码接口参数
     *
     * @return
     */
    public AjaxParams getMobileCode() {
        AjaxParams params = new AjaxParams();
        params.put("_action", "GETMC_RESOURCES"); // 方法名，固定值：GETMC_RESOURCES
        String kmp = "that";
        int rnum = IBaseMethod.getRandomNumber(3, 1000);
        String tm = DateUtil.format(new Date(), "yyyyMMddHHmm");
        String chcode;
        if (rnum % 2 == 0) {
            chcode = tm + "kao-" + tm + kmp + "-pujinfu-google" + (rnum / 2);
        } else {
            chcode = tm + "kao-" + kmp + tm + "-pujinfu-google" + rnum;
        }
        // MD5加密
        chcode = IBaseMethod.getMD5(chcode);
        params.put("kmp", kmp); // 请求来源，固定值：ios:this、android:that
        params.put("rnum", rnum + ""); //随机数，3到1000之间的正整数且大于等于3
        params.put("tm", tm); // 时间戳，格式：201704101427
        params.put("chcode", chcode); // 根据特定规则生成的MD5值
        return params;
    }


    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param newPassword2
     * @return
     */
    public AjaxParams updatePassword(String oldPassword, String newPassword, String newPassword2) {
        AjaxParams params = UserIDSIDCompany();
        params.put("_action", "MODIFY_PWD"); // 方法名，固定值：MODIFY_PWD
        params.put("old_password", oldPassword); // 旧密码
        params.put("new_password", newPassword); // 新密码
        params.put("new_password2", newPassword2); // 新密码重复
        return params;
    }



    /**
     * 设置登录用户，所属session ，公司code参数
     *
     * @return AjaxParams
     */
    public AjaxParams UserIDSIDCompany() {
        AjaxParams params = UserIDSID();
        params.put("mobile.companyCode", IBase.COMPANY_CODE);//用户所属公司CODE
        return params;
    }

    /**
     * 设置登录用户，所属session
     *
     * @return AjaxParams
     */
    public AjaxParams UserIDSID() {
        AjaxParams params = new AjaxParams();
        IBaseMethod.setBaseInfo(mContext);
        params.put("kaopu.login_user_id", IBase.USERID);//登录的用户ID
        params.put("kaopu.login_s_id", IBase.SESSIONID);//登录用户所属sessionID
        return params;
    }

}
