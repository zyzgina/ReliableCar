package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.VideoRecorderListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.VideoRecordingListener;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.view.VideoCaptureView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.CameraWrapper;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.CaptureConfiguration;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.NativeCamera;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.VideoFile;
import com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture.VideoRecorder;

/**
 * 录制视频
 * Created by Doris on 2017/5/18.
 */
public class VideoRecordActivity extends Activity implements VideoRecorderListener, VideoRecordingListener {

    public static final int RESULT_ERROR = 753245;
    private static final int REQUESTCODE_SWITCHCAMERA = 578465;

    public static final String EXTRA_OUTPUT_FILENAME = "com.kaopujinfu.extraoutputfilename";
    public static final String EXTRA_CAPTURE_CONFIGURATION = "com.kaopujinfu.extracaptureconfiguration";
    public static final String EXTRA_ERROR_MESSAGE = "com.kaopujinfu.extraerrormessage";
    private static final String EXTRA_FRONTFACINGCAMERASELECTED = "com.kaopujinfu.extracamerafacing";
    private static final String SAVED_RECORDED_BOOLEAN = "com.kaopujinfu.savedrecordedboolean";
    protected static final String SAVED_OUTPUT_FILENAME = "com.kaopujinfu.savedoutputfilename";

    private VideoCaptureView mVideoCaptureView;

    private CaptureConfiguration mCaptureConfiguration;
    private VideoFile mVideoFile = null;
    private VideoRecorder mVideoRecorder;
    private boolean mVideoRecorded = false, isFrontFacingCameraSelected;

    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_record);
        // 初始化
        initializeCaptureConfiguration(savedInstanceState);
        // 获取路径
        videoPath = getIntent().getStringExtra("videoPath");
        LogTxt.getInstance().writeLog("进入视频录制页面，视频保存路径：" + videoPath);
        // 获取控件
        mVideoCaptureView = (VideoCaptureView) findViewById(R.id.videocaptureview_vcv);
        if (mVideoCaptureView == null) return;
        // 初始化 UI
        initializeRecordingUI();
    }

    private void initializeCaptureConfiguration(final Bundle savedInstanceState) {
        mCaptureConfiguration = generateCaptureConfiguration();
        mVideoRecorded = generateVideoRecorded(savedInstanceState);
        mVideoFile = generateOutputFile(savedInstanceState);
        isFrontFacingCameraSelected = generateIsFrontFacingCameraSelected();
    }

    private void initializeRecordingUI() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        mVideoRecorder = new VideoRecorder(this, mCaptureConfiguration, mVideoFile,
                new CameraWrapper(new NativeCamera(), display.getRotation()),
                mVideoCaptureView.getPreviewSurfaceHolder(),
                isFrontFacingCameraSelected);
        mVideoCaptureView.setVideoRecordingListener(this);

        if (mVideoRecorded) {
            mVideoCaptureView.updateUIRecordingFinished(getVideoThumbnail());
        } else {
            mVideoCaptureView.updateUINotRecording();
        }
        mVideoCaptureView.setShowTimer(mCaptureConfiguration.getShowTimer());
    }

    @Override
    protected void onPause() {
        if (mVideoRecorder != null) {
            mVideoRecorder.stopRecording(null);
        }
        releaseAllResources();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finishCancelled();
    }

    @Override
    public void onRecordClicked() {
        try {
            mVideoRecorder.toggleRecording();
        } catch (Exception e) {
            LogTxt.getInstance().writeLog("VideoRecordActivity-onRecordClicked()：", e);
        }
    }

    @Override
    public void onAcceptButtonClicked() {
        finishCompleted();
    }

    @Override
    public void onDeclineButtonClicked() {
        finishCancelled();
    }

    @Override
    public void onRecordingStarted() {
        mVideoCaptureView.updateUIRecordingOngoing();
    }

    @Override
    public void onRecordingStopped(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        mVideoCaptureView.updateUIRecordingFinished(getVideoThumbnail());
        releaseAllResources();
    }

    @Override
    public void onRecordingSuccess() {
        mVideoRecorded = true;
    }

    @Override
    public void onRecordingFailed(String message) {
        finishError(message);
    }

    private void finishCompleted() {
        final Intent result = new Intent();
        result.putExtra(EXTRA_OUTPUT_FILENAME, mVideoFile.getFullPath());
        this.setResult(RESULT_OK, result);
        finish();
    }

    private void finishCancelled() {
        this.setResult(RESULT_CANCELED);
        finish();
    }

    private void finishError(final String message) {
        Toast.makeText(getApplicationContext(), "Can't capture video: " + message, Toast.LENGTH_LONG).show();

        final Intent result = new Intent();
        result.putExtra(EXTRA_ERROR_MESSAGE, message);
        this.setResult(RESULT_ERROR, result);
        finish();
    }

    private void releaseAllResources() {
        if (mVideoRecorder != null) {
            mVideoRecorder.releaseAllResources();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(SAVED_RECORDED_BOOLEAN, mVideoRecorded);
        savedInstanceState.putString(SAVED_OUTPUT_FILENAME, mVideoFile.getFullPath());
        super.onSaveInstanceState(savedInstanceState);
    }

    protected CaptureConfiguration generateCaptureConfiguration() {
        CaptureConfiguration returnConfiguration = this.getIntent().getParcelableExtra(EXTRA_CAPTURE_CONFIGURATION);
        if (returnConfiguration == null) {
            returnConfiguration = CaptureConfiguration.getDefault();
        }
        return returnConfiguration;
    }

    private boolean generateVideoRecorded(final Bundle savedInstanceState) {
        if (savedInstanceState == null) return false;
        return savedInstanceState.getBoolean(SAVED_RECORDED_BOOLEAN, false);
    }

    protected VideoFile generateOutputFile(Bundle savedInstanceState) {
        VideoFile returnFile;
        if (savedInstanceState != null) {
            returnFile = new VideoFile(savedInstanceState.getString(SAVED_OUTPUT_FILENAME));
        } else {
            returnFile = new VideoFile(this.getIntent().getStringExtra(EXTRA_OUTPUT_FILENAME));
        }
        // TODO: add checks to see if outputfile is writeable
        return returnFile;
    }

    private boolean generateIsFrontFacingCameraSelected() {
        return getIntent().getBooleanExtra(EXTRA_FRONTFACINGCAMERASELECTED, false);
    }

    public Bitmap getVideoThumbnail() {
        final Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(mVideoFile.getFullPath(),
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        if (thumbnail == null) {

        }
        return thumbnail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.setResult(resultCode, data);
        finish();
    }
}
