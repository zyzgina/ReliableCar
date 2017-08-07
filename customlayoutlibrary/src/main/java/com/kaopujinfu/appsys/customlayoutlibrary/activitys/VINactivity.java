package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etop.vin.VINAPI;
import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.TaskItemBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.VinViewfinderView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.CaptureConfiguration;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.PredefinedCaptureConfigurations;
import com.reliablel.voiceproject.VoiceUtils;

import net.tsz.afinal.FinalDb;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * VIN 扫描
 * Created by 左丽姬 on 2017/5/10.
 */

public class VINactivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private String PATH = FileUtils.getLogFilePath() + "VinCode/";

    private static final String UsrID = "7D0408988D72F47C2166";//
    //    private static final String UsrID = "7332DBAFD2FD18301EF6";
    private Camera mycamera;
    private VinViewfinderView myView;


    private RelativeLayout mRec;
    private ImageButton mBackcamera, mFlashcamera, mTackPicbtn;
    private TextView mVinresult, mRemind;
    private ImageView mShowbitmap;

    private VINAPI api;
    private Bitmap bitmap;
    private int preWidth = 0;
    private int preHeight = 0;
    private int photoWidth = 0;
    private int photoHeight = 0;
    private boolean isROI = false;

    private Timer time;
    private TimerTask timer;
    private Vibrator mVibrator;
    private ToneGenerator tone;
    private byte[] tackData;
    private int[] m_ROI = {0, 0, 0, 0};
    private boolean bInitKernal = false;
    private boolean bP = false;
    private TextView toats_vin;

    private RelativeLayout mMsgvin;
    private TextView query_vin, jxs_vin, company_vin, num_vin;
    private FinalDb db;
    private MapUtils mapUtils;
    private double longitude, latitude;
    private String strCaptureFilePath;
    private VoiceUtils voiceUtils;
    private boolean exitFlag = false, isThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivitySetting();
        setContentView(R.layout.activity_vin);
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        voiceUtils = new VoiceUtils();
        voiceUtils.initialTts(this);
        initView();
    }

    /**
     * activity 信息设置
     */
    private void setActivitySetting() {
        // 设置activity横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Configuration mCfg = this.getResources().getConfiguration();
        int noriention = mCfg.orientation;
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(PATH);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        if (noriention == mCfg.ORIENTATION_LANDSCAPE) {
            if (!bInitKernal) {
                if (api == null) {
                    api = new VINAPI();
                    String cacheDir = (this.getCacheDir()).getPath();
                    String FilePath = cacheDir + "/" + UsrID + ".lic";
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    int nRet = api.VinKernalInit("", FilePath, UsrID, 0x01, 0x02, telephonyManager, this);
                    if (nRet != 0) {
                        Toast.makeText(getApplicationContext(), "激活失败", Toast.LENGTH_SHORT).show();
                        bInitKernal = false;
                    } else {
                        bInitKernal = true;
                    }
                }
            }
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceViwe);
        mRec = (RelativeLayout) findViewById(R.id.re_c);
        mBackcamera = (ImageButton) findViewById(R.id.back_camera);
        mFlashcamera = (ImageButton) findViewById(R.id.flash_camera);
        mTackPicbtn = (ImageButton) findViewById(R.id.tackPic_btn);
        mVinresult = (TextView) findViewById(R.id.vin_result);
        mRemind = (TextView) findViewById(R.id.remind);
        mShowbitmap = (ImageView) findViewById(R.id.showbitmap);

        toats_vin = (TextView) findViewById(R.id.toats_vin);
        mMsgvin = (RelativeLayout) findViewById(R.id.msg_vin);
        mMsgvin.setVisibility(View.GONE);
        query_vin = (TextView) findViewById(R.id.query_vin);
        query_vin.setVisibility(View.GONE);
        jxs_vin = (TextView) findViewById(R.id.jxs_vin);
        company_vin = (TextView) findViewById(R.id.company_vin);
        num_vin = (TextView) findViewById(R.id.num_vin);

        setLayoutXY();
        mBackcamera.setOnClickListener(bClickListener);
        mFlashcamera.setOnClickListener(fClickListener);
        mTackPicbtn.setOnClickListener(tClickListener);
        mapUtils = new MapUtils(this);
        mapUtils.initOnceLocation();
        mapUtils.startLocation(new LoactionListener() {
            @Override
            public void getOnLoactionListener(double longitude, double latitude) {
                VINactivity.this.longitude = longitude;
                VINactivity.this.latitude = latitude;
            }
        });
    }

    private int width;
    private int height;
    private double screenInches;
    private boolean isFatty = false;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    /**
     * 设置布局显示的位置
     */
    private void setLayoutXY() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
        float densityx = metric.xdpi;
        float densityy = metric.ydpi;
        double wx = width / densityx;
        double hy = height / densityy;
        screenInches = Math.sqrt(Math.pow(wx, 2) + Math.pow(hy, 2));

        if (width * 3 == height * 4) {
            isFatty = true;
        }

        mRemind.setText("点击屏幕继续识别");
        mRemind.setTextColor(Color.WHITE);
        mRemind.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 18);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.topMargin = (int) (height * 0.08);
        mRemind.setLayoutParams(layoutParams);
        mRemind.setVisibility(View.INVISIBLE);

        mVinresult.setTextColor(Color.GREEN);
        mVinresult.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 16);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.topMargin = (int) (height * 0.2);
        mVinresult.setLayoutParams(layoutParams);
        mVinresult.setVisibility(View.INVISIBLE);
        mShowbitmap.setVisibility(View.INVISIBLE);

        int back_w = (int) (width * 0.066796875);
        int back_h = (int) (back_w * 1);
        layoutParams = new RelativeLayout.LayoutParams(back_w, back_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.bottomMargin = (int) (height * 0.15);
        mBackcamera.setLayoutParams(layoutParams);

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, back_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.bottomMargin = (int) (height * 0.15);
        query_vin.setLayoutParams(layoutParams);
        query_vin.setGravity(Gravity.CENTER);

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.bottomMargin = (int) (height * 0.07);
        mMsgvin.setLayoutParams(layoutParams);

        int flash_w = (int) (width * 0.066796875);
        int flash_h = (int) (flash_w * 69 / 106);
        layoutParams = new RelativeLayout.LayoutParams(flash_w, flash_h);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) ((back_h / 2));
        layoutParams.topMargin = (int) (height * 0.15);
        mFlashcamera.setLayoutParams(layoutParams);


        int tackPic_w = (int) (width * 0.076796875);
        int tackPic_h = (int) (tackPic_w * 1);
        layoutParams = new RelativeLayout.LayoutParams(tackPic_w, tackPic_h);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.leftMargin = (int) (((width - height * 0.06 * 1.585) - back_h));
        mTackPicbtn.setLayoutParams(layoutParams);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(VINactivity.this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void copyDataBase() throws IOException {
        //  Common common = new Common();
        // String dst = Environment.getExternalStorageDirectory().toString() + "/"+UsrID+".lic";
        String cacheDir = (this.getCacheDir()).getPath();
        String dst = cacheDir + "/" + UsrID + ".lic";
        File file = new File(dst);
        if (!file.exists()) {
            // file.createNewFile();
        } else {
            file.delete();
        }

        try {
            InputStream myInput = getAssets().open(UsrID + ".lic");
            OutputStream myOutput = new FileOutputStream(dst);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            System.out.println(UsrID + ".lic" + "is not found");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (api == null) {
            api = new VINAPI();
            //String FilePath =Environment.getExternalStorageDirectory().toString()+"/"+UsrID+".lic";
            String cacheDir = (this.getCacheDir()).getPath();
            String FilePath = cacheDir + "/" + UsrID + ".lic";
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            int nRet = api.VinKernalInit("", FilePath, UsrID, 0x01, 0x02, telephonyManager, this);
            if (nRet != 0) {
                Toast.makeText(getApplicationContext(), "激活失败", Toast.LENGTH_SHORT).show();
                bInitKernal = false;
            } else {
                bInitKernal = true;
            }
        }
        if (mycamera == null) {
            try {
                mycamera = Camera.open();
            } catch (Exception e) {
                e.printStackTrace();
                String mess = getResources().getString(R.string.toast_camera);
                Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG).show();
                return;
            }
        }

        initCamera(holder);
        time = new Timer();
        if (timer == null) {
            timer = new TimerTask() {
                public void run() {
                    // isSuccess=false;
                    if (mycamera != null) {
                        try {
                            mycamera.autoFocus(new Camera.AutoFocusCallback() {
                                public void onAutoFocus(boolean success, Camera camera) {
                                    // isSuccess=success;
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                ;
            };
        }
        time.schedule(timer, 200, 2000);

    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, int width, int height) {
        if (mycamera != null) {
            mycamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        synchronized (camera) {
                            new Thread() {
                                public void run() {
                                    initCamera(holder);
                                    super.run();
                                }
                            }.start();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            if (mycamera != null) {
                mycamera.setPreviewCallback(null);
                mycamera.stopPreview();
                mycamera.release();
                mycamera = null;
            }
        } catch (Exception e) {
        }
        if (time != null) {
            time.cancel();
            time = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (api != null) {
            api.VinKernalUnInit();
            api = null;
        }
    }

    @Override
    protected void onRestart() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initCamera(SurfaceHolder mholder) {
        Camera.Parameters parameters = mycamera.getParameters();
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
        Camera.Size size;
        int length = list.size();
        Camera.Size tmpsize = getOptimalPreviewSize(list, width, height);
        int previewWidth = list.get(0).width;
        int previewheight = list.get(0).height;
        int second_previewWidth = 0;
        int second_previewheight = 0;
        int nlast = -1;
        int nThird = -1;
        previewWidth = tmpsize.width;
        previewheight = tmpsize.height;
        if (length == 1) {
//            LogTxt.getInstance().writeLog("获取previewWidth:" + previewWidth + "   previewheight:" + previewheight);
            preWidth = previewWidth;
            preHeight = previewheight;
        } else {
            second_previewWidth = previewWidth;
            second_previewheight = previewheight;
            for (int i = 0; i < length; i++) {
                size = list.get(i);
                if (size.height > 700) {
//                    LogTxt.getInstance().writeLog("获取size.heigth:" + size.height);
                    if (size.width * previewheight == size.height * previewWidth && size.height < second_previewheight) {
//                        LogTxt.getInstance().writeLog("获取size.heigth:" + size.height + "   size.width:" + size.width);
                        second_previewWidth = size.width;
                        second_previewheight = size.height;
                    }
                }
            }
            preWidth = second_previewWidth;
            preHeight = second_previewheight;
        }
        List<Camera.Size> listP = parameters.getSupportedPictureSizes();
        length = listP.size();
        int pwidth = listP.get(0).width;
        int pheight = listP.get(0).height;
        tmpsize = getOptimalPreviewSize(listP, width, height);
        pwidth = tmpsize.width;
        pheight = tmpsize.height;
        second_previewWidth = 0;
        second_previewheight = 0;
        nlast = -1;
        nThird = -1;

        if (length == 1) {
            photoWidth = pwidth;
            photoHeight = pheight;
        } else {
            second_previewWidth = pwidth;
            second_previewheight = pheight;
            for (int i = 0; i < length; i++) {
                size = listP.get(i);
                if (size.height > 700) {
                    if (size.width * height == size.height * width && size.height < second_previewheight) {
                        second_previewWidth = size.width;
                        second_previewheight = size.height;
                    }
                }
            }
            photoWidth = second_previewWidth;
            photoHeight = second_previewheight;
        }
        if (!isROI) {
            int $t = preHeight / 10;
            int ntmp = preHeight * 3 / 10;
            int t = ntmp;
            int b = preHeight - ntmp;
            int $l = (int) ((preHeight - $t - $t) * 1.585);
            int l;
            if (preWidth > $l) {
                l = (preWidth - $l) / 2;
            } else {
                l = ($l - preWidth) / 2;
            }
            int r = preWidth - l;
//            LogTxt.getInstance().writeLog("获取计算值:$t=" + $t + "   ntmp=" + ntmp + "  b=" + b + "  $l=" + $l + "   l=" + l + "  r=" + r);
            int borders[] = {l, t, r, b};
            m_ROI[0] = l;
            m_ROI[1] = t;
            m_ROI[2] = r;
            m_ROI[3] = b;
            api.VinSetROI(borders, preWidth, preHeight);
            isROI = true;
            int viewW = width;
            int viewH = height;
            if (screenInches > 7.5) {
                viewW = preWidth;
                viewH = preHeight;
            } else {
                if (preWidth * height >= preHeight * width) {
                    viewH = preHeight * width / preWidth;
                } else {
                    viewW = preWidth * height / preHeight;
                }
            }
            if (isFatty)
                myView = new VinViewfinderView(this, viewW, viewH, isFatty);
            else
                myView = new VinViewfinderView(this, viewW, viewH);
            mRec.addView(myView);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) myView.getLayoutParams();
            lp.width = viewW;
            lp.height = viewH;
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            myView.setLayoutParams(lp);

            lp = (RelativeLayout.LayoutParams) mSurfaceView.getLayoutParams();
            lp.width = viewW;
            lp.height = viewH;
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mSurfaceView.setLayoutParams(lp);

            lp = (RelativeLayout.LayoutParams) mShowbitmap.getLayoutParams();
            int showbitmap_w = (int) (viewW / 2 + viewH * 2 / 5 * 1.585);
            int showbitmap_h = (int) (viewH * 2 / 5);
            lp.width = showbitmap_w;
            lp.height = showbitmap_h;
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            lp.topMargin = (int) (viewH * 0.3 + (height - viewH) / 2);
            mShowbitmap.setLayoutParams(lp);

        }
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setJpegQuality(100);
        parameters.setPictureSize(photoWidth, photoHeight);
        // preWidth =1280;
        // preHeight =720;
        parameters.setPreviewSize(preWidth, preHeight);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        try {
            mycamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        mycamera.setPreviewCallback(this);
        if (parameters.isZoomSupported()) {
            parameters.setZoom(2);
        }
        mycamera.setParameters(parameters);
        try {
            mycamera.setPreviewDisplay(mholder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mycamera.startPreview();
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (size.height < 700) continue;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (size.height < 700) continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                } else if (Math.abs(size.height - targetHeight) == minDiff && size.width > optimalSize.width) {
                    optimalSize = size;
                }
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                } else if (Math.abs(size.height - targetHeight) == minDiff && size.width > optimalSize.width) {
                    optimalSize = size;
                }
            }
        }
        return optimalSize;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        tackData = data;
        Camera.Parameters parameters = camera.getParameters();
        int buffl = 30;
        char recogval[] = new char[buffl];
        int pLineWarp[] = new int[32000];
        if (mRemind.getVisibility() == View.INVISIBLE && !bP) {
            int r = api.VinRecognizeNV21Ex(data, parameters.getPreviewSize().width, parameters.getPreviewSize().height, recogval, buffl, pLineWarp);
            if (r == 0) {
                //camera.stopPreview();
                mVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                mVibrator.vibrate(100);
                //
                int[] datas = convertYUV420_NV21toARGB8888(tackData, parameters.getPreviewSize().width,
                        parameters.getPreviewSize().height);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inInputShareable = true;
                opts.inPurgeable = true;
                bitmap = Bitmap.createBitmap(datas, parameters.getPreviewSize().width,
                        parameters.getPreviewSize().height, android.graphics.Bitmap.Config.ARGB_8888);
                LogTxt.getInstance().writeLog("Bitmap:m_ROI:" + m_ROI[0] + " " + m_ROI[1] + " " + m_ROI[2] + " " + m_ROI[3]);
                Bitmap tmpbitmap = Bitmap.createBitmap(bitmap, m_ROI[0], m_ROI[1], m_ROI[2] - m_ROI[0], m_ROI[3] - m_ROI[1]);
                System.out.println("m_ROI:" + m_ROI[0] + " " + m_ROI[1] + " " + m_ROI[2] + " " + m_ROI[3]);
                //savePicture(bitmap,"M");

                String recogResult = api.VinGetResult();
                LogUtils.debug("打印VIN结果:" + recogResult);
                if (getIntent().getBooleanExtra("isScanner", false)) {
                    mRemind.setVisibility(View.VISIBLE);
                    toats_vin.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.putExtra("result", recogResult);
                    setResult(IBase.RETAIL_ELEVEN, intent);
                    finish();
                } else {
                    isThread = true;
                    threadNum = 5;
                    specifiedTime();
                    mRemind.setVisibility(View.VISIBLE);
                    toats_vin.setVisibility(View.GONE);
                    if (api.VinFindVIN() == 1) {
                        mVinresult.setText(recogResult);
                        mVinresult.setTextColor(Color.GREEN);
                        mVinresult.setVisibility(View.VISIBLE);
                        savePicture(tmpbitmap, "V");
                    } else {
                        mVinresult.setText(recogResult);
                        mVinresult.setTextColor(Color.YELLOW);
                        mVinresult.setVisibility(View.VISIBLE);
                        savePicture(tmpbitmap, "E");
                    }
                    //查询监管信息
                    queySupervises(recogResult);
                    mShowbitmap.setImageBitmap(tmpbitmap);
                    mShowbitmap.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static int[] convertYUV420_NV21toARGB8888(byte[] data, int width, int height) {
        int size = width * height;
        int offset = size;
        int[] pixels = new int[size];
        int u, v, y1, y2, y3, y4;
        for (int i = 0, k = 0; i < size; i += 2, k += 2) {
            y1 = data[i] & 0xff;
            y2 = data[i + 1] & 0xff;
            y3 = data[width + i] & 0xff;
            y4 = data[width + i + 1] & 0xff;

            u = data[offset + k] & 0xff;
            v = data[offset + k + 1] & 0xff;
            u = u - 128;
            v = v - 128;

            pixels[i] = convertYUVtoARGB(y1, u, v);
            pixels[i + 1] = convertYUVtoARGB(y2, u, v);
            pixels[width + i] = convertYUVtoARGB(y3, u, v);
            pixels[width + i + 1] = convertYUVtoARGB(y4, u, v);

            if (i != 0 && (i + 2) % width == 0)
                i += width;
        }

        return pixels;
    }

    private static int convertYUVtoARGB(int y, int u, int v) {
        int r, g, b;

        r = y + (int) 1.402f * u;
        g = y - (int) (0.344f * v + 0.714f * u);
        b = y + (int) 1.772f * v;
        r = r > 255 ? 255 : r < 0 ? 0 : r;
        g = g > 255 ? 255 : g < 0 ? 0 : g;
        b = b > 255 ? 255 : b < 0 ? 0 : b;
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }

    @Override
    protected void onStop() {

        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (time != null) {
            time.cancel();
            time = null;
        }
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        try {
            if (mycamera != null) {
                mycamera.setPreviewCallback(null);
                mycamera.stopPreview();
                mycamera.release();
                mycamera = null;
            }
        } catch (Exception e) {
        }
        if (api != null) {
            api.VinKernalUnInit();
            api = null;
        }
    }

    /**
     * 保存图片
     */
    private String savePicture(Bitmap mBitmap, String mStr) {
        strCaptureFilePath = PATH + mStr + "_VIN_" + pictureName() + ".jpg";
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(strCaptureFilePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "图像存储失败", Toast.LENGTH_SHORT).show();
        }
        return strCaptureFilePath;
    }

    public String pictureName() {
        String str = "";
        Time t = new Time();
        t.setToNow();
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        if (month < 10)
            str = String.valueOf(year) + "0" + String.valueOf(month);
        else {
            str = String.valueOf(year) + String.valueOf(month);
        }
        if (date < 10)
            str = str + "0" + String.valueOf(date + "_");
        else {
            str = str + String.valueOf(date + "_");
        }
        if (hour < 10)
            str = str + "0" + String.valueOf(hour);
        else {
            str = str + String.valueOf(hour);
        }
        if (minute < 10)
            str = str + "0" + String.valueOf(minute);
        else {
            str = str + String.valueOf(minute);
        }
        if (second < 10)
            str = str + "0" + String.valueOf(second);
        else {
            str = str + String.valueOf(second);
        }
        return str;
    }

    private View.OnClickListener fClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                String mess = getResources().getString(R.string.toast_flash);
                Toast.makeText(VINactivity.this, mess, Toast.LENGTH_LONG).show();
            } else {
                if (mycamera != null) {
                    Camera.Parameters parameters = mycamera.getParameters();
                    String flashMode = parameters.getFlashMode();
                    if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        parameters.setExposureCompensation(0);
                    } else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        parameters.setExposureCompensation(-1);
                    }
                    try {
                        mycamera.setParameters(parameters);
                    } catch (Exception e) {
                        String mess = getResources().getString(R.string.toast_flash);
                        Toast.makeText(VINactivity.this, mess, Toast.LENGTH_LONG).show();
                    }
                    mycamera.startPreview();
                }
            }
        }
    };
    private View.OnClickListener bClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            api.VinKernalUnInit();
            finish();
        }
    };
    private View.OnClickListener tClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (time != null) {
                time.cancel();
                time = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (mycamera != null) {
                try {
                    bP = true;
                    isFocusTakePicture(mycamera);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private void isFocusTakePicture(Camera camera) {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success && bP) {
                    try {
                        mycamera.takePicture(shutterCallback, null, picturecallback);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        mycamera.startPreview();
                        return;
                    }
                }
            }
        });
    }

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            try {
                if (tone == null) {
                    tone = new ToneGenerator(1, ToneGenerator.MIN_VOLUME);
                }
                tone.startTone(ToneGenerator.TONE_PROP_BEEP);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private Camera.PictureCallback picturecallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            if (data != null) {

                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            camera.stopPreview();
            if (bitmap != null) {

                int $t = height / 10;
                int ntmp = height * 3 / 10;
                int t = ntmp;
                int b = height - ntmp;
                int $l = (int) ((height - $t - $t) * 1.585);
                int l = (width - $l) / 2;
                int r = width - l;
                double proportion = (double) width / (double) photoWidth;
                double hproportion = (double) height / (double) photoHeight;
                l = (int) (l / proportion);
                t = (int) (t / hproportion);
                r = (int) (r / proportion);
                b = (int) (b / hproportion);
                Bitmap tmpbitmap = Bitmap.createBitmap(bitmap, l, t, r - l, b - t);
                String path = savePicture(tmpbitmap, "P");
                tackData = null;
                String flashMode = parameters.getFlashMode();
                if (flashMode != null && flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                    //camera.startPreview();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    //		camera.stopPreview();
                }
                mShowbitmap.setImageBitmap(tmpbitmap);
                mShowbitmap.setVisibility(View.VISIBLE);
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                mRemind.setVisibility(View.VISIBLE);
                toats_vin.setVisibility(View.GONE);
                mVinresult.setText("图片已保存至相册");
                mVinresult.setVisibility(View.VISIBLE);
                if (time == null) {
                    time = new Timer();
                    if (timer == null) {
                        timer = new TimerTask() {
                            public void run() {
                                // isSuccess=false;
                                if (mycamera != null) {
                                    try {
                                        mycamera.autoFocus(new Camera.AutoFocusCallback() {
                                            public void onAutoFocus(
                                                    boolean success,
                                                    Camera camera) {
                                                // isSuccess=success;
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            ;
                        };
                    }
                    time.schedule(timer, 500, 2500);
                }
                try {
                    mycamera.setPreviewDisplay(mSurfaceHolder);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                mycamera.setPreviewCallback(VINactivity.this);
                camera.startPreview();
                bP = false;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if (mycamera != null) {
                    mycamera.setPreviewCallback(null);
                    mycamera.stopPreview();
                    mycamera.release();
                    mycamera = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (api != null) {
                api.VinKernalUnInit();
                api = null;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        isThread = false;
        mRemind.setVisibility(View.INVISIBLE);
        toats_vin.setVisibility(View.VISIBLE);
        mVinresult.setVisibility(View.INVISIBLE);
        mShowbitmap.setVisibility(View.INVISIBLE);
        mMsgvin.setVisibility(View.GONE);
        query_vin.setVisibility(View.GONE);
        return true;
    }

    TaskItemBean.TaskItemsEntity entity;

    /**
     * 根据查询监管信息
     */
    private void queySupervises(String vin) {
        //正在查询提示
        vinCode = vin;
        toats_vin.setVisibility(View.VISIBLE);
        List<TaskItemBean.TaskItemsEntity> lists = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "vinNo=\"" + vin + "\"");
        if (lists != null && lists.size() > 0) {
            entity = lists.get(0);
            int status_speek = entity.getCommit_status();
            taskCode = entity.getTaskCode();
            if (entity.getCommit_status() == 0) {
                entity.setCommit_status(1);
                entity.setCheckMethod(IBase.VINCODE);
                entity.setCheckTime(DateUtil.getSimpleDateYYYYMMDDHHMMMSS(System.currentTimeMillis()));
                entity.setLat(latitude + "");
                entity.setLng(longitude + "");
                db.update(entity);
                query_vin.setVisibility(View.GONE);
                jxs_vin.setText("经销商:" + entity.getDistributor());
                company_vin.setText("金融公司:" + entity.getFinance());
                jxs_vin.setVisibility(View.VISIBLE);
                company_vin.setVisibility(View.VISIBLE);
                //将图片保存至上传对列
                savePhoto(vin, entity.getTaskCode());
            } else {
                jxs_vin.setVisibility(View.GONE);
                company_vin.setVisibility(View.GONE);
                query_vin.setVisibility(View.VISIBLE);
                query_vin.setText("该车已盘库,点击继续");
            }
            // 查询成功信息显示
            mMsgvin.setVisibility(View.VISIBLE);
            //查询已盘，未盘
            List<TaskItemBean.TaskItemsEntity> finish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\"");
            List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\" and commit_status=0");
            num_vin.setText("今日盘点" + (finish.size() - nofinish.size()) + "台，还剩" + nofinish.size() + "台");
            if (nofinish.size() > 0) {
                String content;
                if (status_speek == 0) {
                    if (entity.getAllowVideo() == 1) {
                        showVideo();
                    }
                    content = "盘库成功剩余" + nofinish.size() + "台";
                } else {
                    content = "该车已盘库";
                }
                setContentSpeek(content);
            }
            if (nofinish.size() == 0) {
                if (entity.getAllowVideo() == 1) {
                    showVideo();
                }
                voiceUtils.startSpeek("全部完成辛苦了", new VoiceUtils.SpeekEndListener() {
                    @Override
                    public void setSpeekEndListener(boolean b) {
                        if (b) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            voiceUtils.releaseSpeek();
                            if (entity.getAllowVideo() == 0) {
                                finish();
                            }
                        }
                    }
                });
            }
        } else {
            query_vin.setVisibility(View.VISIBLE);
            query_vin.setText("查询失败...");
            setContentSpeek("查询失败");
        }
    }

    private void setContentSpeek(String content) {
        voiceUtils.startSpeek(content, new VoiceUtils.SpeekEndListener() {
            @Override
            public void setSpeekEndListener(boolean b) {
                if (b && exitFlag) {
                    voiceUtils.releaseSpeek();
                }
            }
        });
    }

    //复制文档
    private void savePhoto(final String vin, final String code) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                File upload = new File(strCaptureFilePath);
                String uplod = FileUtils.getCarUploadPath() + "image/" + System.currentTimeMillis() + "/vin";
                String name = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance()) + ".jpg";
                File save = new File(uplod);
                if (!save.exists()) {
                    save.mkdirs();
                }
                save = new File(uplod, name);
                if (upload.exists()) {
                    final String savePath = save.getAbsolutePath();
                    FileUtils.CopySdcardFile(upload.getAbsolutePath(), savePath);
                    //复制完成保存到本地数据库
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<String> lists = new ArrayList<String>();
                            lists.add(savePath);
                            IBaseMethod.saveDateLoaction(db, lists, code + "_" + vin, "VIN码盘库", latitude + "", longitude + "");
                            List<UploadBean> lis = db.findAll(UploadBean.class);
                            for (UploadBean ub : lis) {
                                LogUtils.debug(ub.toString());
                            }
                        }
                    });
                }
            }
        }.start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exitFlag = true;
        isThread = false;
        mapUtils.stopLocation();
    }

    /* 添加在规定时间没有点击屏幕自动进入扫描状态 */
    private int threadNum = 5;//等待时间

    private void specifiedTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (isThread) {
                    threadNum--;
                    LogUtils.debug("进度走动:" + threadNum);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (threadNum == 0) {
                        isThread = false;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                List<TaskItemBean.TaskItemsEntity> nofinish = db.findAllByWhere(TaskItemBean.TaskItemsEntity.class, "taskCode=\"" + entity.getTaskCode() + "\" and commit_status=0");
                                if (nofinish.size() == 0) {
                                    finish();
                                    return;
                                } else {
                                    mRemind.setVisibility(View.INVISIBLE);
                                    toats_vin.setVisibility(View.VISIBLE);
                                    mVinresult.setVisibility(View.INVISIBLE);
                                    mShowbitmap.setVisibility(View.INVISIBLE);
                                    mMsgvin.setVisibility(View.GONE);
                                    query_vin.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            }
        }.start();
    }

    /* 盘库视频录制 */
    private void showVideo() {
        isThread = false;
        DialogUtil.jumpCorrectErr(this, "该车盘库需要进行视频录制操作，请录制", "录 制", 2, getResources().getColor(android.R.color.holo_orange_light), false, new DialogButtonListener() {
            @Override
            public void ok() {
                CaptureConfiguration config = createCaptureConfiguration();
                Intent intent = new Intent(VINactivity.this, VideoRecordActivity.class);
                intent.putExtra(VideoRecordActivity.EXTRA_CAPTURE_CONFIGURATION, config);
                intent.putExtra("isScance", true);
                startActivityForResult(intent, 101);
            }

            @Override
            public void cancel() {

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

    private String vinCode = "", taskCode = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String path = data.getStringExtra(VideoRecordActivity.EXTRA_OUTPUT_FILENAME);
            //提交成功
            File file = new File(path);
            UploadBean uploadBean = IBaseMethod.saveUploadBean(file, taskCode + "_" + vinCode, "VIN码盘库", latitude + "", longitude + "");
            FinalDb db = FinalDb.create(VINactivity.this, IBase.BASE_DATE, true);
            db.save(uploadBean);
            threadNum = 3;
            isThread = true;
            specifiedTime();
        }
    }
}
