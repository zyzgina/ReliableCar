package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Describe: 权限设置
 * Created Author: Gina
 * Created Date: 2017/7/13.
 */

public class PermissionsUntils {
    private static String[] premissions = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.RECORD_AUDIO};

    //请求权限
    public static void requesetContanctsPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, premissions, 1);
    }

    //请求相机权限
    public static void requesetCameraPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, 1);
    }

    //请求定位权限
    public static void requesetLoactionPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    //请求语音权限
    public static void requesetRecordPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.RECORD_AUDIO}, 1);
    }

    //检查相机权限是否存在
    public static boolean checkCameraPermissions(Activity activity) {
        boolean flag = false;
        PackageManager pm = activity.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(android.Manifest.permission.CAMERA, "net.loansys.app")) {
            flag = true;
        }
        return flag;
    }

    //检查相机权限是否存在
    public static boolean checkLoationPermissions(Activity activity) {
        boolean flag = false;
        PackageManager pm = activity.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, "net.loansys.app")) {
            flag = true;
        }
        return flag;
    }

    //检查语音权限是否存在
    public static boolean checkRecordPermissions(Activity activity) {
        boolean flag = false;
        PackageManager pm = activity.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(android.Manifest.permission.RECORD_AUDIO, "net.loansys.app")) {
            flag = true;
        }
        return flag;
    }
}
