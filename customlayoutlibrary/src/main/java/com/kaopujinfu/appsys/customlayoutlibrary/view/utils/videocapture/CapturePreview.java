package com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.kaopujinfu.appsys.customlayoutlibrary.listener.CapturePreviewListener;

import java.io.IOException;

/**
 * Created by Doris on 2017/5/18.
 */
public class CapturePreview implements SurfaceHolder.Callback {

    private boolean mPreviewRunning = false;
    private final CapturePreviewListener mInterface;
    public final CameraWrapper mCameraWrapper;

    public CapturePreview(CapturePreviewListener capturePreviewInterface, CameraWrapper cameraWrapper,
                          SurfaceHolder holder) {
        mInterface = capturePreviewInterface;
        mCameraWrapper = cameraWrapper;

        initalizeSurfaceHolder(holder);
    }

    @SuppressWarnings("deprecation")
    private void initalizeSurfaceHolder(final SurfaceHolder surfaceHolder) {
        surfaceHolder.removeCallback(this);
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Necessary for older API's
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        // NOP
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
        if (mPreviewRunning) {
            try {
                mCameraWrapper.stopPreview();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        try {
            mCameraWrapper.configureForPreview(width, height);
        } catch (final RuntimeException e) {
            e.printStackTrace();
            mInterface.onCapturePreviewFailed();
            return;
        }

        try {
            mCameraWrapper.enableAutoFocus();
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }

        try {
            mCameraWrapper.startPreview(holder);
            setPreviewRunning(true);
        } catch (final IOException e) {
            e.printStackTrace();
            mInterface.onCapturePreviewFailed();
        } catch (final RuntimeException e) {
            e.printStackTrace();
            mInterface.onCapturePreviewFailed();
        }
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        // NOP
    }

    public void releasePreviewResources() {
        if (mPreviewRunning) {
            try {
                mCameraWrapper.stopPreview();
                setPreviewRunning(false);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setPreviewRunning(boolean running) {
        mPreviewRunning = running;
    }

    @SuppressWarnings("deprecation")
    public static boolean isFrontCameraAvailable() {
        int i;
        for (i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo newInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }
}
