package com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams;

import android.content.Context;

import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.AjaxParams;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;

/**
 * Created by 左丽姬 on 2017/4/10.
 */

public class  HttpUser {
    private static Context context;
    private static HttpUser httpUser;
    private static MyAjaxParams myParams;
    public static HttpUser getIntence(Context context){
        HttpUser.context =context;
        HttpUser.myParams=new MyAjaxParams(context);
        if (httpUser == null)
            httpUser = new HttpUser();
        return httpUser;
    }


    /**
     * 发送短信、语音验证码-登录用
     * @param action 短信验证：IBaseUrl.ACTION_LOGIN_SMS 或  语音验证：IBaseUrl.ACTION_LOGIN_VOICE
     * @param userId 用户名/邮箱号
     * @param callBack 回调函数
     */
    public void sendLoginMobileCode(String action, String userId, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(userId)){
            IBaseMethod.showToast(context,"请填写用户名", IBase.RETAIL_TWO);
            return ;
        }
        AjaxParams params = myParams.loginSendMobileCode(action, userId);
        IBaseMethod.post(context, IBaseUrl.USER, params, callBack);
    }

    /**
     * 发送短信、语音验证码-注册用
     * @param action 短信验证：IBaseUrl.ACTION_REGISTER_SMS 或  语音验证：IBaseUrl.ACTION_REGISTER_VOICE
     * @param mobile 手机号
     * @param mc 校验码
     *  @param email 选填参数，如果填写了会发送一个注册的邮件给用户以防止运营商发送短信失败时用户无法接口到短信而无法注册
     * @param callBack 回调函数
     */
    public void sendRegisterMobileCode(String action, String mobile, String mc, String email, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(mc)){
            IBaseMethod.showToast(context,"mc值为空", IBase.RETAIL_TWO);
            if(callBack != null){
                callBack.onFailure(1, "mc值为空");
            }
            return;
        }
        if (GeneralUtils.isEmpty(mobile)){
            IBaseMethod.showToast(context,"请填写手机号", IBase.RETAIL_TWO);
            if(callBack != null){
                callBack.onFailure(1, "请填写手机号");
            }
            return ;
        }
        AjaxParams params = myParams.registerSendMobileCode(action, mobile, mc, email);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /** 忘记密码 */
    /**
     * 忘记密码 - 验证用户是否存在
     * @param userId 手机号或邮箱
     * @param callBack 回调函数
     */
    public void userIsExsit(String userId, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(userId)){
            IBaseMethod.showToast(context,"请填写用户名", IBase.RETAIL_TWO);
            return ;
        }
        AjaxParams params = myParams.userIsExist(userId);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /**
     * 发送短信验证码-忘记密码用
     * @param mobile 手机号
     * @param mc 校验码
     * @param  callBack 回调函数
     */
    public void sendForgetMobileCode(String mobile, String mc, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(mobile)){
            IBaseMethod.showToast(context,"请填写手机号", IBase.RETAIL_TWO);
            if (callBack != null){
                callBack.onFailure(1, "请填写手机号");
            }
            return ;
        }
        if (GeneralUtils.isEmpty(mc)){
            IBaseMethod.showToast(context,"mc值为空", IBase.RETAIL_TWO);
            if (callBack != null){
                callBack.onFailure(1, "mc值为空");
            }
            return ;
        }
        AjaxParams params = myParams.forgetSendMobileCode(mobile, mc);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /**
     * 忘记密码 - 验证短信验证码是否正确
     * @param mobile 手机号
     * @param mobileCode 验证码
     * @param callBack 回调函数
     */
    public void validateForgetMobileCode(String mobile, String mobileCode, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(mobile)){
            IBaseMethod.showToast(context,"请填写手机号", IBase.RETAIL_TWO);
            return ;
        }
        if (GeneralUtils.isEmpty(mobileCode)){
            IBaseMethod.showToast(context,"请填写验证码", IBase.RETAIL_TWO);
            return ;
        }
        AjaxParams params = myParams.forgetValidateMoblieCode(mobile, mobileCode);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /**
     * 发送邮件验证码-忘记密码用
     * @param email 邮箱
     * @param callBack 回调函数
     */
    public void sendForgetEmailCode(String email, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(email)){
            IBaseMethod.showToast(context,"请填写邮箱", IBase.RETAIL_TWO);
            return;
        }
        AjaxParams params = myParams.forgetSendEmailCode(email);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /**
     * 忘记密码 - 验证邮件验证码是否正确
     * @param email 邮箱
     * @param emailCode 验证码
     * @param callBack 回调函数
     */
    public void validateForgetEmailCode(String email, String emailCode, final CallBack<Object> callBack){
        if (GeneralUtils.isEmpty(email)){
            IBaseMethod.showToast(context,"请填写邮箱", IBase.RETAIL_TWO);
            return ;
        }
        if (GeneralUtils.isEmpty(emailCode)){
            IBaseMethod.showToast(context,"请填写验证码", IBase.RETAIL_TWO);
            return ;
        }
        AjaxParams params = myParams.forgetValidateMoblieCode(email, emailCode);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

    /**
     * 获取发送短信时的校验码
     * @param callBack 回调函数
     */
    public void getMoblieCode(CallBack<Object> callBack){
        AjaxParams params = myParams.getMobileCode();
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }


    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param callBack 回调函数
     */
    public void updatePassword(String oldPassword, String newPassword, String newPassword2, CallBack<Object> callBack){
        AjaxParams params = myParams.updatePassword(oldPassword, newPassword, newPassword2);
        IBaseMethod.post(context,  IBaseUrl.USER, params, callBack);
    }

}
