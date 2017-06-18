package com.kaopujinfu.appsys.thecar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogItemListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.ShowPasswordListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpUser;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;

import net.tsz.afinal.FinalDb;

/**
 * 登录页面
 * Created by zuoliji on 2017/3/28.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username_login, userpass_login, usercode_login;
    private Button goto_login, register_login, forget_login, verificationcode_login;
    private CheckBox userpassshow_login;
    private RelativeLayout usercodelayour_login;
    private LoadingDialog dialog;
    private FinalDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IBaseMethod.setBarStyle(this, Color.TRANSPARENT);
        db = FinalDb.create(this, IBase.BASE_DATE);
        initLogin();
        autoLogin();
    }

    private void autoLogin() {
        Long currentTime = System.currentTimeMillis();
        Long loginTime = (Long) SPUtils.get(LoginActivity.this, "currentLoginTime", currentTime);
        if (currentTime - loginTime == 0 || currentTime - loginTime >= 24 * 60 * 60 * 1000) { // 24 小时
            // 需要手动登录
        } else {
            // 自动登录
            String userName = (String) SPUtils.get(LoginActivity.this, "login_name", "");
            String userPass = (String) SPUtils.get(LoginActivity.this, "login_user_password", "");
            if (!GeneralUtils.isEmpty(userName) && !GeneralUtils.isEmpty(userPass)) {
                // 用户名和密码不为空的时候进行登录
                userpass_login.setText(userPass);
                login(userName, userPass, true);
            }
        }
    }

    private void initLogin() {
        username_login = (EditText) findViewById(R.id.username_login);
        //判断是否是登录账户
        String login_naem = SPUtils.get(this, "login_name", "").toString();
        if (!GeneralUtils.isEmpty(login_naem)) {
            username_login.setText(login_naem);
        }
        userpass_login = (EditText) findViewById(R.id.userpass_login);
        goto_login = (Button) findViewById(R.id.goto_login);
        register_login = (Button) findViewById(R.id.register_login);
        forget_login = (Button) findViewById(R.id.forget_login);
        usercodelayour_login = (RelativeLayout) findViewById(R.id.usercodelayour_login);
        usercode_login = (EditText) findViewById(R.id.usercode_login);
        verificationcode_login = (Button) findViewById(R.id.verificationcode_login);
        userpassshow_login = (CheckBox) findViewById(R.id.userpassshow_login);

        forget_login.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        goto_login.setOnClickListener(this);
        register_login.setOnClickListener(this);
        forget_login.setOnClickListener(this);
        verificationcode_login.setOnClickListener(this);
        userpassshow_login.setOnCheckedChangeListener(new ShowPasswordListener(userpass_login));

        dialog = new LoadingDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.goto_login) {//登录
            String username = username_login.getText().toString();
            SPUtils.put(this, "login_name", username);
            String userpass = userpass_login.getText().toString();
            login(username, userpass, false);
        } else if (i == R.id.verificationcode_login) {// 获取验证码
            final String uname = username_login.getText().toString();
            DialogUtil.verificationCode(this, new String[]{"短信验证码", "语音验证码"}, new DialogItemListener() {
                @Override
                public void itemListener(int position) {
                    String action = position == 0 ? IBaseUrl.ACTION_LOGIN_SMS : IBaseUrl.ACTION_LOGIN_VOICE;
                    HttpUser.getIntence(LoginActivity.this).sendLoginMobileCode(action, uname, new CallBack<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            Result result = Result.getMcJson(o.toString());
                            if (result.isSuccess()) {
                                IBaseMethod.showToast(LoginActivity.this, "验证码发送成功！", IBase.RETAIL_ONE);
                            }
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {

                        }
                    });
                }
            });

        }
    }

    /**
     * 更换用户登录清除当前所有贷款申请数据
     */
//    private void clearLenderInfo() {
//        List<LenderInfor> infors = db.findAll(LenderInfor.class);
//        if (infors != null && infors.size() > 0) {
//            if (!IBase.USERID.equals(infors.get(0).getUserId_db())) {
//                db.deleteAll(LenderInfor.class);
//            }
//        }
//    }
    public void login(String userName, final String userPass, final boolean isAuto) {
        dialog.show();
        dialog.setLoadingTitle("正在登录...");
        HttpBank.getIntence(this).login(userName, userPass, null, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("登录:" + o.toString());
                Loginbean result = Loginbean.getLoginbean(o.toString());
                dialog.dismiss();
                if (result.isSuccess()) {
                    /** 保存数据 */
                    IBase.USERID = result.getUser_id();
                    IBase.SESSIONID = result.getS_id();
                    SPUtils.put(LoginActivity.this,"loginUser",o.toString());
                    SPUtils.put(LoginActivity.this, "login_user_id", IBase.USERID);
                    SPUtils.put(LoginActivity.this, "login_s_id", IBase.SESSIONID);
//                    clearLenderInfo();
                    if (!isAuto) {
                        SPUtils.put(LoginActivity.this, "login_user_password", userPass);
                        SPUtils.put(LoginActivity.this, "currentLoginTime", System.currentTimeMillis());
                    }
                    if (!GeneralUtils.isEmpty(result.getCompanyCode()))
                        IBase.COMPANY_CODE = result.getCompanyCode();
                    if (GeneralUtils.isEmpty(result.getName()))
                        IBase.user.setCompanyName(result.getName());
                    SPUtils.put(LoginActivity.this, "companyCode", IBase.COMPANY_CODE);
                    SPUtils.put(LoginActivity.this, "uploadToken", result.getUpload_token());
                    SPUtils.put(LoginActivity.this, "currentUploadTime", System.currentTimeMillis());
                    Intent intent = new Intent(LoginActivity.this, CarMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if ("NEED_MOBILE_CHECK".equals(result.getComment())) {
                        // 需要验证码
                        usercodelayour_login.setVisibility(View.VISIBLE);
                        IBaseMethod.showToast(LoginActivity.this, "此次登录IP不是常用的IP，需要发送短信或语音验证码来验证登录", IBase.RETAIL_ZERO);
                    } else {
                        // 登录失败
                        IBaseMethod.showToast(LoginActivity.this, result.getComment(), IBase.RETAIL_ZERO);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_TWO)
                    IBaseMethod.showToast(LoginActivity.this, strMsg, IBase.RETAIL_TWO);
            }
        });
    }

    /* 设置项目服务器的IP地址 */
    public void settingIp(View view) {
        DialogUtil.updateIPDialog(this);
    }

}
