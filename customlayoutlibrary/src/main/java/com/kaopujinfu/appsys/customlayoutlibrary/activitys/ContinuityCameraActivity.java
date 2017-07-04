package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 连续拍照
 * Created by Doris on 2017/5/17.
 */
public class ContinuityCameraActivity extends Activity implements View.OnClickListener, SensorEventListener, SurfaceHolder.Callback {

    private SurfaceView cameraView;
    private ImageView cameraTake, cameraPhoto, cameraPhotoBack, cameraPhotoConfirm;
    private RelativeLayout cameraPhotoLayout;
    private int repceStatus;//判断是否连续拍照

    private boolean isPortrait = true;
    private boolean isLandscapeRigth = false;
    private Bitmap mBitmap;
    private String imagePath;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private SensorManager mManager;
    private Sensor mSensor;
    private SensorListener mListener = new SensorListener() {
        @Override
        public void onChanged(SensorEvent event) {
            float x = event.values[0];
            if (x > 8) {
                // 横屏（左边）
                isPortrait = false;
                isLandscapeRigth = false;
            } else if (x < -8) {
                // 横屏（右边）
                isPortrait = false;
                isLandscapeRigth = true;
            } else {
                // 竖屏
                isPortrait = true;
                isLandscapeRigth = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_continuity_camera);
        // 获取图片保存路径
        imagePath = getIntent().getStringExtra("imagePath");
        repceStatus = getIntent().getIntExtra("repceStatus", 0);
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        LogTxt.getInstance().writeLog("进入拍照页面，图片保存路径：" + imagePath);

        init();
    }

    private void init() {
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraTake = (ImageView) findViewById(R.id.cameraTake);
        cameraPhoto = (ImageView) findViewById(R.id.cameraPhoto);
        cameraPhotoBack = (ImageView) findViewById(R.id.cameraPhotoBack);
        cameraPhotoConfirm = (ImageView) findViewById(R.id.cameraPhotoConfirm);
        cameraPhotoLayout = (RelativeLayout) findViewById(R.id.cameraPhotoLayout);

        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHolder = cameraView.getHolder();
        mHolder.addCallback(this);

        setCameraEvent();
    }

    private void setCameraEvent() {
        cameraView.setOnClickListener(this);
        cameraTake.setOnClickListener(this);
        cameraPhotoBack.setOnClickListener(this);
        cameraPhotoConfirm.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.debug("进入了onResume");
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
        register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
        unregister();
    }

    /**
     * 获取Camera对象
     *
     * @return
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
        }
        return camera;
    }

    /**
     * 开始预览相机内容
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            // 旋转90度
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 注册感应器
     */
    public void register() {
        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * 注销感应器
     */
    public void unregister() {
        mManager.unregisterListener(this);
    }
    private boolean isJd=true;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cameraView) {
            // 点击拍照界面对焦
            if (mCamera != null&&isJd) {
                mCamera.autoFocus(null);
            }
        } else if (view.getId() == R.id.cameraTake) {
            // 拍照
            isJd=false;
            takePhoto();
        } else if (view.getId() == R.id.cameraPhotoBack) {
            // 点击取消保存按钮
            isJd=true;
            mCamera.startPreview();
            cameraPhotoLayout.setVisibility(View.GONE);
        } else if (view.getId() == R.id.cameraPhotoConfirm) {
            // 点击保存按钮
            savePhoto();
            isJd=true;
            mCamera.startPreview();
            cameraPhotoLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
                if (b) {
                    mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] bytes, Camera camera) {
                            mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            Matrix martix = new Matrix();
                            if (isPortrait) {
                                // 竖屏
                                martix.setRotate(90);
                            } else if (isLandscapeRigth) {
                                // 横屏向右边
                                martix.setRotate(180);
                            }
                            DisplayMetrics dm = new DisplayMetrics();
                            //获取屏幕信息
                            getWindowManager().getDefaultDisplay().getMetrics(dm);
                            int screenWidth = dm.widthPixels;
                            int screenHeigh = dm.heightPixels;
                            //设置图片大小
                            mBitmap = ThumbnailUtils.extractThumbnail(mBitmap, screenHeigh, screenWidth);
                            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), martix, true);
                            cameraPhoto.setImageBitmap(mBitmap);
                            cameraPhotoLayout.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 保存照片
     */
    private void savePhoto() {
        new Thread() {
            @Override
            public void run() {
                if (mBitmap != null) {
                    BufferedOutputStream bos = null;
                    try {
                        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance()) + ".jpg";
                        File file = new File(imagePath + name);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        bos = new BufferedOutputStream(new FileOutputStream(file));
                        // 将图片压缩到流中
                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (repceStatus == 1) {
                                    finish();
                                }
                            }
                        });
                    } catch (Exception e) {
                        LogTxt.getInstance().writeLog("保存图片出错：", e);
                    } finally {
                        try {
                            bos.flush();//输出
                            bos.close();//关闭
                            mBitmap.recycle();// 回收bitmap空间
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent arg0) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.onChanged(arg0);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (mCamera != null && mHolder != null) {
            startPreview(mCamera, mHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mCamera != null) {
            mCamera.stopPreview();
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    interface SensorListener {
        void onChanged(SensorEvent event);
    }

}