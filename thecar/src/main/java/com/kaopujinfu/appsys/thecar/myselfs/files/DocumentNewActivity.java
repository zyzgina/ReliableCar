package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.ContinuityCameraActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.ScannerActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogCameraListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.PicassoImageLoader;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VINutils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.DocumentNewImagesAdapter;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 新建文档
 * Created by Doris on 2017/5/15.
 */
public class DocumentNewActivity extends BaseNoScoActivity implements View.OnClickListener {

    private GridView docmentImages_new;
    private DocumentNewImagesAdapter mAdapter;
    private ImageView documentVINScan, documentScan;
    private EditText documentNum_new, documentVIN_new;

    private String fileName;
    private final int REQUEST_CODE_GALLERY = 1001;

    private FinalDb db;
    private String path;

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
                    if (!GeneralUtils.isEmpty(imagePath)) {
                        if (isVerfiy) {
                            Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
                            imageTaskNew.setVisibility(View.VISIBLE);
                            vinTaskNew.setImageBitmap(mBitmap);
                            compyImage(imagePath);
                        } else
                            FileUtils.deleteFile(imagePath);
                    }
                    break;
            }
        }
    };
    private double longitude = 0, latitude = 0;
    private MapUtils mapUtils;
    private boolean isDoument;
    private LinearLayout imageTaskNew;
    private ImageView vinTaskNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_new);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        mapUtils = new MapUtils(this);
        mapUtils.initOnceLocation();
        mapUtils.startLocation(new LoactionListener() {
            @Override
            public void getOnLoactionListener(double longitude, double latitude) {
                DocumentNewActivity.this.longitude = longitude;
                DocumentNewActivity.this.latitude = latitude;
                mapUtils.stopLocation();
            }
        });
    }

    @Override
    public void initView() {
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("新建文档");
        top_btn.setText("提交");
        top_btn.setOnClickListener(this);
        mtop_back.setOnClickListener(this);
        db = FinalDb.create(this, IBase.BASE_DATE, true);

        String vinCode = SPUtils.get(this, IBase.USERID + "vinCode", "").toString();
        documentVIN_new = (EditText) findViewById(R.id.documentVIN_new);
        if (!GeneralUtils.isEmpty(vinCode)) {
            documentVIN_new.setText(vinCode);
            HttpBank.getIntence(DocumentNewActivity.this).httpIsVinExit(vinCode, vinhandler);
        }
        documentVIN_new.addTextChangedListener(textWatcher);
        vinVerfiydocumentVIN = (LinearLayout) findViewById(R.id.vinVerfiydocumentVIN);
        documentNum_new = (EditText) findViewById(R.id.documentNum_new);
        documentVINScan = (ImageView) findViewById(R.id.documentVINScan_new);
        documentScan = (ImageView) findViewById(R.id.documentScan_new);
        documentVINScan.setOnClickListener(this);
        documentScan.setOnClickListener(this);

        path = FileUtils.getCarUploadPath() + "image/" + System.currentTimeMillis() + "/";
        mAdapter = new DocumentNewImagesAdapter(this, path);
        docmentImages_new = (GridView) findViewById(R.id.docmentImages_new);
        docmentImages_new.setAdapter(mAdapter);
        docmentImages_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mAdapter.getCount() - 1) {
                    fileName = System.currentTimeMillis() + ".jpg";
                    DialogUtil.selectPicDialog(DocumentNewActivity.this, fileName, new DialogCameraListener() {
                        @Override
                        public boolean takePhoto() {
                            LogTxt.getInstance().writeLog("跳转到拍照界面");
                            Intent intent = new Intent(DocumentNewActivity.this, ContinuityCameraActivity.class);
                            intent.putExtra("imagePath", path);
                            startActivityForResult(intent, IBase.RETAIL_ONE);
                            return false;
                        }

                        @Override
                        public boolean selectImage() {
                            LogTxt.getInstance().writeLog("跳转到图片多选界面");
                            selectPic();
                            return false;
                        }
                    });
                } else {
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    Uri mUri = Uri.parse("file://" + mAdapter.getItem(i));
                    it.setDataAndType(mUri, "image/*");
                    startActivity(it);
                }
            }
        });
        isDoument = getIntent().getBooleanExtra("isDoument", false);
        if (isDoument) {
            Intent intent = new Intent(DocumentNewActivity.this, ScannerActivity.class);
            startActivityForResult(intent, IBase.RETAIL_THREE);
        }
        imageTaskNew = (LinearLayout) findViewById(R.id.image_task_new);
        vinTaskNew = (ImageView) findViewById(R.id.vin_task_new);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
//                mAdapter.addImages(resultList);
                savePhoto(resultList);
//                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(DocumentNewActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.top_btn) {
            commit();
        } else if (view == mtop_back) {
            back();
        } else if (view.getId() == R.id.documentVINScan_new) {
            isVin = false;
            dialog.show();
            dialog.setLoadingTitle("正在进入VIN扫描...");
            vinVerfiydocumentVIN.setVisibility(View.GONE);
            Intent intent = new Intent(DocumentNewActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        } else if (view.getId() == R.id.documentScan_new) {
            Intent intent = new Intent(DocumentNewActivity.this, ScannerActivity.class);
            startActivityForResult(intent, IBase.RETAIL_THREE);
        }
    }

    private String imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_ZERO) {
            // 从相机中选择
            mAdapter.notifyDataSetChanged();
        }
        if (requestCode == IBase.RETAIL_ONE) {
            // 拍照
            mAdapter.notifyDataSetChanged();
        }
        if (requestCode == IBase.RETAIL_THREE) {
            // 标签编号
            if (data != null) {
                String number = data.getStringExtra("result");
                if (!GeneralUtils.isEmpty(number)) {
                    documentNum_new.setText(number);
                } else {
                    if (isDoument) {
                        finish();
                    }
                }
            } else {
                if (isDoument) {
                    finish();
                }
            }
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
                    HttpBank.getIntence(DocumentNewActivity.this).httpIsVinExit(vin, vinhandler);
                }
            }
        }
        if (requestCode == IBase.RETAIL_NINE) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    UploadBean uploadBean = null;

    private void commit() {
        final String vinNo = documentVIN_new.getText().toString();
        String rfidId = documentNum_new.getText().toString();
        if (GeneralUtils.isEmpty(vinNo)) {
            IBaseMethod.showToast(DocumentNewActivity.this, "请输入VIN码！", IBase.RETAIL_ZERO);
            return;
        } else if (!VINutils.checkVIN(vinNo) || !isVerfiy) {
            vinVerfiydocumentVIN.setVisibility(View.VISIBLE);
            return;
        } else if (GeneralUtils.isEmpty(rfidId)) {
            IBaseMethod.showToast(DocumentNewActivity.this, "请输入标签编号！", IBase.RETAIL_ZERO);
            return;
        }
        String upKey = "";
        if (!GeneralUtils.isEmpty(savePath)) {
            uploadBean = IBaseMethod.saveUploadBean(new File(savePath), vinNo, "校验车辆_文档绑标签", latitude + "", longitude + "");
            upKey = uploadBean.getQny_key();
        }
        dialog.show();
        dialog.setLoadingTitle("正在提交文档……");
        HttpBank.getIntence(this).newDocument(vinNo, rfidId, upKey, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                Result result = Result.getMcJson(o.toString());
                if (result.isSuccess()) {
                    LogTxt.getInstance().writeLog("新建文档成功");
                    //保存上传的图片到本地
                    List<String> lists = mAdapter.getLists();
                    if (lists.size() > 0) {
                        IBaseMethod.saveDateLoaction(db, lists, documentVIN_new.getText().toString(), "文档绑定", latitude + "", longitude + "");
                    }
                    //通知首页统计数据发改变
                    JumpEventBus jumpEventBus = new JumpEventBus();
                    jumpEventBus.setStatus(IBase.RETAIL_THREE);
                    EventBus.getDefault().post(jumpEventBus);
                    if (isDoument) {
                        RetailAplication.getInstance().exitAllActicity();
                    }
                    isCommit = true;
                    if (!GeneralUtils.isEmpty(uploadBean)) {
                        FinalDb db = FinalDb.create(DocumentNewActivity.this, IBase.BASE_DATE, true);
                        db.save(uploadBean);
                    }
                    Intent intent = new Intent(DocumentNewActivity.this, DocumentCommitActivity.class);
                    intent.putExtra("success", IBase.CONSTANT_ZERO);
                    startActivity(intent);
                    finish();
                } else {
                    LogTxt.getInstance().writeLog("新建文档失败");
                    IBaseMethod.showToast(DocumentNewActivity.this, result.getComment(), IBase.RETAIL_ZERO);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                LogTxt.getInstance().writeLog("新建文档错误");
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(DocumentNewActivity.this);
                }
            }
        });
    }

    private String savePath;

    private void compyImage(final String strCaptureFilePath) {
        String uplod = FileUtils.getCarUploadPath() + IBase.VIN_DOC + "/" + System.currentTimeMillis();
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

    private void back() {
        if (mAdapter.getCount() == 1) {
            if (isDoument) {
                finish();
            } else {
                Intent intent = new Intent(DocumentNewActivity.this, DocumentActivity.class);
                startActivity(intent);
                finish();
            }
            return;
        }
        DialogUtil.prompt(this, getResources().getString(R.string.exitDocument),
                "取消", "确定", new DialogButtonListener() {
                    @Override
                    public void ok() {
                        mAdapter.exit();
                        if (isDoument) {
                            finish();
                        } else {
                            Intent intent = new Intent(DocumentNewActivity.this, DocumentActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }

    private void selectPic() {
        PicassoImageLoader imageLoader = new PicassoImageLoader();
        FunctionConfig functionConfig = new FunctionConfig.Builder().setMutiSelectMaxSize(8).build();
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                // 标题栏背景颜色
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimaryDark))
                // 标题栏文本字体颜色
                .setTitleBarTextColor(Color.WHITE)
                // 标题栏icon颜色，如果设置了标题栏icon，设置setTitleBarIconColor将无效
                .setTitleBarIconColor(Color.WHITE)
                // 设置Floating按钮Nornal状态颜色
                .setFabNornalColor(getResources().getColor(R.color.car_theme))
                // 设置Floating按钮Pressed状态颜色
                .setFabPressedColor(getResources().getColor(R.color.blue))
                // 选择框未选颜色
                .setCheckNornalColor(Color.WHITE)
                // 选择框选中颜色
                .setCheckSelectedColor(getResources().getColor(R.color.car_theme))
                //设置标题栏清除选择按钮
                .setIconClear(R.drawable.cancel_bluetooth)
                //设置返回按钮icon
                .setIconBack(R.drawable.back)
                // 设置预览按钮icon
                .setIconPreview(R.drawable.ic_preview)
                .build();
        CoreConfig config = new CoreConfig.Builder(DocumentNewActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(config);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }

    private void savePhoto(final List<PhotoInfo> resultList) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                int i = 1;
                for (PhotoInfo photoInfo : resultList) {
                    File upload = new File(photoInfo.getPhotoPath());
                    String name = "文档绑定_" + DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance()) + "_" + (i + 1) + ".jpg";
                    File save = new File(path);
                    if (!save.exists()) {
                        save.mkdirs();
                    }
                    save = new File(path, name);
                    if (upload.exists()) {
                        FileUtils.CopySdcardFile(upload.getAbsolutePath(), save.getAbsolutePath());
                    }
                    i++;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();

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
                        HttpBank.getIntence(DocumentNewActivity.this).httpIsVinExit(vin, vinhandler);
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
