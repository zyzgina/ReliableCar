package com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;

import com.kaopujinfu.appsys.customlayoutlibrary.listener.CapturePreviewListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.VideoRecorderListener;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;

import java.io.IOException;

/**
 * Created by Doris on 2017/5/18.
 */

public class VideoRecorder implements MediaRecorder.OnInfoListener, CapturePreviewListener {

    private CameraWrapper mCameraWrapper;
    private CapturePreview mVideoCapturePreview;

    private final CaptureConfiguration mCaptureConfiguration;
    private final VideoFile mVideoFile;

    private MediaRecorder mRecorder;
    private boolean mRecording = false;
    private final VideoRecorderListener mRecorderInterface;

    public VideoRecorder(VideoRecorderListener recorderInterface,
                         CaptureConfiguration captureConfiguration,
                         VideoFile videoFile,
                         CameraWrapper cameraWrapper,
                         SurfaceHolder previewHolder,
                         boolean useFrontFacingCamera) {
        mCaptureConfiguration = captureConfiguration;
        mRecorderInterface = recorderInterface;
        mVideoFile = videoFile;
        mCameraWrapper = cameraWrapper;

        initializeCameraAndPreview(previewHolder, useFrontFacingCamera);
    }

    protected void initializeCameraAndPreview(SurfaceHolder previewHolder, boolean useFrontFacingCamera) {
        try {
            mCameraWrapper.openCamera(useFrontFacingCamera);
        } catch (final OpenCameraException e) {
            e.printStackTrace();
            mRecorderInterface.onRecordingFailed(e.getMessage());
            return;
        }

        mVideoCapturePreview = new CapturePreview(this, mCameraWrapper, previewHolder);
    }

    public void toggleRecording() throws Exception {
        if (mCameraWrapper == null) {
            throw new Exception();
        }

        if (isRecording()) {
            stopRecording(null);
        } else {
            startRecording();
        }
    }

    protected void startRecording() {
        mRecording = false;

        if (!initRecorder()) return;
        if (!prepareRecorder()) return;
        if (!startRecorder()) return;

        mRecording = true;
        mRecorderInterface.onRecordingStarted();
    }

    public void stopRecording(String message) {
        if (!isRecording()) return;

        try {
            getMediaRecorder().stop();
            mRecorderInterface.onRecordingSuccess();
        } catch (final RuntimeException e) {
            LogTxt.getInstance().writeLog("VideoRecoder-stopRecording()：", e);
        }

        mRecording = false;
        mRecorderInterface.onRecordingStopped(message);
    }

    private boolean initRecorder() {
        try {
            mCameraWrapper.prepareCameraForRecording();
        } catch (final PrepareCameraException e) {
            e.printStackTrace();
            mRecorderInterface.onRecordingFailed("Unable to record video");
            return false;
        }

        setMediaRecorder(new MediaRecorder());
        configureMediaRecorder(getMediaRecorder(), mCameraWrapper.getCamera());

        return true;
    }

    @SuppressWarnings("deprecation")
    protected void configureMediaRecorder(final MediaRecorder recorder, android.hardware.Camera camera)
            throws IllegalStateException, IllegalArgumentException {
        recorder.setCamera(camera);
        recorder.setAudioSource(mCaptureConfiguration.getAudioSource());
        recorder.setVideoSource(mCaptureConfiguration.getVideoSource());

        CamcorderProfile baseProfile = mCameraWrapper.getBaseRecordingProfile();
        baseProfile.fileFormat = mCaptureConfiguration.getOutputFormat();

        RecordingSize size = mCameraWrapper.getSupportedRecordingSize(mCaptureConfiguration.getVideoWidth(), mCaptureConfiguration.getVideoHeight());
        baseProfile.videoFrameWidth = size.width;
        baseProfile.videoFrameHeight = size.height;
        baseProfile.videoBitRate = mCaptureConfiguration.getVideoBitrate();

        baseProfile.audioCodec = mCaptureConfiguration.getAudioEncoder();
        baseProfile.videoCodec = mCaptureConfiguration.getVideoEncoder();

        recorder.setProfile(baseProfile);
        recorder.setMaxDuration(mCaptureConfiguration.getMaxCaptureDuration());
        recorder.setOutputFile(mVideoFile.getFullPath());
        recorder.setOrientationHint(mCameraWrapper.getRotationCorrection());
        recorder.setVideoFrameRate(mCaptureConfiguration.getVideoFPS());

        try {
            recorder.setMaxFileSize(mCaptureConfiguration.getMaxCaptureFileSize());
        } catch (IllegalArgumentException e) {
            LogTxt.getInstance().writeLog("VideoRecoder-configureMediaRecorder()-IllegalArgumentException：", e);
        } catch (RuntimeException e2) {
            LogTxt.getInstance().writeLog("VideoRecoder-configureMediaRecorder()-RuntimeException：", e2);
        }
        recorder.setOnInfoListener(this);
    }

    private boolean prepareRecorder() {
        try {
            getMediaRecorder().prepare();
            return true;
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (final IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean startRecorder() {
        try {
            getMediaRecorder().start();
            return true;
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (final RuntimeException e2) {
            e2.printStackTrace();
            mRecorderInterface.onRecordingFailed("Unable to record video with given settings");
            return false;
        }
    }

    protected boolean isRecording() {
        return mRecording;
    }

    protected void setMediaRecorder(MediaRecorder recorder) {
        mRecorder = recorder;
    }

    protected MediaRecorder getMediaRecorder() {
        return mRecorder;
    }

    private void releaseRecorderResources() {
        MediaRecorder recorder = getMediaRecorder();
        if (recorder != null) {
            recorder.release();
            setMediaRecorder(null);
        }
    }

    public void releaseAllResources() {
        if (mVideoCapturePreview != null) {
            mVideoCapturePreview.releasePreviewResources();
        }
        if (mCameraWrapper != null) {
            mCameraWrapper.releaseCamera();
            mCameraWrapper = null;
        }
        releaseRecorderResources();
    }

    @Override
    public void onCapturePreviewFailed() {
        mRecorderInterface.onRecordingFailed("Unable to show camera preview");
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        switch (what) {
            case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
                // NOP
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
                stopRecording("Capture stopped - Max duration reached");
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
                stopRecording("Capture stopped - Max file size reached");
                break;
            default:
                break;
        }
    }
}
