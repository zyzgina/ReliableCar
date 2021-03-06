package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VINutils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.PhotosGridAdapter;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentCommitActivity;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.util.Calendar;

/**
 * 任务详情
 * Created by 左丽姬 on 2017/5/22.
 */

public class PhotosDetailsActivity extends BaseNoScoActivity implements View.OnClickListener {
    private MyGridView taskGridview;
    private PhotosGridAdapter adapter;
    private File file;
    private String UploadPath;
    private Button missionClickTask;
    private String vinCode = "";
    private EditText documentVIN_new;
    private LinearLayout vinVerfiydocumentVIN;
    private boolean isVin = true;
    private double longitude = 0, latitude = 0;
    private MapUtils mapUtils;
    private LinearLayout imageTaskNew;
    private ImageView vinTaskNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photosdetails);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        mapUtils = new MapUtils(this);
        mapUtils.initOnceLocation();
        mapUtils.startLocation(new LoactionListener() {
            @Override
            public void getOnLoactionListener(double longitude, double latitude) {
                LogUtils.debug("====" + longitude + "   " + latitude);
                PhotosDetailsActivity.this.longitude = longitude;
                PhotosDetailsActivity.this.latitude = latitude;
                mapUtils.stopLocation();
            }
        });
    }

    @Override
    public void initView() {
        dialog.dismiss();
        UploadPath = getIntent().getStringExtra("UploadPath");
        mtop_back.setVisibility(View.GONE);
        mBackll.setVisibility(View.VISIBLE);
        mTopbacktext.setText("任务");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("任务详情");
        top_btn.setText("提交");
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);
        mBackll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        missionClickTask = (Button) findViewById(R.id.missionClickTask);
        ScrollView scrollviewTaskDetails = (ScrollView) findViewById(R.id.scrollviewTaskDetails);

        //保存文件的的文件夹
        if (GeneralUtils.isEmpty(UploadPath)) {
            file = new File(FileUtils.getCarUploadPath() + "image/" + DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance()));
            if (!file.exists()) {
                file.mkdirs();
            }
            missionClickTask.setVisibility(View.GONE);
        } else {
            file = new File(UploadPath);
            missionClickTask.setVisibility(View.VISIBLE);
            missionClickTask.setOnClickListener(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.sp45));
            scrollviewTaskDetails.setLayoutParams(params);
        }
        findViewById(R.id.code_documentNum_new).setVisibility(View.GONE);
        findViewById(R.id.view_code_documentNum_new).setVisibility(View.GONE);

        documentVIN_new = (EditText) findViewById(R.id.documentVIN_new);
        vinCode = SPUtils.get(this, IBase.USERID + "vinCode", "").toString();
        if (!GeneralUtils.isEmpty(vinCode)) {
            documentVIN_new.setText(vinCode);
            HttpBank.getIntence(PhotosDetailsActivity.this).httpIsVinExit(vinCode, vinhandler);
        }
        documentVIN_new.addTextChangedListener(textWatcher);
        vinVerfiydocumentVIN = (LinearLayout) findViewById(R.id.vinVerfiydocumentVIN);
        findViewById(R.id.documentVINScan_new).setOnClickListener(this);

        taskGridview = (MyGridView) findViewById(R.id.taskGridview);
        adapter = new PhotosGridAdapter(this, file);
        taskGridview.setAdapter(adapter);
        taskGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = adapter.getString(position);
                Intent intent = new Intent();
                if ("评估高清图".equals(string))
                    intent.setClass(PhotosDetailsActivity.this, AssessmentActivity.class);
                else
                    intent.setClass(PhotosDetailsActivity.this, UploadTaskActivity.class);
                intent.putExtra("type", string);
                intent.putExtra("path", file.getAbsolutePath());
                startActivityForResult(intent, IBase.RETAIL_TEN);
            }
        });
        imageTaskNew = (LinearLayout) findViewById(R.id.image_task_new);
        vinTaskNew = (ImageView) findViewById(R.id.vin_task_new);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.top_btn) {
            commit();
        } else if (v.getId() == R.id.missionClickTask) {
            //点击任务管理
//            Intent intent = new Intent(this, TaskManageActivity.class);
//            startActivity(intent);
        } else if (v.getId() == R.id.documentVINScan_new) {
            isVin = false;
            dialog.show();
            dialog.setLoadingTitle("正在进入VIN扫描...");
            vinVerfiydocumentVIN.setVisibility(View.GONE);
            Intent intent = new Intent(PhotosDetailsActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        }

    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (adapter.getSise() == 0) {
            finish();
            return;
        }
        DialogUtil.prompt(this, getResources().getString(R.string.exitDocument),
                "取消", "确定", new DialogButtonListener() {
                    @Override
                    public void ok() {
                        adapter.exit();
                        finish();
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }

    private String imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_TEN && resultCode == IBase.RESUTL_THREE) {
            LogUtils.debug("进入==onActivityResult");
            adapter.notifyDataSetChanged();
        }
        if (requestCode == IBase.RETAIL_ELEVEN) {
            // VIN码
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog.cancel();
            }
            if (data != null) {
                String vin = data.getStringExtra("result");
                if (!GeneralUtils.isEmpty(vin)) {
                    documentVIN_new.setText(vin);
                    imagePath = data.getStringExtra("imagePath");
                    HttpBank.getIntence(PhotosDetailsActivity.this).httpIsVinExit(vin, vinhandler);
                }
            }
        }
    }

    boolean isVerfiy = false;
    private Handler vinhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.CONSTANT_TEN:
                    isVerfiy = (boolean) msg.obj;
                    if (!GeneralUtils.isEmpty(imagePath)) {
                        if (isVerfiy) {
                            Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
                            imageTaskNew.setVisibility(View.VISIBLE);
                            vinTaskNew.setImageBitmap(mBitmap);
                            compyImage(imagePath);
                        } else {
                            FileUtils.deleteFile(imagePath);
                        }
                    }
                    break;
            }
        }
    };

    private void commit() {
        String vinCo = documentVIN_new.getText().toString();
        if (GeneralUtils.isEmpty(vinCo) || !VINutils.checkVIN(vinCo) || !isVerfiy) {
            vinVerfiydocumentVIN.setVisibility(View.VISIBLE);
            return;
        }
        LogUtils.debug("savePath=" + savePath);
        if (!GeneralUtils.isEmpty(savePath)) {
            LogUtils.debug("进入了？");
            isCommit = true;
            UploadBean uploadBean = IBaseMethod.saveUploadBean(new File(savePath), vinCo, "校验车辆_照片采集", latitude + "", longitude + "");
            FinalDb db = FinalDb.create(PhotosDetailsActivity.this, IBase.BASE_DATE, true);
            db.save(uploadBean);
        }
        RetailAplication.getInstance().exitAllActicity();
        //保存图片
        adapter.saveDateList(vinCo, longitude + "", latitude + "");
        Intent intent = new Intent(PhotosDetailsActivity.this, DocumentCommitActivity.class);
        intent.putExtra("success", IBase.CONSTANT_TWO);
        intent.putExtra("UploadPath", file.getAbsolutePath());
        startActivity(intent);
        finish();
    }

    private String savePath;

    private void compyImage(final String strCaptureFilePath) {
        String uplod = FileUtils.getCarUploadPath() + IBase.VIN_PHOTO + "/" + System.currentTimeMillis();
        String name = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance()) + ".jpg";
        File save = new File(uplod);
        if (!save.exists()) {
            save.mkdirs();
        }
        save = new File(uplod, name);
        IBaseMethod.compyImage(strCaptureFilePath,save.getAbsolutePath(),savePath);
        savePath=save.getAbsolutePath();
    }

    boolean isCommit = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isCommit && !GeneralUtils.isEmpty(savePath)) {
            FileUtils.deleteFile(savePath);
        }
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
                if (vin.length() > 17) {
                    vin = vin.substring(0, 17);
                    documentVIN_new.setText(vin);
                    documentVIN_new.setSelection(vin.length());
                }
                if (vin.length() == 17) {
                    if (VINutils.checkVIN(vin)) {
                        vinVerfiydocumentVIN.setVisibility(View.GONE);
                        HttpBank.getIntence(PhotosDetailsActivity.this).httpIsVinExit(vin, vinhandler);
                    } else {
                        vinVerfiydocumentVIN.setVisibility(View.VISIBLE);
                    }
                } else {
                    vinVerfiydocumentVIN.setVisibility(View.GONE);
                }

            } else {
                isVin = true;
            }
        }
    };
}
