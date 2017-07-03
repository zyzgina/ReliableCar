package com.kaopujinfu.appsys.thecar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.ShowPasswordListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;

/**
 * 修改密码
 * Created by zuoliji on 2017/3/30.
 */

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText oldpassword_update, password_update, passtwo_update;
    private Button commitgo_update;
    private TextView error_update;
    private CheckBox oldpasswordshow_update, passwordshow_update, passtwoshow_update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {
        dialog = new LoadingDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("修改密码");
        top_meun.setVisibility(View.GONE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        oldpassword_update = (EditText) findViewById(R.id.oldpassword_update);
        password_update = (EditText) findViewById(R.id.password_update);
        passtwo_update = (EditText) findViewById(R.id.passtwo_update);
        oldpasswordshow_update = (CheckBox) findViewById(R.id.oldpasswordshow_update);
        passwordshow_update = (CheckBox) findViewById(R.id.passwordshow_update);
        passtwoshow_update = (CheckBox) findViewById(R.id.passtwoshow_update);

        oldpassword_update.addTextChangedListener(new UpdateEditChangedListener(IBase.RETAIL_ZERO, oldpassword_update));
        password_update.addTextChangedListener(new UpdateEditChangedListener(IBase.RETAIL_ONE, password_update));
        passtwo_update.addTextChangedListener(new UpdateEditChangedListener(IBase.RETAIL_TWO, passtwo_update));

        error_update = (TextView) findViewById(R.id.error_update);
        commitgo_update = (Button) findViewById(R.id.commitgo_update);
        commitgo_update.setOnClickListener(this);

        oldpasswordshow_update.setOnCheckedChangeListener(new ShowPasswordListener(oldpassword_update));
        passwordshow_update.setOnCheckedChangeListener(new ShowPasswordListener(password_update));
        passtwoshow_update.setOnCheckedChangeListener(new ShowPasswordListener(passtwo_update));
    }

    /**
     * 验证表单
     */
    private void verificationForm() {
        Message msg = new Message();
        String oldpassword = oldpassword_update.getText().toString();
        if (oldpassword.length() < 6 || oldpassword.length() > 15) {
            msg.obj = "旧密码输入错误";
            msg.what = IBase.RETAIL_TWO;
            uHandler.sendMessage(msg);
            return;
        }
        String password = password_update.getText().toString();
        if (password.length() < 6 || password.length() > 15) {
            msg.obj = "请确认是否在6-15之间";
            msg.what = IBase.RETAIL_TWO;
            uHandler.sendMessage(msg);
            return;
        }
        String passtwo = passtwo_update.getText().toString();
        if (!passtwo.equals(password)) {
            msg.obj = "两次密码不一致";
            msg.what = IBase.RETAIL_TWO;
            uHandler.sendMessage(msg);
            return;
        }
        uHandler.sendEmptyMessage(IBase.RETAIL_THREE);
        updateCommit(oldpassword, password);
    }

    /**
     * 表单提交
     */
    private void updateCommit(String oldPassword, String newPassword) {
        dialog.show();
        dialog.setLoadingTitle("正在修改密码...");
        HttpBank.getIntence(this).updatePassword(oldPassword, newPassword, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("修改密码:"+o.toString());
                Result result = Result.getMcJson(o.toString());
                IBaseMethod.showToast(UpdatePasswordActivity.this, result.getComment(), IBase.RETAIL_ONE);
                if (result.isSuccess()) {
                    SPUtils.remove(UpdatePasswordActivity.this, "login_user_password");
                    Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.commitgo_update) {
            if (isTextChange)
                verificationForm();

        }
    }

    private boolean isOld = false, isPassword = false, isPassTwo = false;

    /**
     * 文本框文字改变监听
     */
    private class UpdateEditChangedListener implements TextWatcher {
        private int status = IBase.RETAIL_NEGATIVE;
        private EditText mEeditText;

        public UpdateEditChangedListener() {

        }

        public UpdateEditChangedListener(int status, EditText mEeditText) {
            this.status = status;
            this.mEeditText = mEeditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (status != IBase.RETAIL_NEGATIVE)
                isChangVer();
        }

        private void isChangVer() {
            String nameText = mEeditText.getText().toString();
            switch (status) {
                case IBase.RETAIL_ZERO:
                    if (!GeneralUtils.isEmpty(nameText)) {
                        isOld = true;
                    } else {
                        isOld = false;
                    }
                    break;
                case IBase.RETAIL_ONE:
                    if (!GeneralUtils.isEmpty(nameText)) {
                        isPassword = true;
                    } else {
                        isPassword = false;
                    }
                    break;
                case IBase.RETAIL_TWO:
                    if (!GeneralUtils.isEmpty(nameText)) {
                        isPassTwo = true;
                    } else {
                        isPassTwo = false;
                    }
                    break;
            }
            isRegShow();
        }

        //判断注册按钮是否显示
        private void isRegShow() {
            if (!isOld) {
                uHandler.sendEmptyMessage(IBase.RETAIL_ZERO);
                return;
            }
            if (!isPassword) {
                uHandler.sendEmptyMessage(IBase.RETAIL_ZERO);
                return;
            }
            if (!isPassTwo) {
                uHandler.sendEmptyMessage(IBase.RETAIL_ZERO);
                return;
            }
            uHandler.sendEmptyMessage(IBase.RETAIL_ONE);
        }

    }

    private boolean isTextChange = false;
    private Handler uHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.RETAIL_ZERO:
                    //验证注册按钮不可以点击
                    isTextChange = false;
                    commitgo_update.setBackgroundResource(R.drawable.button_circular5_light_gray);
                    break;
                case IBase.RETAIL_ONE:
                    //验证注册按钮可以点击
                    isTextChange = true;
                    commitgo_update.setBackgroundResource(R.drawable.button_circular5_light_green);
                    error_update.setVisibility(View.GONE);
                    break;
                case IBase.RETAIL_TWO:
                    //验证失败（焦点改变监听验证）
                    error_update.setVisibility(View.VISIBLE);
                    String error1 = (String) msg.obj;
                    error_update.setText(error1);
                    break;
                case IBase.RETAIL_THREE:
                    error_update.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
