package com.kaopujinfu.appsys.customlayoutlibrary.carsh;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

public class AppCrashHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler mDefaultHandler;

    public static AppCrashHandler getInstance() {
        if (instance == null) {
            instance = new AppCrashHandler();
        }
        return instance;
    }

    public Context mContext;

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private static AppCrashHandler instance;

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        String logdir;
        if (Environment.getExternalStorageDirectory() != null) {
            logdir = FileUtils.getCrashLogFilePath();
            File file = new File(logdir);
            boolean mkSuccess;
            if (!file.isDirectory()) {
                mkSuccess = file.mkdirs();
                if (!mkSuccess) {
                    mkSuccess = file.mkdirs();
                }
            }
            try {
                FileWriter fw = new FileWriter(logdir + File.separator + DateUtil.getSimpleDateYYYYMMDDHHMMM(System.currentTimeMillis()) + ".txt", true);
                fw.write(new Date() + "\n");
                StackTraceElement[] stackTrace = arg1.getStackTrace();
                fw.write(arg1.getMessage() + "\n");
                for (int i = 0; i < stackTrace.length; i++) {
                    fw.write("file:" + stackTrace[i].getFileName() + " class:" + stackTrace[i].getClassName()
                            + " method:" + stackTrace[i].getMethodName() + " line:" + stackTrace[i].getLineNumber()
                            + "\n");
                }
                fw.write("\n");
                fw.close();
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        arg1.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
