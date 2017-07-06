package com.kaopujinfu.appsys.customlayoutlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.kaopujinfu.appsys.customlayoutlibrary.carsh.AppCrashHandler;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuoliji on 2017/3/28.
 */

public class RetailAplication extends MultiDexApplication {

    private List<Activity> activities = new ArrayList<>();
    private static RetailAplication sInstance = null;

    public List<Activity> getActivityList() {
        return activities;
    }

    public RetailAplication() {
        sInstance = this;
    }

    public static RetailAplication getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init(getApplicationContext());
        String domain = SPUtils.get(getContext(), "domain", "").toString();
        if (GeneralUtils.isEmpty(domain))
            SPUtils.put(getContext(), "domain", "http://kanche.loansys.net/");

        //abdroid 7.0 调用系统路径抛出FileUriExposedException的解决方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public void exitAllActicity() {
        if (activities != null && activities.size() > 0) {
            Log.i("info", "activitys.size=" + activities.size());
            for (Activity activity : activities) {
                if (activity != null) {
                    activity.finish();
                }
            }
            activities.clear();
        }
    }
}
