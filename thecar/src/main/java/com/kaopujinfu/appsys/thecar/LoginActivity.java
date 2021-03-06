package com.kaopujinfu.appsys.thecar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.ShowPasswordListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.PermissionsUntils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.IMMListenerRelativeLayout;

import net.tsz.afinal.FinalDb;

import static com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils.get;

/**
 * 登录页面
 * Created by zuoliji on 2017/3/28.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username_login, userpass_login;
    private Button goto_login, register_login, forget_login;
    private CheckBox userpassshow_login;
    private LoadingDialog dialog;
    private FinalDb db;
    private IMMListenerRelativeLayout keyLogin;
    private RelativeLayout ContentRl;
    private ImageView logo_login;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    RelativeLayout showKeyLog;
    RelativeLayout ipLinear;
    EditText ip_login;
    boolean flag = false;
    private LinearLayout addrssLinear;
    private CheckBox ipshow_login;
    private TextView testTv, productionTv;
    private FrameLayout testAddress, productionAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.transparent));
        db = FinalDb.create(this, IBase.BASE_DATE);
        showKeyLog = (RelativeLayout) findViewById(R.id.showKeyLog);
        keyLogin = (IMMListenerRelativeLayout) findViewById(R.id.keyLogin);
        keyLogin.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        //6.0获取权限
        boolean isForthefirstime = (boolean) SPUtils.get(this, "isForthefirstime", true);
        if (isForthefirstime)
            PermissionsUntils.requesetContanctsPermissions(this);
        initIp();
        initLogin();
    }


    private void initLogin() {
        String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
        username_login = (EditText) findViewById(R.id.username_login);
        username_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        ContentRl = (RelativeLayout) findViewById(R.id.ContentRl);
        logo_login = (ImageView) findViewById(R.id.logo_login);

        ipLinear = (RelativeLayout) findViewById(R.id.ipLinear);
        ip_login = (EditText) findViewById(R.id.ip_login);
        ip_login.setText(urlPath);
        ip_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        logo_login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (flag) {
                    flag = false;
                    ipLinear.setVisibility(View.GONE);
                } else {
                    flag = true;
                    ipLinear.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        //判断是否是登录账户
        String login_naem = get(this, "login_name", "").toString();
        if (!GeneralUtils.isEmpty(login_naem)) {
            username_login.setText(login_naem);
        }
        userpass_login = (EditText) findViewById(R.id.userpass_login);
        goto_login = (Button) findViewById(R.id.goto_login);
        register_login = (Button) findViewById(R.id.register_login);
        forget_login = (Button) findViewById(R.id.forget_login);
        userpassshow_login = (CheckBox) findViewById(R.id.userpassshow_login);

        forget_login.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        goto_login.setOnClickListener(this);
        register_login.setOnClickListener(this);
        forget_login.setOnClickListener(this);
        userpassshow_login.setOnCheckedChangeListener(new ShowPasswordListener(userpass_login));

        dialog = new LoadingDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        keyLogin.setOnKeyboardStateChangedListener(new IMMListenerRelativeLayout.IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case IMMListenerRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏
                        showKeyLog.setVisibility(View.VISIBLE);
                        break;
                    case IMMListenerRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示
                        addrssLinear.setVisibility(View.GONE);
                        ipshow_login.setChecked(false);
                        showKeyLog.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        testAddress.setOnClickListener(this);
        productionAddress.setOnClickListener(this);
    }

    private void initIp() {
        ipshow_login = (CheckBox) findViewById(R.id.ipshow_login);
        addrssLinear = (LinearLayout) findViewById(R.id.addrssLinear);
        testTv = (TextView) findViewById(R.id.testTv);
        productionTv = (TextView) findViewById(R.id.productionTv);
        testAddress = (FrameLayout) findViewById(R.id.testAddress);
        productionAddress = (FrameLayout) findViewById(R.id.productionAddress);
        ipshow_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setFocusable();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.hideSoftInputFromWindow(buttonView.getWindowToken(), 0); //强制隐藏键盘
                    addrssLinear.setVisibility(View.VISIBLE);
                } else {
                    addrssLinear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.goto_login) {//登录
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive())
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
            setFocusable();
            String username = username_login.getText().toString();
            SPUtils.put(this, "login_name", username);
            String userpass = userpass_login.getText().toString();
            LogUtils.debug("保存的IP地址:" + ip_login.getText().toString());
            SPUtils.put(RetailAplication.getContext(), "domain", ip_login.getText().toString());
            login(username, userpass, false);
        } else if (i == R.id.testAddress) {
            ip_login.setText(testTv.getText().toString());
            ipshow_login.setChecked(false);
            addrssLinear.setVisibility(View.GONE);
        } else if (i == R.id.productionAddress) {
            ip_login.setText(productionTv.getText().toString());
            ipshow_login.setChecked(false);
            addrssLinear.setVisibility(View.GONE);
        }
    }

    public void login(String userName, final String userPass, final boolean isAuto) {
        dialog.show();
        dialog.setLoadingTitle("正在登录...");
        HttpBank.getIntence(this).login(userName, userPass, null, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("登录:" + o.toString());
                Loginbean result = Loginbean.getLoginbean(o.toString());
                dialog.dismiss();
                if (result != null && result.isSuccess()) {
                    /** 保存数据 */
                    IBase.USERID = result.getUser_id();
                    IBase.SESSIONID = result.getS_id();
                    SPUtils.put(LoginActivity.this, "loginUser", o.toString());
                    SPUtils.put(LoginActivity.this, "login_user_id", IBase.USERID);
                    SPUtils.put(LoginActivity.this, "login_s_id", IBase.SESSIONID);
                    if (!isAuto) {
                        SPUtils.put(LoginActivity.this, "login_user_password", userPass);
                        SPUtils.put(LoginActivity.this, "currentLoginTime", System.currentTimeMillis());
                    }
                    if (!GeneralUtils.isEmpty(result.getCompanyCode()))
                        IBase.COMPANY_CODE = result.getCompanyCode();
                    SPUtils.put(LoginActivity.this, "companyCode", IBase.COMPANY_CODE);
                    if (!GeneralUtils.isEmpty(result.getUpload_token()))
                        SPUtils.put(LoginActivity.this, "uploadToken", result.getUpload_token());
                    SPUtils.put(LoginActivity.this, "currentUploadTime", System.currentTimeMillis());
                    Intent intent = new Intent(LoginActivity.this, CarMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 登录失败
                    String comment = "登录失败,请检查登录信息";
                    if (result != null)
                        comment = result.getComment();
                    DialogUtil.jumpCorrectErr(LoginActivity.this, comment, "继 续", 2, getResources().getColor(android.R.color.holo_orange_light));
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_TWO)
                    IBaseMethod.showToast(LoginActivity.this, strMsg, IBase.RETAIL_TWO);
                if (errorNo == IBase.CONSTANT_ONE)
                    IBaseMethod.showNetToast(LoginActivity.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SPUtils.put(this, "isForthefirstime", false);
    }

    private void setFocusable() {
        //设置失去焦点
        username_login.setFocusable(false);
        username_login.setFocusableInTouchMode(false);
        //设置获取焦点
        username_login.setFocusable(true);
        username_login.setFocusableInTouchMode(true);
        userpass_login.setFocusable(false);
        userpass_login.setFocusableInTouchMode(false);
        userpass_login.setFocusable(true);
        userpass_login.setFocusableInTouchMode(true);
        ip_login.setFocusable(false);
        ip_login.setFocusableInTouchMode(false);
        ip_login.setFocusable(true);
        ip_login.setFocusableInTouchMode(true);
    }
}
