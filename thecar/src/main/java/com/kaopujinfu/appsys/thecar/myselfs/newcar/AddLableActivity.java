package com.kaopujinfu.appsys.thecar.myselfs.newcar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.ScannerActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VideoRecordActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VINutils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.CaptureConfiguration;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.PredefinedCaptureConfigurations;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentCommitActivity;

import net.tsz.afinal.FinalDb;

import java.io.File;

/**
 * 车辆标签绑定
 * Created by 左丽姬 on 2017/5/17.
 */
public class AddLableActivity extends BaseActivity implements View.OnClickListener {

    private ImageView documentVINScan, documentScan;
    private EditText documentNum_new, documentVIN_new;
    private LinearLayout vinVerfiydocumentVIN;
    private boolean isVin = true;
    boolean isVerfiy = false;
    private Handler vinhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.CONSTANT_TEN:
                    isVerfiy = (boolean) msg.obj;
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbinding);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("车辆标签绑定");
        top_btn.setText("提交");
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);
        mtop_back.setOnClickListener(this);

        TextView txt_documentNum_new = (TextView) findViewById(R.id.txt_documentNum_new);
        txt_documentNum_new.setText("标签编号");
        String vinCode = SPUtils.get(this, IBase.USERID + "vinCode", "").toString();
        documentVIN_new = (EditText) findViewById(R.id.documentVIN_new);
        if (!GeneralUtils.isEmpty(vinCode))
            documentVIN_new.setText(vinCode);
        documentVIN_new.addTextChangedListener(textWatcher);
        vinVerfiydocumentVIN = (LinearLayout) findViewById(R.id.vinVerfiydocumentVIN);
        documentNum_new = (EditText) findViewById(R.id.documentNum_new);
        documentNum_new.setHint("请输入标签编号");
        documentVINScan = (ImageView) findViewById(R.id.documentVINScan_new);
        documentScan = (ImageView) findViewById(R.id.documentScan_new);
        documentVINScan.setOnClickListener(this);
        documentScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.top_btn) {
            //提交
            commitBinding();
        } else if (i == R.id.documentVINScan_new) {
            isVin = false;
            dialog.show();
            dialog.setLoadingTitle("正在进入VIN扫描...");
            vinVerfiydocumentVIN.setVisibility(View.GONE);
            Intent intent = new Intent(AddLableActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        } else if (i == R.id.documentScan_new) {
            Intent intent = new Intent(AddLableActivity.this, ScannerActivity.class);
            startActivityForResult(intent, IBase.RETAIL_THREE);
        } else if (v == mtop_back) {
            Intent intent = new Intent(AddLableActivity.this, LableActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 绑定标签
     */
    private void commitBinding() {
        String vinNo = documentVIN_new.getText().toString();
        String devCode = documentNum_new.getText().toString();
        if (GeneralUtils.isEmpty(vinNo)) {
            IBaseMethod.showToast(this, "请输入VIN码！", IBase.RETAIL_ZERO);
            return;
        } else if (!VINutils.checkVIN(vinNo) || isVerfiy) {
            vinVerfiydocumentVIN.setVisibility(View.VISIBLE);
            return;
        } else if (GeneralUtils.isEmpty(devCode)) {
            IBaseMethod.showToast(this, "请输入标签编号！", IBase.RETAIL_ZERO);
            return;
        }
        dialog.show();
        dialog.setLoadingTitle("正在绑定……");
        HttpBank.getIntence(this).httpLable(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("绑定标签:" + o.toString());
                Result result = Result.getMcJson(o.toString());
                if (result != null && result.isSuccess()) {
                    showDialog();
                } else {
                    if (result != null)
                        IBaseMethod.showToast(AddLableActivity.this, result.getComment(), IBase.RETAIL_ZERO);
                    else
                        IBaseMethod.showToast(AddLableActivity.this, "网络出错", IBase.RETAIL_ZERO);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(AddLableActivity.this);
                }
            }
        }, vinNo, devCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_THREE) {
            // 标签编号
            if (data != null) {
                String number = data.getStringExtra("result");
                documentNum_new.setText(number);
            }
        }

        if (requestCode == IBase.RETAIL_ELEVEN) {
            // VIN编号
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog.cancel();
            }
            if (data != null) {
                String vin = data.getStringExtra("result");
                if (!GeneralUtils.isEmpty(vin)) {
                    documentVIN_new.setText(vin);
                    SPUtils.put(AddLableActivity.this, IBase.USERID + "vinCode", vin);
                    HttpBank.getIntence(AddLableActivity.this).httpIsVinExit(vin, vinhandler);
                }
            }
        }

        if (resultCode == RESULT_OK) {
            String path = data.getStringExtra(VideoRecordActivity.EXTRA_OUTPUT_FILENAME);
            LogUtils.debug("上传视频的路径:" + path);
            //提交成功
            File file = new File(path);
            UploadBean uploadBean = IBaseMethod.saveUploadBean(file, documentVIN_new.getText().toString(), "车辆绑标签");
            FinalDb db = FinalDb.create(AddLableActivity.this, IBase.BASE_DATE, true);
            db.save(uploadBean);
            Intent intent = new Intent(this, DocumentCommitActivity.class);
            intent.putExtra("success", IBase.CONSTANT_FOUR);
            startActivity(intent);
            finish();
        }

    }

    /**
     * 提交成功
     */
    private void showDialog() {
        final Dialog mDialog = new Dialog(this, R.style.PassDialog);
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_binding, null);
        mDialog.setContentView(view);
        final WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
        p.height = (int) (d.getHeight() * 0.5);
        mDialog.getWindow().setAttributes(p);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        Button canlce_dialog = (Button) view.findViewById(R.id.canlce_dialog);
        canlce_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mDialog.cancel();
                final CaptureConfiguration config = createCaptureConfiguration();
//                String fileName = "";
                final Intent intent = new Intent(AddLableActivity.this, VideoRecordActivity.class);
                intent.putExtra(VideoRecordActivity.EXTRA_CAPTURE_CONFIGURATION, config);
//                intent.putExtra(VideoRecordActivity.EXTRA_OUTPUT_FILENAME, filename);
                startActivityForResult(intent, 101);
            }
        });
    }

    private CaptureConfiguration createCaptureConfiguration() {
        // 设置大小(RES_360P/RES_480P/RES_720P/RES_1080P/RES_1440P/RES_2160P)
        final PredefinedCaptureConfigurations.CaptureResolution resolution = PredefinedCaptureConfigurations.CaptureResolution.RES_720P;
        // 设置清晰度 (HIGH高清、MEDIUM中等、LOW低配)
        final PredefinedCaptureConfigurations.CaptureQuality quality = PredefinedCaptureConfigurations.CaptureQuality.MEDIUM;
        CaptureConfiguration.Builder builder = new CaptureConfiguration.Builder(resolution, quality);
        // 设置视频最大时长，秒为单位
//        builder.maxDuration(60);
        // 设置视频最大大小，M为单位
//        builder.maxFileSize(5);
        // 设置每秒的帧数
//        builder.frameRate(5);
        // 设置是否显示时间
        // 显示时间
        builder.showRecordingTime();
        // 隐藏时间
//        builder.noCameraToggle();
        return builder.build();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isVin) {
                vinVerfiydocumentVIN.setVisibility(View.GONE);
                String vin = documentVIN_new.getText().toString();
                if (vin.length() >17) {
                    vin = vin.substring(0, 17);
                    documentVIN_new.setText(vin);
                    documentVIN_new.setSelection(vin.length());
                }
                if(vin.length()==17) {
                    if (VINutils.checkVIN(vin)) {
                        vinVerfiydocumentVIN.setVisibility(View.GONE);
                        HttpBank.getIntence(AddLableActivity.this).httpIsVinExit(vin, vinhandler);
                    } else {
                        vinVerfiydocumentVIN.setVisibility(View.VISIBLE);
                    }
                }else{
                    vinVerfiydocumentVIN.setVisibility(View.GONE);
                }

            } else {
                isVin = true;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddLableActivity.this, LableActivity.class);
        startActivity(intent);
        finish();
    }

}
