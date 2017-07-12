package com.github.reliablecar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.BitmapImageView;
import com.kaopujinfu.appsys.thecar.CarMainActivity;
import com.kaopujinfu.appsys.thecar.LoginActivity;

import static com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils.get;

/**
 * 启动界面
 * author : zuoliji
 * date   :2017/3/28
 */
public class StartActivity extends AppCompatActivity {

    private BitmapImageView image_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initStart();
    }

    /**
     * 初始化页面方法
     */
    private void initStart() {
        image_start = (BitmapImageView) findViewById(R.id.image_start);
//        //启动动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.start);
        animation.setAnimationListener(animationListener);
        image_start.setAnimation(animation);
    }

    /**
     * 动画监听
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //动画开始
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            autoLogin();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


    private void autoLogin() {
        Long currentTime = System.currentTimeMillis();
        Long loginTime = (Long) get(StartActivity.this, "currentLoginTime", currentTime);
        String userPass = (String) get(StartActivity.this, "login_user_password", "");
        if (GeneralUtils.isEmpty(userPass)||currentTime - loginTime == 0 || currentTime - loginTime >= 24 * 60 * 60 * 1000) { // 24 小时
            // 需要手动登录
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 自动登录
            String userName = (String) get(StartActivity.this, "login_name", "");
            if (!GeneralUtils.isEmpty(userName) && !GeneralUtils.isEmpty(userPass)) {
                // 用户名和密码不为空的时候进行登录
                login(userName, userPass, true);
            }
        }
    }

    public void login(String userName, final String userPass, final boolean isAuto) {
        HttpBank.getIntence(this).login(userName, userPass, null, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("登录:" + o.toString());
                Loginbean result = Loginbean.getLoginbean(o.toString());
                if (result != null && result.isSuccess()) {
                    /** 保存数据 */
                    IBase.USERID = result.getUser_id();
                    IBase.SESSIONID = result.getS_id();
                    SPUtils.put(StartActivity.this, "loginUser", o.toString());
                    SPUtils.put(StartActivity.this, "login_user_id", IBase.USERID);
                    SPUtils.put(StartActivity.this, "login_s_id", IBase.SESSIONID);
                    if (!isAuto) {
                        SPUtils.put(StartActivity.this, "login_user_password", userPass);
                        SPUtils.put(StartActivity.this, "currentLoginTime", System.currentTimeMillis());
                    }
                    if (!GeneralUtils.isEmpty(result.getCompanyCode()))
                        IBase.COMPANY_CODE = result.getCompanyCode();
                    SPUtils.put(StartActivity.this, "companyCode", IBase.COMPANY_CODE);
                    SPUtils.put(StartActivity.this, "uploadToken", result.getUpload_token());
                    SPUtils.put(StartActivity.this, "currentUploadTime", System.currentTimeMillis());
                    Intent intent = new Intent(StartActivity.this, CarMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 登录失败
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
