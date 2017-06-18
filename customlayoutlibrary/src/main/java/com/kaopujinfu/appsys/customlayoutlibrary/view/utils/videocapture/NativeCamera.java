package com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Doris on 2017/5/18.
 */
public class NativeCamera {

    private Camera camera = null;
    private boolean isFrontFacingCamera = false;
    private Camera.Parameters params = null;

    public Camera getNativeCamera() {
        return camera;
    }

    public void openNativeCamera(boolean useFrontFacingCamera) throws RuntimeException {
        if (useFrontFacingCamera) {
            if (!hasFrontFacingCamera()) return;
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            isFrontFacingCamera = true;
        } else {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
    }

    public void unlockNativeCamera() {
        camera.unlock();
    }

    public void releaseNativeCamera() {
        camera.release();
    }

    public void setNativePreviewDisplay(SurfaceHolder holder) throws IOException {
        camera.setPreviewDisplay(holder);
    }

    public void startNativePreview() {
        camera.startPreview();
    }

    public void stopNativePreview() {
        camera.stopPreview();
    }

    public void clearNativePreviewCallback() {
        camera.setPreviewCallback(null);
    }

    public Camera.Parameters getNativeCameraParameters() {
        if (params == null) {
            params = camera.getParameters();
        }
        return params;
    }

    public void updateNativeCameraParameters(Camera.Parameters params) {
        this.params = params;
        camera.setParameters(params);
    }

    public void setDisplayOrientation(int degrees) {
        camera.setDisplayOrientation(degrees);
    }

    public int getCameraOrientation() {
        Camera.CameraInfo camInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(getCurrentCameraId(), camInfo);
        return camInfo.orientation;
    }

    public boolean isFrontFacingCamera() {
        return isFrontFacingCamera;
    }

    private int getCurrentCameraId() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == getCurrentCameraFacing()) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private int getCurrentCameraFacing() {
        return isFrontFacingCamera ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    private boolean hasFrontFacingCamera() {
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo newInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, newInfo);
            if (newInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

}
